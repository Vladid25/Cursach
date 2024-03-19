
package com.example.drivetracker.ui.order

import android.widget.Button
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.drivetracker.data.CarRecord
import com.example.drivetracker.data.TruckRecord
import com.example.drivetracker.data.VehicleRepository
import com.example.drivetracker.data.entity.Car
import com.example.drivetracker.data.entity.Truck
import com.example.drivetracker.ui.RentWheelsScreen
import java.util.Date

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
                        DisplayTruck(truckRecord = truck)
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
    val dialogState = remember { mutableStateOf(false) }

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
                IconButton(onClick = { dialogState.value=true }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                }
                IconButton(onClick = { /* Handle account action */ }) {
                    Icon(imageVector = Icons.Sharp.AccountCircle, contentDescription = "Account")
                }
            }
        }
    }
    if(dialogState.value){
        PopupWithButtons(onDismiss = {dialogState.value=false}, navHostController)
    }

}

@Composable
fun DisplayCar(carRecord: CarRecord) {
    Card(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth()
    ) {
        Row(Modifier.fillMaxWidth()){
            Row(
                Modifier
                    .padding(20.dp)
            ) {
                Text(
                    text = carRecord.car.brand+" ",
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize
                )
                Text(
                    text = carRecord.car.model,
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize
                )

            }
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = "Rating: ${carRecord.rating}")
                    Text(text = carRecord.car.year.toString())
                }

            }
        }

    }
}

@Composable
fun DisplayTruck(truckRecord: TruckRecord) {
    Card(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth()
    ) {
        Row(Modifier.fillMaxWidth()){
            Row(
                Modifier
                    .padding(20.dp)
            ) {
                Text(
                    text = truckRecord.truck.brand+" ",
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize
                )
                Text(
                    text = truckRecord.truck.model,
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize
                )

            }
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = "Rating: ${truckRecord.rating}")
                    Text(text = truckRecord.truck.year.toString())
                }

            }
        }

    }
}


@Composable
fun PopupWithButtons(
    onDismiss:()->Unit,
    navHostController: NavHostController
) {
    AlertDialog(
        onDismissRequest =onDismiss,
        confirmButton = { /*TODO*/ },
        title = { Text(text = "Вибір") },
        text = {
            Column {
                Text(text = "Виберіть тип транспорту:")
                Button(onClick = { navHostController.navigate(RentWheelsScreen.AddCar.name) }) {
                    Text(text = "Car")
                }
                Button(onClick = { navHostController.navigate(RentWheelsScreen.AddTruck.name) }) {
                    Text(text = "Truck")
                }
            }
               },
        dismissButton = {
            Button(onClick = onDismiss){
                Text(text = "Back")
            }
        }
    )
}


@Preview
@Composable
fun DisplayCarPreview(){
    DisplayCar(carRecord = CarRecord(car=Car("Porsche", "911", 2024, 2, 340.2), uploadDate = Date()))
}