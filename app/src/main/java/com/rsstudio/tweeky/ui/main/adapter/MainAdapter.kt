package com.rsstudio.tweeky.ui.main.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rsstudio.tweeky.R
import com.rsstudio.tweeky.data.network.model.Athlete
import com.rsstudio.tweeky.data.network.model.Data

class MainAdapter(
    private var context: Context,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() , Filterable {

    private var list: MutableList<Athlete> = mutableListOf()

    private var filteredAthleteList: MutableList<Data> = mutableListOf()

    var logTag = "@MainAdapter"

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var tvName: TextView = view.findViewById(R.id.tvName)
        var tvScore: TextView = view.findViewById(R.id.tvScore)

        @SuppressLint("SetTextI18n")
        fun onBind(item: Data) {

            tvName.text = item.name
            tvScore.text = item.runup.toString()

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.view_player_data, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val item = filteredAthleteList[position]
        if (holder is MainAdapter.ItemViewHolder) {
//            holder.cvContainer.animation = AnimationUtils.loadAnimation(context,R.anim.anim_fade_scale)
            holder.onBind(item)
        }
    }

    fun submitList(newList: List<Athlete>) {
        list.clear()
        filteredAthleteList.clear()
        list.addAll(newList)
        filteredAthleteList.addAll(list[0].athletes)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        if (filteredAthleteList.size != 0) {
            return filteredAthleteList.size
        }
        return 0
    }

    override fun getFilter(): Filter {

        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {

                val charString = constraint?.toString() ?: ""

                if(charString.isEmpty()){
                    filteredAthleteList.clear()
                    filteredAthleteList.addAll(list[0].athletes)
                } else{

                    var filteredList:  MutableList<Data> = mutableListOf()

                    list[0].athletes.filter {
                        (it.name.lowercase().contains(constraint.toString().lowercase().trim()))
                    }.forEach{ filteredList.add(it)}
                    filteredAthleteList = filteredList
                }

                return FilterResults().apply { values = filteredAthleteList }

            }
            override fun publishResults(constraint: CharSequence, results: FilterResults?) {
                if (results!!.values != null) {
                    filteredAthleteList = results.values as MutableList<Data>
                    notifyDataSetChanged()
                }

            }
        }
    }
}


