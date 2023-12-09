package com.example.cookbook.ui.Wishlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.R
import com.example.cookbook.databinding.FragmentHomeBinding
import com.example.cookbook.databinding.FragmentWishlistBinding
import com.example.cookbook.models.FirebaseRecipe
import com.example.cookbook.ui.RecyclerViewAdapter
import com.example.cookbook.ui.home.RecipeDetailFragment
import com.google.firebase.database.FirebaseDatabase

class WishlistFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    lateinit var myAdapter: RecyclerViewAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val query = FirebaseDatabase.getInstance().reference.child("favorites").limitToFirst(50)
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
            myAdapter = RecyclerViewAdapter(movieList, false)
            recyclerView.layoutManager = GridLayoutManager(context, 2)
//            myAdapter.setMyItemClickListener(this)
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
        return view
    }
    companion object;
//    override fun onItemClickedFromAdapter(movie: FirebaseRecipe) {
//        val manager = activity?.supportFragmentManager
//        val transaction = manager?.beginTransaction()
//        transaction?.replace(R.id.nav_host_fragment_activity_main, RecipeDetailFragment.newInstance(movie))
//        transaction?.addToBackStack(null)
//        transaction?.commit()
//    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}