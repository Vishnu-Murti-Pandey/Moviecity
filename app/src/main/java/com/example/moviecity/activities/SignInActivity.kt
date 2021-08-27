package com.example.moviecity.activities

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.moviecity.MainActivity
import com.example.moviecity.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var email: String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.tvbtSignUp.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btSignIn.setOnClickListener {
            checkEmailAndPassword()
        }

        binding.forgetPassword.setOnClickListener {
            val intent = Intent(this, ResetPasswordActivity::class.java)
            startActivity(intent)
        }

    }


    private fun loginUser() {
        Log.d(TAG, "loginUserActivated")
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    Toast.makeText(this, "SignIn Successful...", Toast.LENGTH_SHORT).show()
                    openMainActivity()
                } else {
                    if (auth.currentUser == null) {
                        Toast.makeText(
                            this,
                            "User not Registered... Or password is wrong",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
    }


    private fun openMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


    private fun checkEmailAndPassword() {
        email = binding.etEmail.text.toString()
        password = binding.etPassword.text.toString()

        if (password.isEmpty()) {
            Toast.makeText(this, "Password field is required...", Toast.LENGTH_SHORT).show()
        } else if (email.isEmpty()) {
            Toast.makeText(this, "Email field is required...", Toast.LENGTH_SHORT).show()
        } else if (password.length < 8) {
            Toast.makeText(
                this,
                "Password must contains at least 8 characters...",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            loginUser()
        }
    }

}