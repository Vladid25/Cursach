@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.drivetracker.ui.order

import android.annotation.SuppressLint
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
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.drivetracker.data.entity.Car
import com.example.drivetracker.model.OrderVehicleUiState
import com.example.drivetracker.ui.RentWheelsScreen
import com.example.drivetracker.ui.adding.AddCarScreen
import com.example.drivetracker.ui.auth.LogInScreen
import com.example.drivetracker.ui.auth.SignInScreen
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun OrderVehicleScreen(
    navHostController: NavHostController,
    viewModel: OrderVehicleViewModel = OrderVehicleViewModel()
){
    Surface(modifier = Modifier.fillMaxSize()) {
        val car = Car(0,"AUDI", "Q7", 2019, false, 4)
        val list= mutableListOf(car)

        for(i in 1..20){
            list.add(car)
        }
        Column(Modifier.fillMaxSize()) {
            TopVehicleBar(viewModel)
            Column {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 100.dp)) {
                    items(list){item ->
                        DisplayCar(item)
                    }
                }
            }

        }
        BottomAppBarWithThreeSections(navHostController)

    }
}

@Composable
fun TopVehicleBar(
    viewModel: OrderVehicleViewModel
){
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
                    onClick = {
                        selectedTabIndex = index
                        viewModel.changeVehicle(selectedTabIndex)
                              },
                    text = { Text(text) }
                )
            }
        }
    }
}


@Composable
fun BottomAppBarWithThreeSections(
    navHostController: NavHostController
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        BottomAppBar {
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
                IconButton(onClick = { navHostController.navigate(RentWheelsScreen.AddCar.name) }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Home")

                }
                IconButton(onClick = { /* Handle action */ }) {
                    Icon(imageVector = Icons.Sharp.AccountCircle, contentDescription = "Home")
                }
            }
        }
    }

}

@Composable
fun DisplayCar(car:Car){
    Card(modifier = Modifier.padding(15.dp)) {
        Column(Modifier.padding(10.dp)) {
            Text(text = car.brand)
            Text(text = car.model)
            Text(text = car.year.toString())
            Text(text = car.numberSeats.toString())

        }
    }
}