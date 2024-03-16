package com.example.drivetracker.ui.adding

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.size.PixelSize
import com.example.drivetracker.data.CarRecord
import com.example.drivetracker.data.entity.Car
import com.example.drivetracker.ui.RentWheelsScreen
import com.example.drivetracker.ui.order.OrderVehicleViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.storage
import java.io.InputStream
import java.util.Date

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

            var maxSpeedText by remember { mutableStateOf(TextFieldValue()) }
            OutlinedTextField(
                value = maxSpeedText,
                onValueChange = {
                    maxSpeedText = it
                },
                label={
                    Text(text = "Enter max speed")
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
                        maxSpeed = maxSpeedText.text.toDouble(),
                    )
                    val carRecord = CarRecord(car, uploadDate = Date())
                    val carId = db.push().key!!
                    db.child(carId).setValue(carRecord)
                    navHostController.navigate(RentWheelsScreen.OrderVehicles.name)
                }
            }
            ) {
                Text(text = "Add")
            }


        }
    }


}
