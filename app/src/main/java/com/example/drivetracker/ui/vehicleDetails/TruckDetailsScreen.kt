package com.example.drivetracker.ui.vehicleDetails

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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.drivetracker.ui.RentWheelsScreen

@Composable
fun TruckDetailsScreen(
    viewModel: VehicleDetailsViewModel,
    navHostController: NavHostController,
    deleteTruck:()->Unit
){
    val truck = viewModel.getDisplayedTruck()
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        val dialogState = remember { mutableStateOf(false) }
        val newPriceState = remember {
            mutableStateOf(false)
        }
        Column{
            Row {
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
            ){
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = truck.truck.brand+" "+ truck.truck.model,
                        fontSize = MaterialTheme.typography.displayMedium.fontSize,
                        textAlign = TextAlign.Center,
                        modifier =  Modifier.fillMaxWidth(),
                        lineHeight = 40.sp
                    )

                    Text(
                        text = "Номер: " + truck.truck.registrationNumber,
                        fontSize = MaterialTheme.typography.headlineMedium.fontSize
                    )

                    Text(
                        text = "★${truck.getRating()}",
                        fontSize = MaterialTheme.typography.headlineMedium.fontSize
                    )
                    Text(
                        text = "Рік випуску: " + truck.truck.year,
                        fontSize = MaterialTheme.typography.headlineMedium.fontSize
                    )
                    Text(
                        text = "Вантажність: " + truck.truck.cargoCapacity,
                        fontSize = MaterialTheme.typography.headlineMedium.fontSize
                    )
                    Text(
                        text = "Дата додавання: " + truck.uploadDate,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize
                    )
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ){
                        Column {
                            Text(
                                text = "Застава: ${truck.pledge} грн",
                                modifier = Modifier.padding(20.dp),
                                fontSize = MaterialTheme.typography.headlineMedium.fontSize
                            )
                            Text(
                                text = truck.price.toString() +" грн/день",
                                modifier = Modifier.padding(20.dp),
                                fontSize = MaterialTheme.typography.headlineMedium.fontSize
                            )
                            if(viewModel.isAdmin()){
                                Button(onClick = deleteTruck) {
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
                items(truck.comments){
                    DisplayComment(comment = it)
                }
            }

        }

        if(newPriceState.value){
            NewPriceDialog(
                onDismiss = {
                    newPriceState.value = false
                },
                onSubmit = {
                    viewModel.updateTruckPrice(it)
                    newPriceState.value = false
                })
        }
        if(dialogState.value){
            PopupCalendar(onDismiss = { dialogState.value = false }, navHostController = navHostController, viewModel, isCar = false)
        }
    }

}

