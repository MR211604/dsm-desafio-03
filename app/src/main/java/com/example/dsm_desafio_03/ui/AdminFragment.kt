package com.example.dsm_desafio_03.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dsm_desafio_03.R
import com.example.dsm_desafio_03.adapter.AdminResourceAdapter
import com.example.dsm_desafio_03.utils.JwtUtils
import com.example.dsm_desafio_03.viewmodel.AdminViewModel
import com.example.dsm_desafio_03.model.ResourceModel
import com.example.dsm_desafio_03.model.UpdateResourceRequest
import com.google.android.material.textfield.TextInputEditText
import java.text.NumberFormat
import java.util.Locale

class AdminFragment : Fragment() {

    private lateinit var viewModel: AdminViewModel
    private lateinit var adapter: AdminResourceAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root: View = inflater.inflate(R.layout.fragment_admin, container, false)

        val tvAccessRestricted: TextView = root.findViewById(R.id.tv_access_restricted)
        val adminContent: View = root.findViewById(R.id.admin_content)

        // Validate admin role
        val sharedPreferences = requireActivity().getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("jwt_token", null)
        val userRol = JwtUtils.getUserRolFromToken(token)

        if (userRol == null || userRol != "admin") {
            tvAccessRestricted.visibility = View.VISIBLE
            adminContent.visibility = View.GONE
            return root
        }

        // User is admin — show content
        tvAccessRestricted.visibility = View.GONE
        adminContent.visibility = View.VISIBLE

        viewModel = ViewModelProvider(this)[AdminViewModel::class.java]

        // Views
        val btnCreateResource: Button = root.findViewById(R.id.btn_admin_create_resource)
        val etSearch: EditText = root.findViewById(R.id.et_admin_search)
        val rvResources: RecyclerView = root.findViewById(R.id.rv_admin_resources)
        val progressBar: ProgressBar = root.findViewById(R.id.progress_admin)
        val paginationContainer: LinearLayout = root.findViewById(R.id.pagination_container)
        val tvPaginationInfo: TextView = root.findViewById(R.id.tv_pagination_info)
        val btnPrevPage: ImageView = root.findViewById(R.id.btn_prev_page)
        val btnNextPage: ImageView = root.findViewById(R.id.btn_next_page)

        // Filter Chips
        val chipAll: TextView = root.findViewById(R.id.chip_admin_all)
        val chipBooks: TextView = root.findViewById(R.id.chip_admin_books)
        val chipVideos: TextView = root.findViewById(R.id.chip_admin_videos)
        val chipArticles: TextView = root.findViewById(R.id.chip_admin_articles)
        val chipCourses: TextView = root.findViewById(R.id.chip_admin_courses)

        // Setup RecyclerView
        adapter = AdminResourceAdapter(
            onViewAsset = { resourceId ->
                val bundle = Bundle().apply { putInt("resourceId", resourceId) }
                findNavController().navigate(R.id.action_nav_admin_to_resourceDetail, bundle)
            },
            onEditAsset = { resource ->
                showEditDialog(resource, token)
            }
        )
        rvResources.layoutManager = LinearLayoutManager(requireContext())
        rvResources.adapter = adapter

        // Observe Resources
        viewModel.resources.observe(viewLifecycleOwner) { resources ->
            adapter.updateData(resources)
        }

        // Observe Pagination
        viewModel.pagination.observe(viewLifecycleOwner) { pagination ->
            val numberFormat = NumberFormat.getNumberInstance(Locale.US)

            val start = ((pagination.page - 1) * pagination.limit) + 1
            val end = start + pagination.count - 1
            tvPaginationInfo.text = "VIEWING $start-$end OF ${numberFormat.format(pagination.total)}"
            paginationContainer.visibility = if (pagination.totalPages > 1) View.VISIBLE else View.GONE

            // Update button tints based on page position
            btnPrevPage.alpha = if (pagination.page > 1) 1.0f else 0.3f
            btnNextPage.alpha = if (pagination.page < pagination.totalPages) 1.0f else 0.3f
        }

        // Observe Loading
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            rvResources.visibility = if (isLoading) View.GONE else View.VISIBLE
        }

        // Observe Error
        viewModel.error.observe(viewLifecycleOwner) { errorMsg ->
            if (!errorMsg.isNullOrEmpty()) {
                Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.updateSuccess.observe(viewLifecycleOwner) { successMsg ->
            if (!successMsg.isNullOrEmpty()) {
                Toast.makeText(requireContext(), successMsg, Toast.LENGTH_SHORT).show()
                viewModel.clearSuccessMessage()
            }
        }

        // Create Resource setup
        btnCreateResource.setOnClickListener {
            showCreateDialog(token)
        }

        // Search
        etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = etSearch.text.toString().trim()
                val searchQuery = if (query.isEmpty()) null else query
                viewModel.fetchResources(search = searchQuery, page = 1)

                val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(etSearch.windowToken, 0)
                true
            } else {
                false
            }
        }

        // Filter Chips
        chipAll.setOnClickListener {
            viewModel.fetchResources(type = "ALL", page = 1)
            updateChipSelection(chipAll, listOf(chipBooks, chipVideos, chipArticles, chipCourses))
        }
        chipBooks.setOnClickListener {
            viewModel.fetchResources(type = "BOOK", page = 1)
            updateChipSelection(chipBooks, listOf(chipAll, chipVideos, chipArticles, chipCourses))
        }
        chipVideos.setOnClickListener {
            viewModel.fetchResources(type = "VIDEO", page = 1)
            updateChipSelection(chipVideos, listOf(chipAll, chipBooks, chipArticles, chipCourses))
        }
        chipArticles.setOnClickListener {
            viewModel.fetchResources(type = "ARTICLE", page = 1)
            updateChipSelection(chipArticles, listOf(chipAll, chipBooks, chipVideos, chipCourses))
        }
        chipCourses.setOnClickListener {
            viewModel.fetchResources(type = "COURSE", page = 1)
            updateChipSelection(chipCourses, listOf(chipAll, chipBooks, chipVideos, chipArticles))
        }

        // Pagination Buttons
        btnPrevPage.setOnClickListener { viewModel.previousPage() }
        btnNextPage.setOnClickListener { viewModel.nextPage() }

        // Initial fetch
        viewModel.fetchResources("ALL")

        return root
    }

    private fun updateChipSelection(selected: TextView, unselected: List<TextView>) {
        selected.setBackgroundResource(R.drawable.chip_outline_bg)
        selected.backgroundTintList = resources.getColorStateList(R.color.primary_light, null)
        selected.setTextColor(resources.getColor(R.color.chip_text, null))

        unselected.forEach { chip ->
            chip.setBackgroundResource(R.drawable.chip_outline_bg)
            chip.backgroundTintList = resources.getColorStateList(R.color.chip_bg, null)
            chip.setTextColor(resources.getColor(R.color.chip_text, null))
        }
    }

    private fun showCreateDialog(token: String?) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_edit_resource, null)
        // Optionally update dialog title
        val titleText = dialogView.findViewById<TextView>(R.id.tv_dialog_title)
        titleText?.text = "Crear Nuevo Recurso"

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        val etTitle = dialogView.findViewById<TextInputEditText>(R.id.et_edit_title)
        val etDesc = dialogView.findViewById<TextInputEditText>(R.id.et_edit_description)
        val etUrl = dialogView.findViewById<TextInputEditText>(R.id.et_edit_url)
        val etImg = dialogView.findViewById<TextInputEditText>(R.id.et_edit_image)
        val rgType = dialogView.findViewById<RadioGroup>(R.id.rg_edit_type)
        val btnCancel = dialogView.findViewById<Button>(R.id.btn_edit_cancel)
        val btnSave = dialogView.findViewById<Button>(R.id.btn_edit_save)
        val btnDelete = dialogView.findViewById<Button>(R.id.btn_edit_delete)

        btnSave.text = "Crear"
        btnDelete.visibility = View.GONE

        btnCancel.setOnClickListener { dialog.dismiss() }

        btnSave.setOnClickListener {
            if (token == null) {
                Toast.makeText(requireContext(), "No token found", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val title = etTitle.text.toString()
            val desc = etDesc.text.toString()
            val url = etUrl.text.toString()
            val img = etImg.text.toString()
            val selectedType = when (rgType.checkedRadioButtonId) {
                R.id.rb_type_video -> "VIDEO"
                R.id.rb_type_article -> "ARTICLE"
                R.id.rb_type_book -> "BOOK"
                R.id.rb_type_course -> "COURSE"
                else -> ""
            }

            if (title.isEmpty() || desc.isEmpty() || url.isEmpty() || selectedType.isEmpty() || img.isEmpty()) {
                Toast.makeText(requireContext(), "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val req = UpdateResourceRequest(title, desc, url, img, selectedType)
            viewModel.createResource(token, req)
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showEditDialog(resource: ResourceModel, token: String?) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_edit_resource, null)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        val etTitle = dialogView.findViewById<TextInputEditText>(R.id.et_edit_title)
        val etDesc = dialogView.findViewById<TextInputEditText>(R.id.et_edit_description)
        val etUrl = dialogView.findViewById<TextInputEditText>(R.id.et_edit_url)
        val etImg = dialogView.findViewById<TextInputEditText>(R.id.et_edit_image)
        val rgType = dialogView.findViewById<RadioGroup>(R.id.rg_edit_type)
        val btnCancel = dialogView.findViewById<Button>(R.id.btn_edit_cancel)
        val btnSave = dialogView.findViewById<Button>(R.id.btn_edit_save)
        val btnDelete = dialogView.findViewById<Button>(R.id.btn_edit_delete)

        // Pre-fill fields
        etTitle.setText(resource.title)
        etDesc.setText(resource.description)
        etUrl.setText(resource.url)
        etImg.setText(resource.image)

        when (resource.type.uppercase()) {
            "VIDEO" -> dialogView.findViewById<RadioButton>(R.id.rb_type_video).isChecked = true
            "ARTICLE" -> dialogView.findViewById<RadioButton>(R.id.rb_type_article).isChecked = true
            "BOOK" -> dialogView.findViewById<RadioButton>(R.id.rb_type_book).isChecked = true
            "COURSE" -> dialogView.findViewById<RadioButton>(R.id.rb_type_course).isChecked = true
        }

        btnDelete.visibility = View.VISIBLE

        btnCancel.setOnClickListener { dialog.dismiss() }

        btnDelete.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Eliminar recurso")
                .setMessage("¿Estás seguro de que deseas eliminar este recurso? Esta acción no se puede deshacer.")
                .setPositiveButton("Eliminar") { _, _ ->
                    if (token != null) {
                        viewModel.deleteResource(token, resource.id)
                        dialog.dismiss()
                    } else {
                        Toast.makeText(requireContext(), "No token found", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Cancelar", null)
                .show()
        }

        btnSave.setOnClickListener {
            if (token == null) {
                Toast.makeText(requireContext(), "No token found", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val title = etTitle.text.toString()
            val desc = etDesc.text.toString()
            val url = etUrl.text.toString()
            val img = etImg.text.toString()
            val selectedType = when (rgType.checkedRadioButtonId) {
                R.id.rb_type_video -> "VIDEO"
                R.id.rb_type_article -> "ARTICLE"
                R.id.rb_type_book -> "BOOK"
                R.id.rb_type_course -> "COURSE"
                else -> ""
            }

            if (title.isEmpty() || desc.isEmpty() || url.isEmpty() || selectedType.isEmpty() || img.isEmpty()) {
                Toast.makeText(requireContext(), "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val req = UpdateResourceRequest(title, desc, url, img, selectedType)
            viewModel.updateResource(token, resource.id, req)
            dialog.dismiss()
        }

        dialog.show()
    }
}
