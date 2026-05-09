package com.example.dsm_desafio_03

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import android.widget.ImageView
import android.widget.EditText
import android.widget.TextView
import android.view.inputmethod.EditorInfo
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.view.inputmethod.InputMethodManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.dsm_desafio_03.viewmodel.ResourceViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val root = findViewById<View>(R.id.main)
        ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setupWithNavController(navController)

        val viewModel = ViewModelProvider(this)[ResourceViewModel::class.java]

        val btnSearch = findViewById<ImageView>(R.id.btn_search)
        val etSearch = findViewById<EditText>(R.id.et_search)
        val tvAppTitle = findViewById<TextView>(R.id.tv_app_title)

        var isSearchOpen = false

        btnSearch.setOnClickListener {
            if (!isSearchOpen) {
                // Open Search
                isSearchOpen = true
                tvAppTitle.animate().alpha(0f).setDuration(200).setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        tvAppTitle.visibility = View.GONE
                    }
                }).start()

                etSearch.alpha = 0f
                etSearch.visibility = View.VISIBLE
                etSearch.animate().alpha(1f).setDuration(200).setListener(null).start()
                etSearch.requestFocus()

                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(etSearch, InputMethodManager.SHOW_IMPLICIT)

                btnSearch.setImageResource(android.R.drawable.ic_menu_close_clear_cancel)
            } else {
                // Close Search
                isSearchOpen = false
                etSearch.animate().alpha(0f).setDuration(200).setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        etSearch.visibility = View.GONE
                        etSearch.text.clear()
                        viewModel.fetchResources(search = null)
                    }
                }).start()

                tvAppTitle.alpha = 0f
                tvAppTitle.visibility = View.VISIBLE
                tvAppTitle.animate().alpha(1f).setDuration(200).setListener(null).start()

                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(etSearch.windowToken, 0)

                btnSearch.setImageResource(android.R.drawable.ic_menu_search)
            }
        }

        etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = etSearch.text.toString().trim()
                val searchQuery = if (query.isEmpty()) null else query
                viewModel.fetchResources(search = searchQuery)

                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(etSearch.windowToken, 0)
                true
            } else {
                false
            }
        }
    }
}