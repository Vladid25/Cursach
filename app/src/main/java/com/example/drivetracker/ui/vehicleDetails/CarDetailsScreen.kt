package com.example.drivetracker.ui.vehicleDetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.drivetracker.ui.RentWheelsScreen

@Composable
fun CarDetailsScreen(
    viewModel: VehicleDetailsViewModel,
    navHostController: NavHostController,
    deleteCar:()->Unit
){
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

                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Орендувати")
                }
            }

        }
    }
}

@Preview
@Composable
fun CarDetailsScreenPreview(){
    //CarDetailsScreen(car = CarRecord(car= Car("Porsche", "911", 2024, 2, 340.2), uploadDate = Date()))
}