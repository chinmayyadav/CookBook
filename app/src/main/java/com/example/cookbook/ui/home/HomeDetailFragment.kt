package com.example.cookbook.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.cookbook.R
import com.example.cookbook.models.FirebaseRecipe
import com.squareup.picasso.Picasso
import java.io.Serializable

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_MOV = "recipe"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeDetailFragment : Fragment() {
    private var recipe: FirebaseRecipe? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            recipe = it.getSerializable(ARG_MOV) as FirebaseRecipe
        }

    }

    override fun onStart() {
        super.onStart()
        loadMovieInfo()
    }

    private fun loadMovieInfo() {
        val movieTitle: TextView = requireActivity().findViewById(R.id.movieTitle)
//        val movieID: TextView = requireActivity().findViewById(R.id.movieID)
//        val movieRate: TextView = requireActivity().findViewById(R.id.movieRate)
//        val movieInfo: TextView = requireActivity().findViewById(R.id.movieInfo)
//        val movieYear: TextView = requireActivity().findViewById(R.id.movieYear)
//        val movieImage: ImageView = requireActivity().findViewById(R.id.movieImage)


        movieTitle.text = recipe!!.name
//        movieID.text = recipe!!.id.toString()
//        movieRate.text = recipe!!.stars
//        movieInfo.text = recipe!!.description
//        movieYear.text = recipe!!.year
//        Picasso.get()
//            .load(recipe!!.url)
//            .into(movieImage)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: FirebaseRecipe) =
            HomeDetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_MOV, param1 as Serializable)
                }
            }
    }
}