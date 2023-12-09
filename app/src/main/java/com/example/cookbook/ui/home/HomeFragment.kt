package com.example.cookbook.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.R
import com.example.cookbook.databinding.FragmentHomeBinding
import com.example.cookbook.models.FirebaseRecipe
import com.example.cookbook.ui.RecyclerViewAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.getValue
import androidx.navigation.fragment.findNavController
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction



class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    lateinit var myAdapter: RecyclerViewAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

//        val homeViewModel =
//            ViewModelProvider(this).get(HomeViewModel::class.java)
//
//        _binding = FragmentHomeBinding.inflate(inflater, container, false)
//        val root: View = binding.root
//        return root
        val query = FirebaseDatabase.getInstance().reference.child("recipe_data").limitToFirst(50)
        val movieList: MutableList<FirebaseRecipe> = mutableListOf()
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        query.get().addOnSuccessListener { dataSnapshot ->
            for (item in dataSnapshot.children) {
                val recipe = item.getValue(FirebaseRecipe::class.java)
                recipe?.let { movieList.add(it) }
            }
            myAdapter = RecyclerViewAdapter(movieList)
            recyclerView.adapter = myAdapter
        }




//        recyclerView.adapter = RecyclerViewAdapter(movieList) // Replace 'YourAdapter' with your actual adapter class
//        myAdapter = RecyclerViewAdapter(movieList)
//        myAdapter.setMyItemClickListener(this)
//        rv.adapter = myAdapter
        // Add data to your adapter if needed

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}