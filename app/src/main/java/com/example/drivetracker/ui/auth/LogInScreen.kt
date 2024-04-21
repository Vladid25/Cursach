package com.example.drivetracker.ui.auth

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LogInScreen(
    onSignInClick: ()->Unit,
    auth:FirebaseAuth,
    onLogInClick: ()->Unit
){
    val context = LocalContext.current
    Surface(modifier = Modifier
        .fillMaxSize()) {
        Box(contentAlignment = Alignment.Center){
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                var password by rememberSaveable { mutableStateOf("") }
                var passwordHidden1 by rememberSaveable { mutableStateOf(true) }
                Text(
                    text = "Log in",
                    modifier = Modifier.padding(bottom = 75.dp),
                    fontSize = MaterialTheme.typography.headlineLarge.fontSize)
                var emailText by remember { mutableStateOf(TextFieldValue()) }
                OutlinedTextField(
                    value = emailText,
                    onValueChange = {
                        emailText = it
                    },
                    label={
                        Text(text = "Enter email")
                    },
                    maxLines = 1
                )

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
                )
                Button(
                    onClick ={
                        if(emailText.text.isEmpty()||password.isEmpty()){
                            Toast.makeText(context, "Заповніть всі поля!", Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        if(!emailText.text.contains('@')){
                            Toast.makeText(context, "Неправильний формат пошти!", Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        if(password.length<6){
                            Toast.makeText(context, "Пароль має бути 6 або більше знаків!", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        auth.signInWithEmailAndPassword(emailText.text, password).addOnCompleteListener{
                            if(it.isSuccessful){
                                onLogInClick.invoke()
                            }else{
                                if(it.exception.toString().contains("network")){
                                    Toast.makeText(context, "Відсутнє підключення до інтернету!", Toast.LENGTH_SHORT).show()
                                }else{
                                    Toast.makeText(context, "Такий користувач відсутній!", Toast.LENGTH_SHORT).show()

                                }
                            }
                        }
                    },
                    modifier = Modifier.padding(top = 50.dp, bottom = 25.dp)) {
                    Text("Log in")
                }
                Text(
                    "or",
                    fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                    modifier = Modifier.padding(bottom = 25.dp)
                )
                Button(onClick = onSignInClick
                ) {
                    Text("Sign up")
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewLogInScreen(){
    //LogInScreen({})
}