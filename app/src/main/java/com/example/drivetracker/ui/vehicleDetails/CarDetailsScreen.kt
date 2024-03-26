@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.drivetracker.ui.vehicleDetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.drivetracker.ui.RentWheelsScreen
import java.time.LocalDate

@Composable
fun CarDetailsScreen(
    viewModel: VehicleDetailsViewModel,
    navHostController: NavHostController,
    deleteCar:()->Unit
){
    val dialogState = remember { mutableStateOf(false) }
    val car = viewModel.getDisplayedCar()
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column{
            Box {
                Button(onClick = { navHostController.navigate(route = RentWheelsScreen.OrderVehicles.name)}) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Arrow back")
                }
            }
            Text(
                text = car.car.brand+" "+ car.car.model,
                fontSize = MaterialTheme.typography.displayMedium.fontSize,
                textAlign = TextAlign.Center,
                modifier =  Modifier.fillMaxWidth()
            )
            Text(
                text = "Рейтинг: " + car.rating,
                fontSize = MaterialTheme.typography.headlineLarge.fontSize
            )
            Text(
                text = "Рік випуску: " + car.car.year,
                fontSize = MaterialTheme.typography.headlineLarge.fontSize
            )
            Text(
                text = "Кількість місць: " + car.car.numberSeats,
                fontSize = MaterialTheme.typography.headlineLarge.fontSize
            )
            Text(
                text = "Макс. швидкість: " + car.car.maxSpeed,
                fontSize = MaterialTheme.typography.headlineLarge.fontSize
            )
            Text(
                text = "Дата додавання: " + car.uploadDate,
                fontSize = MaterialTheme.typography.bodyLarge.fontSize
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ){

                Button(onClick = deleteCar) {
                    Text(text = "Видалити")
                }

                Button(onClick = { dialogState.value=true }) {
                    Text(text = "Орендувати")
                }
            }

        }

    }
    println(dialogState.value.toString())
    if(dialogState.value){
        PopupCalendar(onDismiss = { dialogState.value=false }, navHostController = navHostController)
    }
}

@Composable
fun PopupCalendar(
    onDismiss: () -> Unit,
    navHostController: NavHostController
) {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = onDismiss) {
                Text(text = "Підтвердити")
            }
        },
        title = { Text(text = "Підтвердженя") },
        text = {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                DatePicker(selectedDate = selectedDate) {
                    selectedDate = it
                }
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(text = "Назад")
            }
        }
    )
}

@Composable
fun DatePicker(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    val datePickerState = rememberDatePickerState()
    DatePicker(
        state = datePickerState,
        modifier = Modifier.fillMaxWidth()
    )
}



@Preview
@Composable
fun CarDetailsScreenPreview(){
    //CarDetailsScreen(car = CarRecord(car= Car("Porsche", "911", 2024, 2, 340.2), uploadDate = Date()))
}