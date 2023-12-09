package com.example.cookbook.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.R
import com.example.cookbook.ui.Categories.Meal
import com.squareup.picasso.Picasso

class MealAdapter(private val meals: List<Meal>) : RecyclerView.Adapter<MealAdapter.MealViewHolder>() {

    class MealViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewMealName: TextView = view.findViewById(R.id.textViewMealName)
        val imageViewMealThumb: ImageView = view.findViewById(R.id.imageViewMealThumb)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_meal, parent, false)
        return MealViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = meals[position]
        holder.textViewMealName.text = meal.strMeal
        Picasso.get()
            .load(meal.strMealThumb)
            .into(holder.imageViewMealThumb)
        // Load image using Glide or Picasso
        // Example with Glide:
//        Glide.with(holder.itemView.context)
//            .load(meal.strMealThumb)
//            .into(holder.imageViewMealThumb)
    }

    override fun getItemCount() = meals.size
}
