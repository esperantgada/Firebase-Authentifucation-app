package com.striyank.authentification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.striyank.authentification.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Retrieve user information
        val userId = intent.getStringExtra("user_id")
        val userEmailId = intent.getStringExtra("user_email_id")

        //Display user information
        binding.userId.text = "User ID : ${userId}"
        binding.userEmail.text = "User Email : ${userEmailId}"


        //Logout when LOGOUT button is clicked
        binding.logoutButton.setOnClickListener {

            FirebaseAuth.getInstance().signOut()


            //Navigate to LoginActivity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

    }
}