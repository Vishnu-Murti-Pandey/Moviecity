package com.example.moviecity.activities

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.moviecity.MainActivity
import com.example.moviecity.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore

    private lateinit var email: String
    private lateinit var password: String
    private lateinit var name: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()

        binding.tvbtSignIn.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        binding.btSignUp.setOnClickListener {
            checkNameEmailAndPassword()
        }

    }


    private fun createNewUser() {
        if (auth.currentUser != null) {
            Toast.makeText(this, "User already registered...", Toast.LENGTH_LONG).show()
        } else {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")

                        Toast.makeText(this, "Registration Successful...", Toast.LENGTH_SHORT)
                            .show()
                        uploadUserDataOnFirebase()
                        openMainActivity()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

    }

    private fun uploadUserDataOnFirebase() {

        val userId = auth.currentUser?.uid
        val docRef = userId?.let { fStore.collection("users").document(it) }

        val userData: HashMap<String, Any> = HashMap()
        userData["fullName"] = name
        userData["email"] = email
        userData["password"] = password
        userData["uid"] = auth.currentUser?.uid.toString()

        docRef?.set(userData)?.addOnSuccessListener {
            Log.d(TAG, "User Profile is created in firebase$userId")
        }?.addOnFailureListener {
            Log.d(TAG, "User Profile is not created in firebase$userId")
        }
    }


    private fun openMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


    private fun checkNameEmailAndPassword() {

        name = binding.etName.text.toString()
        email = binding.etEmail.text.toString()
        password = binding.etPassword.text.toString()

        when {
            name.isEmpty() -> {
                Toast.makeText(this, "Name field is required...", Toast.LENGTH_SHORT).show()
            }
            email.isEmpty() -> {
                Toast.makeText(this, "Email field is required...", Toast.LENGTH_SHORT).show()
            }
            password.isEmpty() -> {
                Toast.makeText(this, "Password field is required...", Toast.LENGTH_SHORT).show()
            }
            password.length < 8 -> {
                Toast.makeText(
                    this,
                    "Password must contains at least 8 characters...",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else -> {
                createNewUser()
            }
        }

    }


}