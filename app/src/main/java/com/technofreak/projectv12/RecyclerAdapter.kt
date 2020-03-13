package com.technofreak.projectv12


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

import kotlinx.android.synthetic.main.layout_blog_list_item.view.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.technofreak.projectv12.models.Albums
import kotlin.collections.ArrayList


class RecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{

    private val TAG: String = "AppDebug"

    private var items: List<Albums> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AlbumViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_blog_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {

            is AlbumViewHolder -> {
                holder.bind(items.get(position))
            }

        }
    }


    class BlogItemDiffCallback(
        var oldAlbums: List<Albums>,
        var newAmbums: List<Albums>
    ):DiffUtil.Callback(){
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldAlbums.get(oldItemPosition).imagePath == newAmbums.get(newItemPosition).imagePath
        }

        override fun getOldListSize(): Int =oldAlbums.size

        override fun getNewListSize(): Int = oldAlbums.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

            return oldAlbums.get(oldItemPosition).equals(newAmbums.get(newItemPosition))
        }

    }



    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(abbumlist: ArrayList<Albums>){

        val oldList=items
        val diffResult : DiffUtil.DiffResult=DiffUtil.calculateDiff(
            BlogItemDiffCallback(
                oldList,abbumlist
            )
        )
        items = abbumlist
        diffResult.dispatchUpdatesTo(this)



    }

    class AlbumViewHolder
    constructor(
        itemView: View
    ): RecyclerView.ViewHolder(itemView){

        val imagePath = itemView.image
        val folderNames = itemView.title
        val imgCount = itemView.details

        fun bind(album: Albums){


                Log.i("DEBUGme",""+album.imagePath)


            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(album.imagePath)
                .into(imagePath)
            folderNames.setText(album.folderNames)
            imgCount.setText("------------")

        }

    }

}
