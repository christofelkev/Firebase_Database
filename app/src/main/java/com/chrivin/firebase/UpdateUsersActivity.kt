package com.chrivin.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.chrivin.firebase.databinding.ActivityUpdateUsersBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateUsersActivity : AppCompatActivity() {

    lateinit var updateUsersBinding : ActivityUpdateUsersBinding

    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
    val myReference : DatabaseReference = database.reference.child("myUsers")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateUsersBinding = ActivityUpdateUsersBinding.inflate(layoutInflater)
        val view = updateUsersBinding.root
        setContentView(view)

        supportActionBar?.title = "Update User"
        getAndSetData()

        updateUsersBinding.buttonUpdateUser.setOnClickListener {
            updateData()
        }


    }

    fun getAndSetData(){
        val name = intent.getStringExtra("name")
        val age = intent.getIntExtra("age",0).toString()
        val email = intent.getStringExtra("email")

        updateUsersBinding.editTextUpdateName.setText(name)
        updateUsersBinding.editTextUpdateAge.setText(age)
        updateUsersBinding.editTextUpdateEmail.setText(email)

    }

    fun updateData(){
        val updatedName = updateUsersBinding.editTextUpdateName.text.toString()
        val updatedAge = updateUsersBinding.editTextUpdateAge.text.toString().toInt()
        val updatedEmail = updateUsersBinding.editTextUpdateEmail.text.toString()
        val userId = intent.getStringExtra("id").toString()

        val userMap = mutableMapOf<String,Any>()
        userMap["userId"] = userId
        userMap["userName"] = updatedName
        userMap["userAge"] = updatedAge
        userMap["userEmail"] = updatedEmail

        myReference.child(userId).updateChildren(userMap).addOnCompleteListener { task ->
            if (task.isSuccessful){
                Toast.makeText(applicationContext,"The user has been updated",Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}