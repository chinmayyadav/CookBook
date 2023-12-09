package com.example.cookbook

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.cookbook.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import android.content.Intent
import android.util.Log

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        auth = FirebaseAuth.getInstance()


        binding = ActivityMainBinding.inflate(layoutInflater)

//        setContentView(binding.root)


        //FirebaseAuth.getInstance().signOut()

        if (auth.currentUser == null) {
            // User is not logged in, launch LoginActivity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

//        if (auth.currentUser != null) {
//            // User is already logged in, launch MainActivity
//            startActivity(Intent(this, MainActivity::class.java))
//            finish()
//            return
//        }
//
//        else {
//
//            // User not logged in, launch LoginActivity
//            startActivity(Intent(this, LoginActivity::class.java))
//            finish()
//
//        }
        setContentView(binding.root)
        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_categories, R.id.navigation_wishlist, R.id.navigation_add_new_recipe
            )
            )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onBackPressed() {
        Log.d("MainActivity", "Back stack count: ${supportFragmentManager.backStackEntryCount}")
//        if (supportFragmentManager.backStackEntryCount > 0) {
//            supportFragmentManager.popBackStack()
//        } else {
//            super.onBackPressed()
//        }
        if (supportFragmentManager.findFragmentByTag("specificFragmentTag") != null) {
            // Pop back to a particular fragment
            supportFragmentManager.popBackStack("targetFragmentTag", 0)
        } else {
            super.onBackPressed()
        }
    }
}