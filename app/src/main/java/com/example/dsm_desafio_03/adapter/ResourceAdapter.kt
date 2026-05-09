package com.example.dsm_desafio_03.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.dsm_desafio_03.R
import com.example.dsm_desafio_03.model.ResourceModel

class ResourceAdapter(
    private var resources: List<ResourceModel> = emptyList(),
    private val onClick: (Int) -> Unit
) : RecyclerView.Adapter<ResourceAdapter.ResourceViewHolder>() {

    fun updateData(newResources: List<ResourceModel>) {
        resources = newResources
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResourceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_resource_card, parent, false)
        return ResourceViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResourceViewHolder, position: Int) {
        val resource = resources[position]
        holder.bind(resource)
    }

    override fun getItemCount(): Int = resources.size

    inner class ResourceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgResource: ImageView = itemView.findViewById(R.id.img_resource)
        private val imgIconType: ImageView = itemView.findViewById(R.id.img_icon_type)
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        private val tvDescription: TextView = itemView.findViewById(R.id.tv_description)
        private val tvTag1: TextView = itemView.findViewById(R.id.tv_tag1)
        private val tvRating: TextView = itemView.findViewById(R.id.tv_rating)

        fun bind(resource: ResourceModel) {
            tvTitle.text = resource.title
            tvDescription.text = resource.description
            tvTag1.text = resource.type

            // Rating dummy random display, since API doesn't provide rating from user prompt.
            tvRating.text = "4.5"

            // Set type icon dummy
            val iconRes = when (resource.type.uppercase()) {
                "VIDEO" -> android.R.drawable.ic_media_play
                "BOOK" -> android.R.drawable.ic_menu_agenda
                "ARTICLE" -> android.R.drawable.ic_menu_sort_by_size
                "COURSE" -> android.R.drawable.ic_menu_manage
                else -> android.R.drawable.ic_menu_info_details
            }
            imgIconType.setImageResource(iconRes)

            if (resource.image != null) {
                imgResource.load(resource.image) {
                    crossfade(true)
                    placeholder(R.color.placeholder_bg)
                    error(R.color.placeholder_bg)
                }
            } else {
                imgResource.setImageResource(R.color.placeholder_bg)
            }

            itemView.setOnClickListener {
                onClick(resource.id)
            }
        }
    }
}
