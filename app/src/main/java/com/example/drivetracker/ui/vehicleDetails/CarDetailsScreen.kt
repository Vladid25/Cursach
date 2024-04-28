@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.drivetracker.ui.vehicleDetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.drivetracker.data.comments.Comment
import com.example.drivetracker.data.records.CarRecord
import com.example.drivetracker.data.records.TruckRecord
import com.example.drivetracker.ui.RentWheelsScreen
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.math.round

@Composable
fun CarDetailsScreen(
    viewModel: VehicleDetailsViewModel,
    navHostController: NavHostController,
    deleteCar:()->Unit
){
    val dialogState = remember { mutableStateOf(false) }
    val newPriceState = remember {
        mutableStateOf(false)
    }
    val car = viewModel.getDisplayedCar()
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column{
            Row{
                Row{
                    Button(onClick = { navHostController.navigate(route = RentWheelsScreen.OrderVehicles.name)}) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Arrow back")
                    }
                }
                if(viewModel.isAdmin()){
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ){
                        Button(onClick = { newPriceState.value = true}) {
                            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                        }
                    }
                }

            }

            Card(
                modifier = Modifier
                    .padding(start = 5.dp, end = 5.dp, top = 20.dp, bottom = 20.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = car.car.brand+" "+ car.car.model,
                        fontSize = MaterialTheme.typography.displayMedium.fontSize,
                        textAlign = TextAlign.Center,
                        modifier =  Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "Рейтинг: " + car.getRating()+ "★",
                        fontSize = MaterialTheme.typography.headlineMedium.fontSize
                    )
                    Text(
                        text = "Рік випуску: " + car.car.year,
                        fontSize = MaterialTheme.typography.headlineMedium.fontSize
                    )
                    Text(
                        text = "Кількість місць: " + car.car.numberSeats,
                        fontSize = MaterialTheme.typography.headlineMedium.fontSize
                    )
                    Text(
                        text = "Макс. швидкість: " + round(car.car.maxSpeed),
                        fontSize = MaterialTheme.typography.headlineMedium.fontSize
                    )
                    Text(
                        text = "Дата додавання: " + car.uploadDate,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize
                    )
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ){
                        Column {
                            Text(
                                text = car.price.toString() +" грн/день",
                                modifier = Modifier.padding(20.dp),
                                fontSize = MaterialTheme.typography.headlineMedium.fontSize
                            )
                            if(viewModel.isAdmin()){
                                Button(onClick = deleteCar) {
                                    Text(text = "Видалити")
                                }
                            }
                            else{
                                Button(onClick = { dialogState.value=true }) {
                                    Text(text = "Орендувати")
                                }
                            }
                        }
                    }
                }
            }
            LazyVerticalGrid(columns = GridCells.Adaptive(300.dp)) {
                items(car.comments){
                    DisplayComment(comment = it)
                }
            }
        }
    }
    if(newPriceState.value){
        NewPriceDialog(
            onDismiss = {
            newPriceState.value = false
        },
            onSubmit = {
                viewModel.updateCarPrice(it)
                newPriceState.value = false
            })
    }

    if(dialogState.value){
        PopupCalendar(onDismiss = { dialogState.value=false }, navHostController = navHostController, viewModel)
    }
}

@Composable
fun PopupCalendar(
    onDismiss: () -> Unit,
    navHostController: NavHostController,
    viewModel: VehicleDetailsViewModel,
    isCar:Boolean = true
) {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    val datePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                if(datePickerState.selectedDateMillis!=null){
                    selectedDate= convertMillisToLocalDate(datePickerState.selectedDateMillis!!)
                    println(selectedDate)
                    if(isCar){
                        viewModel.updateCarItem()
                        val carRecord = CarRecord(carItem = viewModel.getDisplayedCar(), ownerEmail = viewModel.getUserEmail(),
                            endRentDate = selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                        viewModel.addCarRecord(carRecord)
                    }else{
                        viewModel.updateTruckItem()
                        val truckRecord = TruckRecord(truckItem = viewModel.getDisplayedTruck(), ownerEmail = viewModel.getUserEmail(),
                            endRentDate = selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                        viewModel.addTruckRecord(truckRecord)
                    }

                    navHostController.navigate(RentWheelsScreen.OrderVehicles.name)
                }
            }) {
                Text(text = "Підтвердити")
            }
        },
        title = { Text(text = "Підтвердженя") },
        text = {
            Column {
                Spacer(modifier = Modifier.height(16.dp))
                DatePicker(
                    state = datePickerState,
                    showModeToggle = false,
                )
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(text = "Назад")
            }
        }
    )
}

fun convertMillisToLocalDate(millis: Long) : LocalDate {
    return Instant
        .ofEpochMilli(millis)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
}

@Composable
fun DisplayComment(comment: Comment){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier.padding(10.dp)
        ){
            Row{
                Text(
                    text = comment.authorEmail,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(text = "${comment.rating}★")

            }
        }
        Text(text = comment.text)
    }
}

@Composable
fun NewPriceDialog(
    onDismiss: () -> Unit,
    onSubmit: (Double) ->Unit
){
    var price by remember {
        mutableStateOf(TextFieldValue())
    }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Нова ціна") },
        text = {
            TextField(
                value = price,
                onValueChange = { price = it },
                label = { Text("Введіть нову ціну") }
            )
        },
        confirmButton = {
            Button(
                onClick = { onSubmit(price.text.toDouble()) }
            ) {
                Text("Підтвердити")
            }
        },
        dismissButton = {
            Button(
                onClick =  onDismiss
            ) {
                Text("Скасувати")
            }
        }
    )
}

