package com.example.drivetracker.ui.auth

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import com.google.firebase.Firebase
import com.google.firebase.auth.auth


//var auth = Firebase.auth

@Composable
fun SignInScreen(){
    Surface(modifier = Modifier
        .fillMaxSize()) {
        Box(contentAlignment = Alignment.Center){
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Sign up",
                    modifier = Modifier.padding(bottom = 75.dp),
                    fontSize = MaterialTheme.typography.headlineLarge.fontSize)
                var loginText by remember { mutableStateOf(TextFieldValue()) }
                OutlinedTextField(
                    value = loginText,
                    onValueChange = {
                        loginText = it
                    },
                    label={
                        Text(text = "Enter email")
                    },
                    maxLines = 1
                )
                var password by rememberSaveable { mutableStateOf("") }
                var passwordHidden1 by rememberSaveable { mutableStateOf(true) }
                var isError by rememberSaveable {
                    mutableStateOf(false)
                }
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    singleLine = true,
                    label = { Text("Enter password") },
                    visualTransformation =
                    if (passwordHidden1) PasswordVisualTransformation() else VisualTransformation.None,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        IconButton(onClick = { passwordHidden1 = !passwordHidden1 }) {
                            val visibilityIcon =
                              if (passwordHidden1) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder
                            val description = if (passwordHidden1) "Show password" else "Hide password"
                             Icon(imageVector = visibilityIcon, contentDescription = description)
                        }
                    },
                    maxLines = 1,
                    isError = isError
                )
                var password2 by rememberSaveable { mutableStateOf("") }
                var passwordHidden by rememberSaveable { mutableStateOf(true) }

                OutlinedTextField(
                    value = password2,
                    onValueChange = { password2 = it },
                    singleLine = true,
                    label = { Text("Repeat password") },
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
                    },
                    maxLines = 1,
                    isError = isError
                )

                Button(onClick = {
                     isError = password2!=password
                   /* auth.createUserWithEmailAndPassword(loginText.text, password).addOnCompleteListener {
                        if(it.isSuccessful){
                            auth.signOut()
                        } else {
                            Log.e("", it.exception.toString())
                        }
                    } */},
                    modifier = Modifier.padding(50.dp)
                ) {
                    Text("Sign up")
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