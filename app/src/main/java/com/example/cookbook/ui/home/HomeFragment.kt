package com.example.cookbook.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.R
import com.example.cookbook.databinding.FragmentHomeBinding
import com.example.cookbook.models.FirebaseRecipe
import com.example.cookbook.ui.RecyclerViewAdapter
import com.google.firebase.database.FirebaseDatabase


class HomeFragment : Fragment(), RecyclerViewAdapter.MyItemClickListener {
    private var _binding: FragmentHomeBinding? = null
    lateinit var myAdapter: RecyclerViewAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val query = FirebaseDatabase.getInstance().reference.child("recipe_data").limitToFirst(50)
        val movieList: MutableList<FirebaseRecipe> = mutableListOf()
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        query.get().addOnSuccessListener { dataSnapshot ->
            for (item in dataSnapshot.children) {
                val recipe = item.getValue(FirebaseRecipe::class.java)
                recipe?.let {
                    Log.d("FirebaseRecipe", "Recipe: $it")
                    movieList.add(it)
                }
            }
            myAdapter = RecyclerViewAdapter(movieList, true)
            myAdapter.setMyItemClickListener(this)
            val searchView: SearchView = view.findViewById(R.id.search_view)
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    myAdapter.filter(newText)
                    return true
                }
            })
            recyclerView.adapter = myAdapter
        }

        val btnFilter: Button = view.findViewById(R.id.btnFilter)
        btnFilter.setOnClickListener {
            showFilterDialog()
        }
        return view
    }
    companion object;
    override fun onItemClickedFromAdapter(movie: FirebaseRecipe) {
        val manager = activity?.supportFragmentManager
        val transaction = manager?.beginTransaction()
        transaction?.replace(R.id.nav_host_fragment_activity_main, RecipeDetailFragment.newInstance(movie))
        transaction?.addToBackStack(null)
        transaction?.commit()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun showFilterDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.filter_dialog, null)
        val dialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .create()

        val btnApplyFilter = dialogView.findViewById<Button>(R.id.btnApplyFilter)
        val checkBoxCuisineTypeMexican = dialogView.findViewById<CheckBox>(R.id.checkBoxCuisineTypeMexican)
        val checkBoxCuisineTypeItalian = dialogView.findViewById<CheckBox>(R.id.checkBoxCuisineTypeItalian)
        val checkBoxCuisineTypeAmerican = dialogView.findViewById<CheckBox>(R.id.checkBoxCuisineTypeAmerican)
        val checkBoxCuisineTypeJapanese = dialogView.findViewById<CheckBox>(R.id.checkBoxCuisineTypeJapanese)
        val checkBoxCuisineTypeIndian = dialogView.findViewById<CheckBox>(R.id.checkBoxCuisineTypeIndian)

        btnApplyFilter.setOnClickListener {
            val filterMexican = checkBoxCuisineTypeMexican.isChecked
            val filterItalian = checkBoxCuisineTypeItalian.isChecked
            val filterAmerican = checkBoxCuisineTypeAmerican.isChecked
            val filterJapanese = checkBoxCuisineTypeJapanese.isChecked
            val filterIndian = checkBoxCuisineTypeIndian.isChecked
            myAdapter.filterData(filterMexican, filterItalian, filterAmerican, filterJapanese, filterIndian)
            dialog.dismiss()
        }

        dialog.show()
    }


}