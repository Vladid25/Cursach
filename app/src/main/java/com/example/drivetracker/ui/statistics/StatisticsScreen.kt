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
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.drivetracker.data.items.CarItem
import com.example.drivetracker.data.items.TruckItem
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
            Text(text = "Статистика")
            LazyVerticalGrid(
                columns = GridCells.Adaptive(300.dp),
                modifier = Modifier
                    .height(1000.dp)
                    .padding(10.dp)
            ) {
                items(viewModel.getCars()){
                    CarStats(carItem = it, rentCount = viewModel.getNumberOfRent(it))
                }

                items(viewModel.getTrucks()){
                    TruckStats(truckItem = it, rentCount = viewModel.getNumberOfRent(it))
                }
            }
        }
        CustomBottomAppBar(navHostController, viewModel.isAdmin())

    }
}

@Composable
fun CarStats(carItem: CarItem, rentCount:Int){
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = carItem.car.brand+" "+carItem.car.model)
        var text = ""
        text = if(carItem.isRented()){
            "Орендовано"
        } else{
            "Вільна"
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ){
            Row {
                Text(text = text)
            }

            Row(
                horizontalArrangement = Arrangement.End
            ) {
                Text(text = "Кількість оренд: $rentCount")
            }

        }
    }
}

@Composable
fun TruckStats(truckItem: TruckItem, rentCount:Int){
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(text = truckItem.truck.brand+" "+truckItem.truck.model)
        val text = if(truckItem.isRented()){
            "Орендовано"
        } else{
            "Вільна"
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ){
            Row {
                Text(text = text)
            }

            Row(
                horizontalArrangement = Arrangement.End
            ) {
                Text(text = "Кількість оренд: $rentCount")
            }

        }
    }
}