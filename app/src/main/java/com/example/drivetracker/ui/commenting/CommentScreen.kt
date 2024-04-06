package com.example.drivetracker.ui.commenting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.drivetracker.ui.RentWheelsScreen

@Composable
fun CommentScreen(
    viewModel: CommentScreenViewModel,
    navHostController: NavHostController
){
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var textFieldValue by remember { mutableStateOf(TextFieldValue()) }
            var rating by remember { mutableStateOf(0) } // Зберігаємо оцінку користувача

            Text(text = "Дякуємо за користування\n Напишіть відгук, якщо бажаєте")

            OutlinedTextField(
                value = textFieldValue,
                onValueChange = {
                    textFieldValue = it
                }
            )

            Row {
                repeat(5) { index ->
                    Button(onClick = { rating = index + 1 }) {
                        Text(text = "★${index + 1}")
                    }
                }
            }

            Row {
                Button(onClick = { navHostController.navigate(RentWheelsScreen.OrderVehicles.name) }) {
                    Text(text = "Скасувати")
                }
                Button(onClick = {

                }) {
                    Text(text = "Підтвердити")
                }
            }
        }
    }
}


@Preview
@Composable
fun CommentScreenPreview(){
    //CommentScreen()
}