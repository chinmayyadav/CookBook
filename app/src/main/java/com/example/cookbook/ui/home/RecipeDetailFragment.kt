package com.example.cookbook.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.cookbook.R
import com.example.cookbook.models.FirebaseRecipe
import com.squareup.picasso.Picasso
import java.io.Serializable


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RecipeDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val ARG_MOV = "recipe"

class RecipeDetailFragment : Fragment() {
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

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadMovieInfo() {
        val recipeTitle: TextView = requireActivity().findViewById(R.id.recipeTitle)
        val recipeIngredients: TextView = requireActivity().findViewById(R.id.recipeIngredients)
        val recipeDirections: TextView = requireActivity().findViewById(R.id.recipeDirections)
        val recipeImage: ImageView = requireActivity().findViewById(R.id.recipeImage)
        val recipeYoutubeLink: TextView = requireActivity().findViewById(R.id.recipeYoutubeLink)

        recipeTitle.text = recipe?.title
        recipeIngredients.text = recipe?.ingredients?.joinToString(separator = "\n") { "- $it" }
        recipeDirections.text = recipe?.directions?.joinToString(separator = "\n") { "* $it" }
        recipeYoutubeLink.text = recipe?.youtubeLink

        Picasso.get()
            .load(recipe?.imageLink)
            .into(recipeImage)

        val webView: WebView = requireActivity().findViewById(R.id.webview)
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.loadUrl("https://www.youtube.com/watch?v=rEohCaSZxOg");
        webView.webViewClient = loadWebView()
    }

    private class loadWebView : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_detail, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: FirebaseRecipe) =
            RecipeDetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_MOV, param1 as Serializable)
                }
            }
    }
}