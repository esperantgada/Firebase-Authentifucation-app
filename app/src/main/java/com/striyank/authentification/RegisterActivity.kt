package com.striyank.authentification

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import com.striyank.authentification.databinding.ActivityRegisterBinding
import kotlinx.android.synthetic.main.activity_login.*

private const val TAG = "RegisterActivty"

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerButton.setOnClickListener {
            registerNewUser()
        }

        binding.loginText.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    //Method that handles user registration
    private fun registerNewUser() {
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

                //Create and register user with email and password
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->

                        if (task.isSuccessful) {
                            val firebasUser: FirebaseUser = task.result!!.user!!

                            Toast.makeText(this, "You're registered successful",
                                Toast.LENGTH_LONG).show()

                            //Send new user to main activity
                            val intent = Intent(this, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK
                            intent.putExtra("user_id", firebasUser.uid)
                            intent.putExtra("user_email_id", email)
                            startActivity(intent)
                            finish()

                        } else {
                            //If registration is not successful, show error message
                            Toast.makeText(this, task.exception!!.message.toString(),
                                Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }
    }
}
