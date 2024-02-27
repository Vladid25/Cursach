package com.example.drivetracker.ui.auth

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.auth.api.Auth
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


var auth = Firebase.auth
@Composable
fun SignInScreen(){
    Surface(modifier = Modifier
        .fillMaxSize()) {
        Box(contentAlignment = Alignment.Center){
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                var loginText by remember { mutableStateOf(TextFieldValue()) }
                OutlinedTextField(
                    value = loginText,
                    onValueChange = {
                        loginText = it
                    },
                    label={
                        Text(text = "Enter login")
                    }
                )
                var password by rememberSaveable { mutableStateOf("") }
                var passwordHidden by rememberSaveable { mutableStateOf(true) }
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    singleLine = true,
                    label = { Text("Enter password") },
                    visualTransformation =
                    if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        IconButton(onClick = { passwordHidden = !passwordHidden }) {
                            val visibilityIcon =
                              if (passwordHidden) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder
                            val description = if (passwordHidden) "Show password" else "Hide password"
                             Icon(imageVector = visibilityIcon, contentDescription = description)
                        }
                    }
                )

                Button(onClick = {
                    auth.createUserWithEmailAndPassword(loginText.text, password).addOnCompleteListener {
                        if(it.isSuccessful){
                            //Toast.makeText(Context, "YRAAA", Toast.LENGTH_SHORT)
                            auth.signOut()

                        } else{
                            Log.e("", it.exception.toString())
                        }
                    }
                }) {
                    Text("Sign in")
                }
                
            }

        }

    }
}

@Preview
@Composable
fun PreviewSignInScreen(){
    SignInScreen()
}