package com.example.prac20realtime

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.prac17api31.PwdTransformation
import com.example.prac20realtime.ui.theme.Prac20RealTimeTheme
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class SecondScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Prac20RealTimeTheme {
                Greeting()
            }
        }
    }
    @Composable
    fun Greeting() {
        var users by remember { mutableStateOf<List<Map<String, Any>>>(emptyList()) }

        LaunchedEffect(Unit) {
            val db : DatabaseReference = FirebaseDatabase.getInstance().reference
            db.child("users").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val userList =  mutableListOf <Map<String,Any>>()
                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.value as? Map<String, Any>
                        if (user != null) {
                            userList.add(user)
                        }
                    }

                    users = userList
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w("Firebase", "loadPost:onCancelled", error.toException())
                }
            })
        }


        Column(modifier = Modifier.padding(16.dp)) {
            users.forEach{ user ->
                val name = user["username"] as? String ?: "Unknown"
                val pwd = user["password"] as? String ?: "Unknown"
                Text(
                    text = "Username: $name, Password: $pwd", modifier = Modifier.padding(8.dp),
                    color = Color.Cyan)
            }
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        Prac20RealTimeTheme {
            Greeting()
        }
    }
}



