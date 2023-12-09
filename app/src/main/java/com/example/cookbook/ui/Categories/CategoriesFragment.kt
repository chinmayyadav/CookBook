package com.example.cookbook.ui.Categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cookbook.databinding.FragmentCategoriesBinding

class CategoriesFragment : Fragment() {

    private var _binding: FragmentCategoriesBinding?=null
    private lateinit var categoriesViewModel: DashboardViewModel

//     This property is only valid between onCreateView and
//     onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

//        dashboardViewModel.categories.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }


//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        categoriesViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
//
//        val listView: ListView = binding.listView
//        val adapter = ArrayAdapter<Category>(
//            requireContext(),
//            R.layout.list_item_category,
//            R.id.textCategoryName, // ID of the TextView in the list item layout
//            mutableListOf() // Initial empty list
//        )
//
//        listView.adapter = adapter
//
//        // Observe the categories LiveData and update the adapter
//        categoriesViewModel.categories.observe(viewLifecycleOwner, { categories ->
//            adapter.clear() // Clear the existing items
//            adapter.addAll(categories) // Add the updated list of categories
//        })
//
//        // Set item click listener if needed
//        listView.setOnItemClickListener { _, _, position, _ ->
//            // Handle item click if needed
//            val selectedCategory = adapter.getItem(position)
//            // Do something with the selectedCategory
//        }
//    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}