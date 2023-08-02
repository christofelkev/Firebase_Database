package com.chrivin.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.chrivin.firebase.databinding.ActivityAddUsersBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddUsersActivity : AppCompatActivity() {

    lateinit var addUsersBinding : ActivityAddUsersBinding

    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
    val myReference : DatabaseReference = database.reference.child("myUsers")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addUsersBinding = ActivityAddUsersBinding.inflate(layoutInflater)
        val view = addUsersBinding.root
        setContentView(view)

        supportActionBar?.title = "Add User"

        addUsersBinding.buttonAddUser.setOnClickListener {
            addUserToDatabase()
        }
    }

    fun addUserToDatabase(){
        val name : String = addUsersBinding.editTextName.text.toString()
        val age : Int = addUsersBinding.editTextAge.text.toString().toInt()
        val email : String = addUsersBinding.editTextEmail.text.toString()

        val id : String = myReference.push().key.toString()

        val user = Users(id,name,age,email)

        myReference.child(id).setValue(user).addOnCompleteListener { task ->
            if (task.isSuccessful){
                Toast.makeText(applicationContext
                    ,"The user has been added to the database"
                    ,Toast.LENGTH_SHORT).show()
                finish()
            }else{
                Toast.makeText(applicationContext
                    ,task.exception.toString()
                    ,Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}