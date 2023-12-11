package com.example.cookbook.ui.Categories

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.databinding.FragmentCategoriesBinding
import com.example.cookbook.models.Category
import com.example.cookbook.ui.MealAdapter
import com.example.cookbook.ui.home.RecipeDetailFragment
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response

import retrofit2.Callback


interface MealDbService {
    @GET("filter.php")
    fun getMealsByCategory(@Query("c") category: String): Call<MealsResponse>
}

// Define a response model according to the JSON structure
data class MealsResponse(val meals: List<Meal>)
data class Meal(val idMeal: String, val strMeal: String, val strMealThumb: String)

interface BackPressHandler {
    fun onBackPressed(): Boolean
}
class CategoriesFragment : Fragment(), BackPressHandler {

    override fun onBackPressed(): Boolean {
        // Handle back press, return true if handled
        return true
    }

    private var _binding: FragmentCategoriesBinding?=null
    private lateinit var categoriesViewModel: DashboardViewModel
    private lateinit var categoryListView: ListView
    private lateinit var  recyclerViewMeals: RecyclerView

//     This property is only valid between onCreateView and
//     onDestroyView.
    private val binding get() = _binding!!
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.themealdb.com/api/json/v1/1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        categoryListView = binding.listViewCategories

//        recyclerViewMeals = binding.recyclerViewMeals
//        recyclerViewMeals.layoutManager = LinearLayoutManager(context)
        val textView: TextView = binding.textDashboard
//        dashboardViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        val categories = listOf(
            Category("1", "BreakFast"),
            Category("2", "Vegetarian"),
            Category("3", "Seafood"),
            Category("4", "Starter"),
            Category("5", "Pasta"),
            Category("6", "Dessert"),
            Category("7", "Soups"),
            // Add more categories as needed
        )

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, categories.map { it.name })
        categoryListView.adapter = adapter

        categoryListView.setOnItemClickListener { _, _, position, _ ->
            val selectedCategory = categories[position].name
            loadMealsForCategory(selectedCategory)
        }

        return root

//        val adapter = ArrayAdapter(requireContext(), R.layout.simple_list_item_1, categories.map { it.name })
//        categoryListView.adapter = adapter

//        dashboardViewModel.categories.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    private fun loadMealsForCategory(category: String) {
        val mealDbService = retrofit.create(MealDbService::class.java)
        mealDbService.getMealsByCategory(category).enqueue(object : Callback<MealsResponse> {
            override fun onResponse(call: Call<MealsResponse>, response: Response<MealsResponse>) {
                if (response.isSuccessful) {
                    Log.w("API_Response", "response.body()?.meals")
                    val meals = response.body()?.meals ?: emptyList()
                    navigateToDisplayDataFragment(meals)
//                    val manager = activity?.supportFragmentManager
//                    val transaction = manager?.beginTransaction()
////                    transaction?.replace(com.example.cookbook.R.id.nav_host_fragment_activity_main, RecipeDetailFragment.newInstance(movie))
//                    transaction?.addToBackStack(null)
//                    transaction?.commit()
                    // Update your RecyclerView with the response data
//                    updateRecyclerView(response.body()?.meals ?: emptyList())
                } else {
                    // Handle error
                }
            }

            override fun onFailure(call: Call<MealsResponse>, t: Throwable) {
                // Handle failure
            }
        })
    }
    private fun navigateToDisplayDataFragment(meals: List<Meal>) {
        val fragment = DisplayDataFragment.newInstance(meals)
        // Perform fragment transaction to display DisplayDataFragment
        // Example code for fragment transaction:
        val manager = activity?.supportFragmentManager
        val transaction = manager?.beginTransaction()
                    transaction?.replace(com.example.cookbook.R.id.nav_host_fragment_activity_main, fragment)
                    transaction?.addToBackStack("tag")
                    transaction?.commit()
//        fragmentManager?.beginTransaction()
//            ?.replace(R.id.nav_host_fragment_activity_main, fragment)
//            ?.addToBackStack(null)
//            ?.commit()
    }



//    private fun updateRecyclerView(meals: List<Meal>) {
//        val adapter = MealAdapter(meals)
//        recyclerViewMeals.adapter = adapter
//    }



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

private fun <T> Call<T>.enqueue(any: Any) {

}
