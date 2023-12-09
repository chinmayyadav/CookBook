package com.example.cookbook.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.R
import com.example.cookbook.models.FirebaseRecipe
import com.squareup.picasso.Picasso

class RecyclerViewAdapter(val items: MutableList<FirebaseRecipe>):
    RecyclerView.Adapter<RecyclerViewAdapter.MovieViewHolder>(){

    var myListener: MyItemClickListener? = null

    interface MyItemClickListener {
        fun onItemClickedFromAdapter(movie: FirebaseRecipe)
//        fun onItemLongClickedFromAdapter(movie: FirebaseRecipe)
    }
    fun setMyItemClickListener(listener: MyItemClickListener) {
        this.myListener = listener
    }

    inner class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recipeCard = view.findViewById<CardView>(R.id.cv)
        val moviePoster = view.findViewById<ImageView>(R.id.rvPoster)
        val movieTitle = view.findViewById<TextView>(R.id.rvTitle)
        val movieOverview = view.findViewById<TextView>(R.id.rvOverview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.recipe_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = items[position]
        holder.movieTitle.text = movie.title

        holder.movieOverview.text = movie.title
//        holder.moviePoster.setImageResource(movie.image!!)
        Picasso.get()
            .load(movie.imageLink)
            .into(holder.moviePoster)
//        holder.movieCard.setOnClickListener {
//            myListener!!.onItemClickedFromAdapter(movie)
//        }
//        holder.movieCard.setOnLongClickListener {
//            myListener!!.onItemLongClickedFromAdapter(movie)
//            true
//        }
    }
}