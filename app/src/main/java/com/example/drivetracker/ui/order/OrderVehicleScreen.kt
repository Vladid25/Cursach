@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.drivetracker.ui.order

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.sharp.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.drivetracker.data.entity.Car

@Composable
fun OrderVehicleScreen(){
    Surface(modifier = Modifier.fillMaxSize()) {
        val car = Car(0,"AUDI", "Q7", 2019, false, 4)
        val list = listOf(car, car, car, car,car, car, car, car,car)
        Column(Modifier.fillMaxSize()) {
            TopVehicleBar()
            LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 100.dp)) {
                items(list){item ->
                    DisplayCar(item)
                }
            }
            BottomAppBarWithThreeSections()
        }

    }
}

@Preview
@Composable
fun PreviewOrderVehicleScreen(){
    OrderVehicleScreen()
}

@Composable
fun TopVehicleBar(){
    Column(verticalArrangement = Arrangement.Top) {
        val tabs = listOf("Cars","Trucks")
        var selectedTabIndex by remember {
            mutableIntStateOf(0)
        }
        TabRow(
            selectedTabIndex = selectedTabIndex
        ) {
            tabs.forEachIndexed { index, text ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex=index },
                    text = { Text(text) }
                )
            }
        }
    }
}

@Composable
fun BottomAppBarWithThreeSections(
    modifier: Modifier = Modifier
) {
    Column(verticalArrangement = Arrangement.Bottom, modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            IconButton(onClick = { /* Handle action */ }) {
                Icon(imageVector = Icons.Default.Home, contentDescription = "Home")
            }
            IconButton(onClick = { /* Handle action */ }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Home")

            }
            IconButton(onClick = { /* Handle action */ }) {
                Icon(imageVector = Icons.Sharp.AccountCircle, contentDescription = "Home")
            }
        }
    }
}

@Composable
fun DisplayCar(car:Car){
    Card(onClick = {}) {
        Column(Modifier.padding(20.dp)) {
            Text(text = car.brand)
            Text(text = car.model)
            Text(text = car.year.toString())
            Text(text = car.numberSeats.toString())

        }
    }
}