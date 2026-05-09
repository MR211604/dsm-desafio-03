package com.example.dsm_desafio_03.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dsm_desafio_03.R
import com.example.dsm_desafio_03.adapter.ResourceAdapter
import com.example.dsm_desafio_03.viewmodel.ResourceViewModel

class HomeFragment : Fragment() {

    private lateinit var viewModel: ResourceViewModel
    private lateinit var adapter: ResourceAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root: View = inflater.inflate(R.layout.fragment_home, container, false)

        viewModel = ViewModelProvider(requireActivity())[ResourceViewModel::class.java]

        val rvResources: RecyclerView = root.findViewById(R.id.rv_resources)
        val progressBar: ProgressBar = root.findViewById(R.id.progress_bar)

        adapter = ResourceAdapter { resourceId ->
            val bundle = Bundle().apply { putInt("resourceId", resourceId) }
            findNavController().navigate(R.id.action_nav_explore_to_resourceDetail, bundle)
        }
        rvResources.layoutManager = LinearLayoutManager(requireContext())
        rvResources.adapter = adapter

        // Setup Chips (Filters)
        val chipAll: TextView = root.findViewById(R.id.chip_all)
        val chipBooks: TextView = root.findViewById(R.id.chip_books)
        val chipVideos: TextView = root.findViewById(R.id.chip_videos)
        val chipArticles: TextView = root.findViewById(R.id.chip_articles)
        val chipCourses: TextView = root.findViewById(R.id.chip_courses)

        viewModel.resources.observe(viewLifecycleOwner) { resources ->
            adapter.updateData(resources)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            rvResources.visibility = if (isLoading) View.GONE else View.VISIBLE
        }

        chipAll.setOnClickListener {
            viewModel.fetchResources("ALL")
            updateChipSelection(chipAll, listOf(chipBooks, chipVideos, chipArticles, chipCourses))
        }
        chipBooks.setOnClickListener {
            viewModel.fetchResources("BOOK")
            updateChipSelection(chipBooks, listOf(chipAll, chipVideos, chipArticles, chipCourses))
        }
        chipVideos.setOnClickListener {
            viewModel.fetchResources("VIDEO")
            updateChipSelection(chipVideos, listOf(chipAll, chipBooks, chipArticles, chipCourses))
        }
        chipArticles.setOnClickListener {
            viewModel.fetchResources("ARTICLE")
            updateChipSelection(chipArticles, listOf(chipAll, chipBooks, chipVideos, chipCourses))
        }
        chipCourses.setOnClickListener {
            viewModel.fetchResources("COURSE")
            updateChipSelection(chipCourses, listOf(chipAll, chipBooks, chipVideos, chipArticles))
        }

        // Fetch initially
        viewModel.fetchResources("ALL")

        return root
    }

    private fun updateChipSelection(selected: TextView, unselected: List<TextView>) {
        selected.setBackgroundResource(R.drawable.chip_outline_bg)
        // Tint to Primary Light for selected
        selected.backgroundTintList = resources.getColorStateList(R.color.primary_light, null)
        selected.setTextColor(resources.getColor(R.color.chip_text, null))

        unselected.forEach { chip ->
            chip.setBackgroundResource(R.drawable.chip_outline_bg)
            chip.backgroundTintList = resources.getColorStateList(R.color.chip_bg, null)
            chip.setTextColor(resources.getColor(R.color.chip_text, null))
        }
    }
}
