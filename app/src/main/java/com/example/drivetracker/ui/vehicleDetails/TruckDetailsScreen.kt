package com.example.drivetracker.ui.vehicleDetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.example.drivetracker.ui.RentWheelsScreen

@Composable
fun TruckDetailsScreen(
    viewModel: VehicleDetailsViewModel,
    navHostController: NavHostController
){
    val truck = viewModel.getDisplayedTruck()
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
                text = truck.truck.brand+" "+ truck.truck.model,
                fontSize = MaterialTheme.typography.displayMedium.fontSize,
                textAlign = TextAlign.Center,
                modifier =  Modifier.fillMaxWidth()
            )
            Text(
                text = "Рейтинг: " + truck.rating,
                fontSize = MaterialTheme.typography.headlineLarge.fontSize
            )
            Text(
                text = "Рік випуску: " + truck.truck.year,
                fontSize = MaterialTheme.typography.headlineLarge.fontSize
            )
            Text(
                text = "Кількість місць: " + truck.truck.cargoCapacity,
                fontSize = MaterialTheme.typography.headlineLarge.fontSize
            )
            Text(
                text = "Дата додавання: " + truck.uploadDate,
                fontSize = MaterialTheme.typography.bodyLarge.fontSize
            )
            Box(
                contentAlignment = Alignment.BottomEnd,
                modifier = Modifier.fillMaxWidth()
            ){
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Орендувати")
                }
            }

        }
    }
}

