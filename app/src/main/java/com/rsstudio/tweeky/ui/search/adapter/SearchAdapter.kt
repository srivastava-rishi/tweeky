package com.rsstudio.tweeky.ui.search.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.card.MaterialCardView
import com.rsstudio.tweeky.R
import com.rsstudio.tweeky.data.network.model.Data
import de.hdodenhof.circleimageview.CircleImageView

class SearchAdapter(
    private var context: Context,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() , Filterable {

    private var list: MutableList<Data> = mutableListOf()
    private var sortBy: Int = 0

    private var filteredPlayerList: MutableList<Data> = mutableListOf()

    var logTag = "@SearchAdapter"

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var tvName: TextView = view.findViewById(R.id.tvName)
        var tvScore: TextView = view.findViewById(R.id.tvScore)
        var tvMe: TextView = view.findViewById(R.id.tvMe)
        var civUserPic: CircleImageView = view.findViewById(R.id.civUserPic)
        var mcvcontainer: MaterialCardView = view.findViewById(R.id.mcvContainer)

        @SuppressLint("SetTextI18n")
        fun onBind(item: Data) {

            tvName.text = item.name

            //setting the score
            when (sortBy) {
                1 -> {
                    tvScore.text = item.score.toString()
                }
                2 -> {
                    tvScore.text = item.runup.toString()
                }
                3 -> {
                    tvScore.text = item.jump.toString()
                }
                4 -> {
                    tvScore.text = item.bfc.toString()
                }
                5 -> {
                    tvScore.text = item.ffc.toString()
                }
                else -> {
                    tvScore.text = item.release.toString()
                }
            }

            // consider mine
            if (item.id == "010"){
                mcvcontainer.strokeWidth = 4
                mcvcontainer.strokeColor = ContextCompat.getColor(context, R.color.red)
                tvMe.visibility = View.VISIBLE
            }else{
                mcvcontainer.strokeWidth = 0
                tvMe.visibility = View.GONE
            }

            // setting image
            Glide
                .with(context)
                .load(item.image)
                .thumbnail(0.7f)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(civUserPic)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.view_filtered_player_data, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val item = filteredPlayerList[position]
        if (holder is SearchAdapter.ItemViewHolder) {
            holder.mcvcontainer.animation = AnimationUtils.loadAnimation(context,R.anim.anim_fade_scale)
            holder.onBind(item)
        }
    }

    fun submitList(newList: List<Data>,sortType: Int) {
        filteredPlayerList.clear()
        list.addAll(newList)
        sortBy = sortType
        list = list.sortedByDescending {
            when (sortBy) {
                1 -> {
                    it.score
                }
                2 -> {
                    it.runup
                }
                3 -> {
                    it.jump
                }
                4 -> {
                    it.bfc
                }
                5 -> {
                    it.ffc
                }
                else -> {
                    it.release
                }
            }
        } as MutableList<Data>
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        if (filteredPlayerList.size != 0) {
            return filteredPlayerList.size
        }
        return 0
    }

    fun clearFilteredList(){
        filteredPlayerList.clear()
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {

        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {

                val charString = constraint?.toString() ?: ""


                if(charString.isEmpty()) {
                    filteredPlayerList.clear()
                    notifyDataSetChanged()
                } else{

                    var filteredList:  MutableList<Data> = mutableListOf()

                    list.filter {
                        (it.name!!.lowercase().startsWith(constraint.toString().lowercase().trim()))
                    }.forEach{ filteredList.add(it)}
                    filteredPlayerList = filteredList
                }

                return FilterResults().apply { values = filteredPlayerList }

            }
            override fun publishResults(constraint: CharSequence, results: FilterResults?) {
                if (results!!.values != null) {
                    filteredPlayerList = results.values as MutableList<Data>
                    notifyDataSetChanged()
                }

            }
        }
    }

}