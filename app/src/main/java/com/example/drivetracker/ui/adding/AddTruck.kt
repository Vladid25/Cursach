package com.example.drivetracker.ui.adding


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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavHostController
import com.example.drivetracker.data.TruckRecord
import com.example.drivetracker.data.entity.Truck
import com.example.drivetracker.ui.RentWheelsScreen
import com.example.drivetracker.ui.order.OrderVehicleViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

@Composable
fun AddTruckScreen(
    viewModel: OrderVehicleViewModel,
    navHostController: NavHostController
){
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

            var cargoCapacity by remember { mutableStateOf(TextFieldValue()) }
            OutlinedTextField(
                value = cargoCapacity,
                onValueChange = {
                    cargoCapacity = it
                },
                label={
                    Text(text = "Enter number of seats")
                },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            Button(onClick = {
                if (brandText.text.isNotEmpty() && modelText.text.isNotEmpty() && yearText.text.isNotEmpty() && cargoCapacity.text.isNotEmpty()) {
                    val truck = Truck(
                        brand = brandText.text,
                        modelText.text,
                        yearText.text.toInt(),
                        cargoCapacity = cargoCapacity.text.toDouble()
                    )
                    val truckRecord = TruckRecord(truck,
                        uploadDate = LocalDate.now().format(DateTimeFormatter
                        .ofPattern("yyyy-MM-dd")))
                    viewModel.addTruck(truckRecord)
                    navHostController.navigate(RentWheelsScreen.OrderVehicles.name)
                }
            }
            ) {
                Text(text = "Add")
            }
        }
    }


}
