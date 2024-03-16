@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.drivetracker.ui.order

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.sharp.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.drivetracker.data.CarRecord
import com.example.drivetracker.data.VehicleRepository
import com.example.drivetracker.data.entity.Car
import com.example.drivetracker.data.entity.Truck
import com.example.drivetracker.model.OrderVehicleUiState
import com.example.drivetracker.ui.RentWheelsScreen

@Composable
fun OrderVehicleScreen(
    navHostController: NavHostController,
    viewModel: OrderVehicleViewModel = remember { OrderVehicleViewModel(VehicleRepository()) }
) {
    val uiState by viewModel.uiState.collectAsState()
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            TopVehicleBar(viewModel)
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 300.dp)
            ) {
                if (uiState.isTruck) {
                    items(viewModel.getTrucks()) { truck ->
                        DisplayTruck(truck = truck)
                    }
                } else {
                    items(viewModel.getCars()) { car ->
                        DisplayCar(car)
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
) {
    val tabs = listOf("Cars", "Trucks")
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }
    viewModel.changeVehicle(selectedTabIndex)
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
                IconButton(onClick = { /* Handle home action */ }) {
                    Icon(imageVector = Icons.Default.Home, contentDescription = "Home")
                }
                IconButton(onClick = { navHostController.navigate(RentWheelsScreen.AddCar.name) }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                }
                IconButton(onClick = { /* Handle account action */ }) {
                    Icon(imageVector = Icons.Sharp.AccountCircle, contentDescription = "Account")
                }
            }
        }
    }
}

@Composable
fun DisplayCar(carRecord: CarRecord) {
    Card(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth()
    ) {
        Column(Modifier.padding(10.dp)) {
            Text(text = carRecord.car.brand)
            Text(text = carRecord.car.model)
            Text(text = carRecord.car.year.toString())
            Text(text = carRecord.car.numberSeats.toString())

        }
    }
}

@Composable
fun DisplayTruck(truck: Truck) {
    Card(modifier = Modifier.padding(15.dp)) {
        Column(Modifier.padding(10.dp)) {
            Text(text = truck.brand)
            Text(text = truck.model)
            Text(text = truck.year.toString())
            Text(text = truck.cargoCapacity.toString())
        }
    }
}
