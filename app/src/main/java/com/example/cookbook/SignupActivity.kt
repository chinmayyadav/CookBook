package com.example.cookbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {

    private lateinit var txtName: EditText
    private lateinit var txtEmail: EditText
    private lateinit var txtPwd: EditText
    private lateinit var btnSignUp: Button
    private lateinit var lnkLogin: TextView
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth = FirebaseAuth.getInstance()

        txtName = findViewById(R.id.txtName)
        txtEmail = findViewById(R.id.txtEmail)
        txtPwd = findViewById(R.id.txtPwd)
        btnSignUp = findViewById(R.id.btnSignUp)
        lnkLogin = findViewById(R.id.lnkLogin)

        // Set click listener for SignUp button
        btnSignUp.setOnClickListener {
            val name = txtName.text.toString().trim()
            val email = txtEmail.text.toString().trim()
            val password = txtPwd.text.toString().trim()

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                // Call function to create a new user account
                signUpUser(email, password)
            } else {
                Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show()
            }
        }

        // Set click listener for the login link
        lnkLogin.setOnClickListener {
            // Navigate to the login activity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun signUpUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign up success, update UI or navigate to the main activity
                    val user = auth.currentUser
                    Toast.makeText(this, "Registration successful for ${user?.email}", Toast.LENGTH_SHORT).show()
                    // Add additional logic or navigation here
                } else {
                    // If sign up fails, display a message to the user.
                    Toast.makeText(baseContext, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}