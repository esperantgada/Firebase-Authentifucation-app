package com.striyank.authentification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.striyank.authentification.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            loginUser()
        }

        binding.registerText.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
    }

    //Method that handles user login
    private fun loginUser() {
        when {
            TextUtils.isEmpty(binding.loginEmailEditText.text.toString().trim { it <= ' ' }) ->
                Toast.makeText(this, "Please, enter your email address",
                    Toast.LENGTH_LONG).show()

            TextUtils.isEmpty(binding.loginPasswordEditText.text.toString().trim { it <= ' ' }) ->
                Toast.makeText(this, "Please, enter your password",
                    Toast.LENGTH_LONG).show()

            else -> {
                val email = binding.loginEmailEditText.text.toString().trim { it <= ' ' }
                val password = binding.loginPasswordEditText.text.toString().trim { it <= ' ' }

                //Sign in user with email and password
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->

                        if (task.isSuccessful) {
                            val firebasUser = FirebaseAuth.getInstance().currentUser!!.uid

                            Toast.makeText(this, "You're logged in successfully",
                                Toast.LENGTH_LONG).show()

                            //Send new user to main activity
                            val intent = Intent(this, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK
                            intent.putExtra("user_id", firebasUser)
                            intent.putExtra("user_email_id", email)
                            startActivity(intent)
                            finish()

                        } else {
                            //If login is not successful, show error message
                            Toast.makeText(this, task.exception!!.message.toString(),
                                Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }
    }
}