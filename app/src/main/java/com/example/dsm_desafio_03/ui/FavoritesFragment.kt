package com.example.dsm_desafio_03.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.dsm_desafio_03.R
import com.example.dsm_desafio_03.model.FavoriteResource
import com.example.dsm_desafio_03.utils.JwtUtils
import com.example.dsm_desafio_03.viewmodel.FavoritesViewModel

class FavoritesFragment : Fragment() {

    private lateinit var viewModel: FavoritesViewModel
    private lateinit var adapter: FavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_favorites, container, false)
        viewModel = ViewModelProvider(this)[FavoritesViewModel::class.java]

        val recycler = root.findViewById<RecyclerView>(R.id.recycler_favorites)
        val progress = root.findViewById<ProgressBar>(R.id.progress_favorites)

        adapter = FavoritesAdapter()
        recycler.adapter = adapter

        viewModel.favorites.observe(viewLifecycleOwner) { favorites ->
            adapter.submitList(favorites)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            progress.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMsg ->
            if (!errorMsg.isNullOrEmpty()) {
                Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show()
            }
        }

        val sharedPreferences = requireActivity().getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("jwt_token", null)
        val userId = JwtUtils.getUserIdFromToken(token)

        if (userId != null) {
            viewModel.fetchFavorites(userId)
        } else {
            Toast.makeText(requireContext(), "Inicie sesión para ver sus favoritos", Toast.LENGTH_SHORT).show()
        }

        return root
    }
}

