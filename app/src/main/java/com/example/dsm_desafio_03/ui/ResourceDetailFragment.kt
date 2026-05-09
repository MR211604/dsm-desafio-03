package com.example.dsm_desafio_03.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import android.content.Context
import org.json.JSONObject
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.dsm_desafio_03.R
import com.example.dsm_desafio_03.model.ResourceModel
import com.example.dsm_desafio_03.utils.JwtUtils
import com.example.dsm_desafio_03.viewmodel.ResourceDetailViewModel

class ResourceDetailFragment : Fragment() {

    private lateinit var viewModel: ResourceDetailViewModel
    private var resourceUrl: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root: View = inflater.inflate(R.layout.fragment_resource_detail, container, false)
        viewModel = ViewModelProvider(this)[ResourceDetailViewModel::class.java]

        val resourceId = arguments?.getInt("resourceId") ?: return root

        val imgHeader: ImageView = root.findViewById(R.id.img_detail_header)
        val btnBack: ImageView = root.findViewById(R.id.btn_back)
        val tvTag: TextView = root.findViewById(R.id.tv_detail_tag)
        val tvTitle: TextView = root.findViewById(R.id.tv_detail_title)
        val tvDesc: TextView = root.findViewById(R.id.tv_detail_desc)
        val tvRatingText: TextView = root.findViewById(R.id.tv_rating_text)
        val btnOpenResource: Button = root.findViewById(R.id.btn_open_resource)
        val btnFavorite: ImageView = root.findViewById(R.id.btn_favorite)
        val progressBar: ProgressBar = root.findViewById(R.id.progress_detail)

        val star1: ImageView = root.findViewById(R.id.star1)
        val star2: ImageView = root.findViewById(R.id.star2)
        val star3: ImageView = root.findViewById(R.id.star3)
        val star4: ImageView = root.findViewById(R.id.star4)
        val star5: ImageView = root.findViewById(R.id.star5)
        val stars = listOf(star1, star2, star3, star4, star5)

        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.resource.observe(viewLifecycleOwner) { resource ->
            resource?.let {
                tvTitle.text = it.title
                tvDesc.text = it.description
                tvTag.text = it.type
                resourceUrl = it.url

                if (!it.image.isNullOrEmpty()) {
                    imgHeader.load(it.image) {
                        crossfade(true)
                        placeholder(R.color.placeholder_bg)
                        error(R.color.placeholder_bg)
                    }
                } else {
                    imgHeader.setImageResource(R.color.placeholder_bg)
                }

                val ratings = it.userRatings
                if (ratings != null && ratings.isNotEmpty()) {
                    val avg = ratings.map { r -> r.rating }.average()
                    tvRatingText.text = "(${"%.1f".format(avg)} / ${ratings.size} Calificaciones)"
                    setStars(avg, stars)
                } else {
                    tvRatingText.text = "(0.0 / 0 Calificaciones)"
                    setStars(0.0, stars)
                }
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMsg ->
            errorMsg?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        btnFavorite.setOnClickListener {
            val sharedPreferences = requireActivity().getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
            val token = sharedPreferences.getString("jwt_token", null)
            val userId = JwtUtils.getUserIdFromToken(token)

            if (token != null && userId != null) {
                viewModel.addFavoriteResource(token, userId, resourceId)
            } else {
                Toast.makeText(requireContext(), "No session token or invalid user ID", Toast.LENGTH_SHORT).show()
            }
        }

        btnOpenResource.setOnClickListener {
            resourceUrl?.let { url ->
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(browserIntent)
            }
        }

        val ratingStar1: ImageView = root.findViewById(R.id.rating_star1)
        val ratingStar2: ImageView = root.findViewById(R.id.rating_star2)
        val ratingStar3: ImageView = root.findViewById(R.id.rating_star3)
        val ratingStar4: ImageView = root.findViewById(R.id.rating_star4)
        val ratingStar5: ImageView = root.findViewById(R.id.rating_star5)
        val btnSubmitRating: Button = root.findViewById(R.id.btn_submit_rating)
        val ratingStars = listOf(ratingStar1, ratingStar2, ratingStar3, ratingStar4, ratingStar5)

        var selectedRating = 0

        ratingStars.forEachIndexed { index, imageView ->
            imageView.setOnClickListener {
                selectedRating = index + 1
                for (i in 0..4) {
                    if (i < selectedRating) {
                        ratingStars[i].setImageResource(android.R.drawable.star_on)
                    } else {
                        ratingStars[i].setImageResource(android.R.drawable.star_off)
                    }
                }
            }
        }

        btnSubmitRating.setOnClickListener {
            if (selectedRating == 0) {
                Toast.makeText(requireContext(), "Please select a rating first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val sharedPreferences = requireActivity().getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
            val token = sharedPreferences.getString("jwt_token", null)
            val userId = JwtUtils.getUserIdFromToken(token)

            if (token != null && userId != null) {
                viewModel.rateResource(token, userId, resourceId, selectedRating)
            } else {
                Toast.makeText(requireContext(), "No session token or invalid user ID", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.fetchResourceDetail(resourceId)

        return root
    }

    private fun setStars(avg: Double, stars: List<ImageView>) {
        val fullStars = avg.toInt()
        val hasHalfStar = (avg - fullStars) >= 0.5

        for (i in 0..4) {
            when {
                i < fullStars -> stars[i].setImageResource(android.R.drawable.star_on)
                i == fullStars && hasHalfStar -> stars[i].setImageResource(android.R.drawable.star_on) // Need half star but fallback to full for simplicity if no drawable
                else -> stars[i].setImageResource(android.R.drawable.star_off)
            }
        }
    }
}
