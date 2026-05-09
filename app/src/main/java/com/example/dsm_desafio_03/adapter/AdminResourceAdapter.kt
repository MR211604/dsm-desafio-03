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

class AdminResourceAdapter(
    private var resources: List<ResourceModel> = emptyList(),
    private val onViewAsset: (Int) -> Unit,
    private val onEditAsset: (ResourceModel) -> Unit
) : RecyclerView.Adapter<AdminResourceAdapter.AdminViewHolder>() {

    fun updateData(newResources: List<ResourceModel>) {
        resources = newResources
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_admin_resource, parent, false)
        return AdminViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdminViewHolder, position: Int) {
        val resource = resources[position]
        holder.bind(resource)
    }

    override fun getItemCount(): Int = resources.size

    inner class AdminViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgResource: ImageView = itemView.findViewById(R.id.img_admin_resource)
        private val tvTypeBadge: TextView = itemView.findViewById(R.id.tv_admin_type_badge)
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_admin_title)
        private val tvDescription: TextView = itemView.findViewById(R.id.tv_admin_description)
        private val tvTag: TextView = itemView.findViewById(R.id.tv_admin_tag)
        private val tvViewAsset: TextView = itemView.findViewById(R.id.tv_admin_view_asset)
        private val tvEditAsset: ImageView = itemView.findViewById(R.id.tv_admin_edit_asset)

        fun bind(resource: ResourceModel) {
            tvTitle.text = resource.title
            tvDescription.text = resource.description
            tvTypeBadge.text = resource.type.uppercase()

            // Badge color based on type
            val badgeColor = when (resource.type.uppercase()) {
                "ARTICLE" -> R.color.badge_article
                "VIDEO" -> R.color.badge_video
                "COURSE" -> R.color.badge_course
                "BOOK" -> R.color.badge_book
                else -> R.color.chip_bg
            }
            tvTypeBadge.backgroundTintList = itemView.context.getColorStateList(badgeColor)

            // Ratings count
            val ratingsCount = resource.userRatings?.size ?: 0

            // Tag from type
            tvTag.text = resource.type.lowercase().replaceFirstChar { it.uppercase() }

            // Image
            if (resource.image != null) {
                imgResource.load(resource.image) {
                    crossfade(true)
                    placeholder(R.color.placeholder_bg)
                    error(R.color.placeholder_bg)
                }
            } else {
                imgResource.setImageResource(R.color.placeholder_bg)
            }

            tvViewAsset.setOnClickListener {
                onViewAsset(resource.id)
            }

            tvEditAsset.setOnClickListener {
                onEditAsset(resource)
            }

            itemView.setOnClickListener {
                onViewAsset(resource.id)
            }
        }
    }
}
