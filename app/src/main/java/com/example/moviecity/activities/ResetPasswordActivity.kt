package com.example.moviecity.activities

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.moviecity.databinding.ActivityResetPasswordBinding
import com.google.firebase.auth.FirebaseAuth


class ResetPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResetPasswordBinding
    private lateinit var email: String
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btResetPassword.setOnClickListener {
            sendResetEmail()
        }

    }

    private fun sendResetEmail() {
        email = binding.etEmail.text.toString()

        if (email.isEmpty()) {
            Toast.makeText(this, "Provide email...", Toast.LENGTH_SHORT).show()
        } else {
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Email sent...", Toast.LENGTH_LONG).show()
                        Log.d(TAG, "Email sent.")
                    }
                }

        }

    }
}