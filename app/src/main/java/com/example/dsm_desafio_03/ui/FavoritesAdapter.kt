package com.example.dsm_desafio_03.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.dsm_desafio_03.R
import com.example.dsm_desafio_03.model.FavoriteResource

class FavoritesAdapter : RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {

    private var items: List<FavoriteResource> = emptyList()

    fun submitList(newItems: List<FavoriteResource>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgHeader: ImageView = itemView.findViewById(R.id.img_favorite_header)
        private val tvType: TextView = itemView.findViewById(R.id.tv_favorite_type)
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_favorite_title)
        private val tvDesc: TextView = itemView.findViewById(R.id.tv_favorite_desc)
        private val tvDate: TextView = itemView.findViewById(R.id.tv_favorite_date)

        fun bind(fav: FavoriteResource) {
            val res = fav.resource
            tvType.text = res.type?.uppercase() ?: "RECURSO"
            tvTitle.text = res.title
            tvDesc.text = res.description

            // Format date basic
            val dateStr = fav.createdAt.split("T").firstOrNull() ?: ""
            tvDate.text = "SAVED $dateStr"

            if (!res.image.isNullOrEmpty()) {
                imgHeader.load(res.image) {
                    crossfade(true)
                    placeholder(R.color.placeholder_bg)
                    error(R.color.placeholder_bg)
                }
            } else {
                imgHeader.setImageResource(R.color.placeholder_bg)
            }
        }
    }
}

