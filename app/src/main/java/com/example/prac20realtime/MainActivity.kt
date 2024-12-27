package com.example.prac20realtime

import android.content.Intent
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.prac17api31.PwdTransformation
import com.example.prac20realtime.ui.theme.Prac20RealTimeTheme
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database

class MainActivity : ComponentActivity() {
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
        var username = remember{ mutableStateOf("")}
        var password = remember{ mutableStateOf("")}
        var repPassword = remember { mutableStateOf("") }
        var email = remember { mutableStateOf("") }
        var name = remember { mutableStateOf("") }
        var surname = remember { mutableStateOf("") }
        val db : DatabaseReference = FirebaseDatabase.getInstance().reference
        Box(
            modifier = Modifier.fillMaxSize()
        ){
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                OutlinedTextField(
                    value = name.value,
                    onValueChange = {name.value = it},
                    label = { Text("Write your name")}
                )
                OutlinedTextField(
                    value = surname.value,
                    onValueChange = {surname.value = it},
                    label = { Text("Write your surname")}
                )
                OutlinedTextField(
                    value = email.value,
                    onValueChange = {email.value = it},
                    label = { Text("Write your email")}
                )
                OutlinedTextField(
                    value = username.value,
                    onValueChange = {username.value = it},
                    label = {Text("Write your username")}
                )
                OutlinedTextField(
                    value = password.value,
                    onValueChange = {password.value = it},
                    label = { Text("Write your password")},
                    visualTransformation = PwdTransformation()
                )
                OutlinedTextField(
                    value = repPassword.value,
                    onValueChange = {repPassword.value = it},
                    label = {Text("Repite you password")},
                    visualTransformation = PwdTransformation()
                )
                Button(
                    modifier = Modifier.height(60.dp).width(120.dp),
                    content = { Text("Sign in")},
                    onClick = {
                        if (name.value.isEmpty() || surname.value.isEmpty() || email.value.isEmpty() || password.value.isEmpty() || repPassword.value.isEmpty()){
                            Toast.makeText(applicationContext, "All fields must be filled in", Toast.LENGTH_LONG)
                                .show()
                        }
                        else if(password.value != repPassword.value){
                            Toast.makeText(applicationContext, "Your password are others", Toast.LENGTH_LONG)
                                .show()
                        }
                        else{
                            val userData = hashMapOf(
                                "username" to username.value,
                                "password" to password.value
                            )
                            db.child("users").setValue(userData)
                                .addOnSuccessListener {
                                    Log.d("Firebase", "Succesfully")
                                }
                                .addOnFailureListener {
                                    Log.d("Firebase", "Error", it)
                                }
                        }
                    }
                )
                Button(
                    modifier = Modifier.height(60.dp).width(120.dp),
                    content = { Text("View all data")},
                    onClick = {
                        val intent = Intent(applicationContext, SecondScreen::class.java)
                        startActivity(intent)
                    }
                )
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

