
package com.example.drivetracker.ui.order

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.sharp.AccountCircle
import androidx.compose.material.icons.sharp.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.drivetracker.data.items.CarItem
import com.example.drivetracker.data.items.TruckItem
import com.example.drivetracker.data.VehicleRepository
import com.example.drivetracker.ui.RentWheelsScreen

@Composable
fun OrderVehicleScreen(
    navHostController: NavHostController,
    viewModel: OrderVehicleViewModel = remember { OrderVehicleViewModel(VehicleRepository()) },
    onCarClicked:(CarItem)-> Unit,
    onTruckClicked:(TruckItem)->Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            TopVehicleBar(viewModel)
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 300.dp)
            ) {
                if (uiState.isTruck) {
                    items(viewModel.getEnableTrucks()) { truck ->
                        DisplayTruck(truckItem = truck, onTruckClicked)
                    }
                } else {
                    items(viewModel.getEnableCars()) { car ->
                        DisplayCar(car, onCarClicked)
                    }
                }
            }

        }
        BottomAppBarWithThreeSections(navHostController)
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun TopVehicleBar(
    viewModel: OrderVehicleViewModel
) {
    val tabs = listOf("Cars", "Trucks")
    var index = 0
    if(viewModel.uiState.value.isTruck){
        index = 1
    }else{
        index = 0
    }
    var selectedTabIndex by remember {
        mutableIntStateOf(index)
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
                IconButton(onClick = {navHostController.navigate(RentWheelsScreen.OrderVehicles.name)}) {
                    Icon(imageVector = Icons.Default.Home, contentDescription = "Home")
                }
                IconButton(onClick = { dialogState.value=true }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                }
                IconButton(onClick = { navHostController.navigate(RentWheelsScreen.MyVehicles.name) }) {
                    Icon(imageVector = Icons.Sharp.AccountCircle, contentDescription = "Account")
                }
                IconButton(onClick = { navHostController.navigate(RentWheelsScreen.StatsScreen.name) }) {
                    Icon(imageVector = Icons.Sharp.List, contentDescription = "Stats")
                }
            }
        }
    }
    if(dialogState.value){
        PopupWithButtons(onDismiss = {dialogState.value=false}, navHostController)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayCar(carItem: CarItem, onCarClicked: (CarItem) -> Unit) {
    Card(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth(),
        onClick = {
            onCarClicked(carItem)
        }
    ) {
        Row(Modifier.fillMaxWidth()){
            Row(
                Modifier
                    .padding(20.dp)
            ) {
                Text(
                    text = carItem.car.brand+" ",
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize
                )
                Text(
                    text = carItem.car.model,
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
                    Text(text = "★${carItem.getRating()}")
                    Text(text = carItem.car.year.toString()+"р.")
                    Text(text = carItem.getPrice().toString()+" грн")
                }

            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayTruck(truckItem: TruckItem, onTruckClicked: (TruckItem) -> Unit) {
    Card(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth(),
        onClick = {
            onTruckClicked(truckItem)
        }
    ) {
        Row(Modifier.fillMaxWidth()){
            Row(
                Modifier
                    .padding(20.dp)
            ) {
                Text(
                    text = truckItem.truck.brand+" ",
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize
                )
                Text(
                    text = truckItem.truck.model,
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
                    Text(text = "★${truckItem.getRating()}")
                    Text(text = truckItem.truck.year.toString()+"р.")
                    Text(text = truckItem.getPrice().toString() + " грн")
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
        confirmButton = {  },
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
    //DisplayCar(carRecord = CarRecord(car=Car("Porsche", "911", 2024, 2, 340.2), uploadDate = Date()))
}