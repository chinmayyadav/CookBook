package com.example.cookbook.ui.Categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.R
import com.example.cookbook.ui.MealAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DisplayDataFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DisplayDataFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_display_data, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewMeals)
        recyclerView.layoutManager = LinearLayoutManager(context)

        arguments?.let { bundle ->
            val meals = bundle.getSerializable("meals") as List<Meal>
            recyclerView.adapter = MealAdapter(meals)
        }

        return view
    }

    companion object {
        fun newInstance(meals: List<Meal>): DisplayDataFragment {
            val fragment = DisplayDataFragment()
            val args = Bundle()
            args.putSerializable("meals", ArrayList(meals)) // ArrayList for Serializable
            fragment.arguments = args
            return fragment
        }
    }
}