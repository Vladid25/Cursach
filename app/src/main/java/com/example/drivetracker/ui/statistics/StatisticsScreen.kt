package com.example.drivetracker.ui.statistics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.drivetracker.data.items.CarItem
import com.example.drivetracker.data.items.TruckItem
import com.example.drivetracker.ui.RentWheelsScreen
import com.example.drivetracker.ui.order.CustomBottomAppBar

@Composable
fun StatisticScreen(
    viewModel: StatisticScreenViewModel,
    navHostController: NavHostController
){
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ){
                Row(horizontalArrangement = Arrangement.Center){
                    Text(
                        text = "Статистика",
                        fontSize = MaterialTheme.typography.headlineLarge.fontSize
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ){
                    Button(onClick = {
                        viewModel.exit()
                        navHostController.navigate(RentWheelsScreen.LogIn.name)
                    }) {
                        Text(text = "Вийти")
                    }
                }


            }
            LazyVerticalGrid(
                columns = GridCells.Adaptive(300.dp),
                modifier = Modifier
                    .height(1000.dp)
                    .padding(bottom = 80.dp)
            ) {
                items(viewModel.getCars()){
                    CarStats(carItem = it, viewModel = viewModel)
                }

                items(viewModel.getTrucks()){
                    TruckStats(truckItem = it, viewModel = viewModel)
                }
            }
        }
        CustomBottomAppBar(navHostController, viewModel.isAdmin())

    }
}

@Composable
fun CarStats(carItem: CarItem, viewModel: StatisticScreenViewModel){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Text(
            text = carItem.car.brand+" "+carItem.car.model,
            fontSize = MaterialTheme.typography.headlineMedium.fontSize,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        val text: String = if(carItem.isRented()){
            "Орендовано: " + viewModel.getCarOwner(carItem)
        } else{
            "Вільна"
        }
        Column(
            modifier = Modifier.fillMaxWidth()
        ){
            Row {
                Text(text = text)
            }

            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Кількість оренд: ${viewModel.getNumberOfRent(carItem)}")
            }

        }
    }
}

@Composable
fun TruckStats(truckItem: TruckItem, viewModel: StatisticScreenViewModel){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {

        Text(
            text = truckItem.truck.brand+" "+truckItem.truck.model,
            fontSize = MaterialTheme.typography.headlineMedium.fontSize,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        val text = if(truckItem.isRented()){
            "Орендовано: " +viewModel.getTruckOwner(truckItem)
        } else{
            "Вільна"
        }
        Column(
            modifier = Modifier.fillMaxWidth()
        ){
            Row {
                Text(text = text)
            }

            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Кількість оренд: ${viewModel.getNumberOfRent(truckItem)}")
            }

        }
    }
}