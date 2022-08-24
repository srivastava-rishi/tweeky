package com.rsstudio.tweeky.ui.main.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.card.MaterialCardView
import com.rsstudio.tweeky.R
import com.rsstudio.tweeky.data.network.model.Athlete
import com.rsstudio.tweeky.data.network.model.Data
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*

class MainAdapter(
    private var context: Context,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    private var list: MutableList<Data> = mutableListOf()
    private var sortBy: Int = 0

    var logTag = "@MainAdapter"

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var tvName: TextView = view.findViewById(R.id.tvName)
        var tvScore: TextView = view.findViewById(R.id.tvScore)
        var ivRank: ImageView = view.findViewById(R.id.ivRank)
        var tvRank: TextView = view.findViewById(R.id.tvRank)
        var tvMe: TextView = view.findViewById(R.id.tvMe)
        var civUserPic: CircleImageView = view.findViewById(R.id.civUserPic)
        var mcvMain: MaterialCardView = view.findViewById(R.id.mcvMain)

        @SuppressLint("SetTextI18n", "ResourceAsColor")
        fun onBind(item: Data,position: Int) {

            tvName.text = item.name

            // assign rank
            when (position) {
                0 -> {
                    tvRank.visibility = View.GONE
                    ivRank.visibility = View.VISIBLE
                    ivRank.setImageResource(R.drawable.number_one)
                }
                1 -> {
                    tvRank.visibility = View.GONE
                    ivRank.visibility = View.VISIBLE
                    ivRank.setImageResource(R.drawable.number_two)
                }
                2 -> {
                    tvRank.visibility = View.GONE
                    ivRank.visibility = View.VISIBLE
                    ivRank.setImageResource(R.drawable.number_three)
                }
                else -> {
                    ivRank.visibility = View.GONE
                    tvRank.visibility = View.VISIBLE
                    tvRank.text = (position + 1).toString()
                }
            }

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
                mcvMain.strokeWidth = 4
                mcvMain.strokeColor = ContextCompat.getColor(context, R.color.red)
                tvMe.visibility = View.VISIBLE
            }else{
                mcvMain.strokeWidth = 0
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
            LayoutInflater.from(parent.context).inflate(R.layout.view_player_data, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val item = list[position]
        if (holder is MainAdapter.ItemViewHolder) {
            holder.mcvMain.animation = AnimationUtils.loadAnimation(context,R.anim.anim_fade_scale)
            holder.onBind(item,position)
        }
    }

    fun submitList(newList: List<Data>, sortType: Int) {
        list.clear()
        list.addAll(newList)
        sortList(sortType)
    }

    fun sortList(sortType: Int) {

      list = list.sortedByDescending {
          when (sortType) {
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
        sortBy = sortType
        notifyDataSetChanged()

    }

    fun getMyPosition() : Int {

        val data = Data(id = "010")
        val pos = list.indexOf(data)

        Log.d(logTag, "myPosition: $pos")
        return pos
    }

    override fun getItemCount(): Int {
        if (list.size != 0) {
            return list.size
        }
        return 0
    }

}


