package com.example.cookbook
//
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//
//class LoginActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_login)
//    }
//}

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.example.cookbook.databinding.ActivityLoginBinding
import com.example.cookbook.SignupActivity


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {
            val email = binding.txtEmail.text.toString()
            val password = binding.txtPwd.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
            } else {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Login successful, navigate to the main activity or update UI as needed
                    //Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                    // Add navigation logic here

                    val user = auth.currentUser
                    Toast.makeText(this, "Welcome ${user?.email}", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    // If login fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun goToSignUpActivity(view: View) {
        val intent = Intent(this, SignupActivity::class.java)
        startActivity(intent)
    }

    private fun signOut() {
        FirebaseAuth.getInstance().signOut()

        // After signing out, you may want to navigate to the login screen or perform other actions
        // For example, navigate to LoginActivity
        startActivity(Intent(this, LoginActivity::class.java))

        // Finish the current activity to prevent the user from going back to the main activity using the back button
        finish()
    }
}
