package com.example.drivetracker.ui.adding

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.drivetracker.data.entity.Car
import com.example.drivetracker.ui.RentWheelsScreen
import com.example.drivetracker.ui.order.OrderVehicleViewModel
import com.google.firebase.database.FirebaseDatabase

@Composable
fun AddCarScreen(
    viewModel: OrderVehicleViewModel,
    navHostController: NavHostController
){
    val db = FirebaseDatabase.getInstance("https://drivetracker-ecf96-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Cars")
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            var brandText by remember { mutableStateOf(TextFieldValue()) }
            OutlinedTextField(
                value = brandText,
                onValueChange = {
                    brandText = it
                },
                label={
                    Text(text = "Enter brand")
                },
                maxLines = 1
            )
            var modelText by remember { mutableStateOf(TextFieldValue()) }
            OutlinedTextField(
                value = modelText,
                onValueChange = {
                    modelText = it
                },
                label={
                    Text(text = "Enter model")
                },
                maxLines = 1
            )

            var yearText by remember { mutableStateOf(TextFieldValue()) }
            OutlinedTextField(
                value = yearText,
                onValueChange = {
                    yearText = it
                },
                label={
                    Text(text = "Enter year")
                },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            var numSeatsText by remember { mutableStateOf(TextFieldValue()) }
            OutlinedTextField(
                value = numSeatsText,
                onValueChange = {
                    numSeatsText = it
                },
                label={
                    Text(text = "Enter number of seats")
                },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
            Button(onClick = {
                if (brandText.text.isNotEmpty() && modelText.text.isNotEmpty() && yearText.text.isNotEmpty() && numSeatsText.text.isNotEmpty()) {
                    val car = Car(
                        brand = brandText.text,
                        modelText.text,
                        yearText.text.toInt(),
                        numberSeats = numSeatsText.text.toInt(),
                        rented = false
                    )
                    viewModel.addCar(car)
                    val carId = db.push().key!!
                    db.child(carId).setValue(car)
                        .addOnCompleteListener {
                            Log.e("M", "YRAAA")
                        }
                        .addOnFailureListener {
                            Log.e("M", "NOOOOO"+ it.message)
                        }
                    navHostController.navigate(RentWheelsScreen.OrderVehicles.name)
                }
            }) {
                Text(text = "Add")
            }
        }
    }
}

@Preview
@Composable
fun PreviewAddCarScreen(){
    //AddCarScreen()
}