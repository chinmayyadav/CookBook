package com.example.cookbook.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.R
import com.example.cookbook.models.FirebaseRecipe
import com.example.cookbook.ui.home.HomeFragment
import com.google.android.material.internal.ContextUtils.getActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class RecyclerViewAdapter(val items: MutableList<FirebaseRecipe>, val showFavorite: Boolean):
    RecyclerView.Adapter<RecyclerViewAdapter.MovieViewHolder>(){

    var myListener: MyItemClickListener? = null
    var context: Context? = null
    private var filteredItems: List<FirebaseRecipe> = items



    interface MyItemClickListener {
        fun onItemClickedFromAdapter(movie: FirebaseRecipe)
//        fun onItemLongClickedFromAdapter(movie: FirebaseRecipe)
    }
    fun setMyItemClickListener(listener: HomeFragment) {
        this.myListener = listener
    }

    inner class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val favoriteIcon: ImageView = view.findViewById(R.id.favoriteIcon)
        val recipeCard = view.findViewById<CardView>(R.id.cv)
        val moviePoster = view.findViewById<ImageView>(R.id.rvPoster)
        val movieTitle = view.findViewById<TextView>(R.id.rvTitle)
        val movieOverview = view.findViewById<TextView>(R.id.rvOverview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View
        view = if (showFavorite){
            layoutInflater.inflate(R.layout.recipe_item, parent, false)

        } else {
            layoutInflater.inflate(R.layout.favorite_item, parent, false)
        }
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredItems.size
    }

    override fun onViewRecycled(holder: MovieViewHolder) {
        super.onViewRecycled(holder)
        holder.recipeCard.scaleX = 1f
        holder.recipeCard.scaleY = 1f
        holder.recipeCard.rotation = 0f
        holder.recipeCard.rotationY = 0f // Reset the rotation
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = filteredItems[position]
        context = holder.itemView.context

        holder.movieTitle.text = movie.title

        holder.movieOverview.text = movie.title
        Picasso.get()
            .load(movie.imageLink)
            .into(holder.moviePoster)
        var isFlipped = false

        holder.recipeCard.setOnClickListener {
            // Perform the flip animation
            val flipDegree = if (isFlipped) 0f else 359f
            val flipAnimation = ObjectAnimator.ofFloat(holder.recipeCard, "rotationY", flipDegree)
            flipAnimation.duration = 500
            flipAnimation.interpolator = AccelerateDecelerateInterpolator()
            flipAnimation.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    // Trigger the callback after the animation ends
                    myListener?.onItemClickedFromAdapter(movie)
                }
            })

            flipAnimation.start()
            isFlipped = !isFlipped
        }
        if(showFavorite) {
            holder.favoriteIcon.setOnClickListener {
                AlertDialog.Builder(context)
                    .setTitle("Add to Favorites")
                    .setMessage("Do you want to add this recipe to your favorites?")
                    .setPositiveButton("Yes") { dialog, which ->
                        // Logic to handle adding to favorites
                        val scaleUpX = ObjectAnimator.ofFloat(holder.recipeCard, "scaleX", 1f, 1.5f)
                        val scaleUpY = ObjectAnimator.ofFloat(holder.recipeCard, "scaleY", 1f, 1.5f)
                        val rotateAnimation = ObjectAnimator.ofFloat(holder.recipeCard, "rotation", 0f, 360f)

                        val scaleDownX = ObjectAnimator.ofFloat(holder.recipeCard, "scaleX", 1.5f, 1f)
                        val scaleDownY = ObjectAnimator.ofFloat(holder.recipeCard, "scaleY", 1.5f, 1f)

                        // Configure the animations
                        scaleUpX.duration = 300
                        scaleUpY.duration = 300
                        rotateAnimation.duration = 600
                        scaleDownX.duration = 300
                        scaleDownY.duration = 300

                        // Play scale up and rotation together
                        val set = AnimatorSet()
                        set.playTogether(scaleUpX, scaleUpY, rotateAnimation)
                        set.start()

                        // After scaling and rotating, scale back down
                        set.addListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator) {
                                AnimatorSet().apply {
                                    playTogether(scaleDownX, scaleDownY)
                                    start()
                                }
                            }
                        })
                        val username =
                            getLoggedInUsername() // Implement this method to get the logged-in user's name
                        val recipeName = movie.title

                        // Store to Firebase
                        if (recipeName != null) {
                            if (username != null) {
                                storeFavoriteRecipe(username, movie)
                                holder.favoriteIcon.setImageResource(R.drawable.ic_favorite)
                            }
                        }
                    }
                    .setNegativeButton("No", null)
                    .show()

            }
        }
        if (!showFavorite) {
            holder.favoriteIcon.visibility = View.INVISIBLE
        }
//        holder.movieCard.setOnLongClickListener {
//            myListener!!.onItemLongClickedFromAdapter(movie)
//            true
//        }
    }
    private fun getLoggedInUsername(): String? {
        val user = FirebaseAuth.getInstance().currentUser
        return user?.getEmail().toString()
    }
    private fun storeFavoriteRecipe(username: String, recipeName: FirebaseRecipe) {
        // Assuming you have a Firebase database reference
        val databaseReference = FirebaseDatabase.getInstance().getReference("favorites")
        recipeName.username = username
        recipeName.type = "favorite"

        // Create a unique key for each favorite or use a specific structure if you have one
        val favoriteId = databaseReference.push().key

        favoriteId?.let {
            val favoriteData = recipeName
            databaseReference.child(it).setValue(favoriteData)
                .addOnSuccessListener {
                    Toast.makeText(context, "Favourites Saved Successfully", Toast.LENGTH_SHORT).show()
                    Log.d("RecipeAdapter", "Favorite saved successfully")
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Favourites Saved Successfully", Toast.LENGTH_SHORT).show()

                    Log.d("RecipeAdapter", "Failed to save favorite", it)
                }
        }
    }

    fun filter(query: String?) {
        filteredItems = if (query.isNullOrEmpty()) {
            items
        } else {
            val lowercaseQuery = query.lowercase()
            items.filter { it.title?.lowercase()?.contains(lowercaseQuery) == true }
        }
        notifyDataSetChanged()
    }

//    fun filterData(filterCuisine: Boolean) {
//        filteredItems = if (filterCuisine) {
//            items.filter { it.cuisineType == "Mexican" } // Replace with actual logic
//        } else {
//            items
//        }
//        notifyDataSetChanged()
//    }
fun filterData(filterMexican: Boolean, filterItalian: Boolean, filterAmerican: Boolean, filterJapanese: Boolean, filterIndian: Boolean) {
    val noFilterApplied = !(filterMexican || filterItalian || filterAmerican || filterJapanese || filterIndian)

    filteredItems = if (noFilterApplied) {
        items
    } else {
        items.filter {
            (!filterMexican || it.cuisineType == "Mexican") &&
                    (!filterItalian || it.cuisineType == "Italian") &&
                    (!filterAmerican || it.cuisineType == "American") &&
                    (!filterJapanese || it.cuisineType == "Japanese") &&
                    (!filterIndian || it.cuisineType == "Indian")
        }
    }
    notifyDataSetChanged()
}
}