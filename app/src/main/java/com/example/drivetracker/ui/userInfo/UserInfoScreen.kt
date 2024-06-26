package com.example.drivetracker.ui.userInfo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.drivetracker.R
import com.example.drivetracker.data.items.CarItem
import com.example.drivetracker.data.items.TruckItem
import com.example.drivetracker.data.records.CarRecord
import com.example.drivetracker.data.records.TruckRecord
import com.example.drivetracker.ui.RentWheelsScreen
import com.example.drivetracker.ui.order.CustomBottomAppBar
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun UserInfoScreen(
    navHostController: NavHostController,
    viewModel: UserInfoViewModel,
    onCarClick:(CarItem)->Unit,
    onTruckClick:(TruckItem)->Unit
){
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Інформація про користувача",
                fontSize = MaterialTheme.typography.displaySmall.fontSize,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(20.dp)
            )
            Card {
                Row {
                    Image(
                        painter = painterResource(id = R.drawable.user_icon),
                        contentDescription = "User image",
                        modifier = Modifier
                            .size(125.dp)
                            .clip(CircleShape)
                    )
                    Text(
                        text = viewModel.getUserEmail(),
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(20.dp)
                    )
                }
            }
            
            Button(
                onClick = { 
                    viewModel.exit() 
                    navHostController.navigate(RentWheelsScreen.LogIn.name)
                }) {
                Text(text = "Вийти")
            }
            val carList = viewModel.getCarRecords()
            val truckList = viewModel.getTruckRecords()

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 300.dp),
                modifier = Modifier
                    .height(375.dp)
                    .padding(10.dp)
            ) {
                items(carList){
                    DisplayCarRecord(
                        carRecord = it,
                        onFinish = {
                            viewModel.updateCarRecord(it)
                            viewModel.updateCar(it.carItem)
                            onCarClick.invoke(it.carItem)
                        },
                        viewModel.isCarDateEnd(it)
                    )
                }
                items(truckList){
                    DisplayTruckRecords(
                        truckRecord = it,
                        onFinish = {
                            viewModel.updateTruckRecord(it)
                            viewModel.updateTruck(it.truckItem)
                            onTruckClick.invoke(it.truckItem)
                        },
                        viewModel.isTruckDateEnd(it)
                    )
                }
            }
        }
        CustomBottomAppBar(navHostController, viewModel.isAdmin())

    }
}

@Preview
@Composable
fun UserInfoScreenPreview(){
    //DisplayCarRecord(carRecord = CarRecord())
}

@Composable
fun DisplayCarRecord(carRecord: CarRecord, onFinish:()->Unit, isEnd:Boolean){
    Card(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth()
    ) {
        Column {
            Row(Modifier.fillMaxWidth()){
                Row(
                    Modifier
                        .padding(20.dp)
                ) {
                    Text(
                        text = carRecord.carItem.car.brand+" "+carRecord.carItem.car.model,
                        fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                        modifier =  Modifier.width(165.dp),
                        lineHeight = 35.sp
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom
                ) {
                    //if(getCurrentDate()==carRecord.endRentDate)
                    Column {
                        Text(
                            text = "Видача: ${carRecord.startRentDate}"
                        )
                        Text(text = "Заверш. ${carRecord.endRentDate}")
                    }

                }
            }
            Button(onClick ={

                onFinish.invoke()
            } ) {
                Text(text = "Завершити")
            }

            if(isEnd){
                Text(text = "Термін оренди закінчився!!!!")
            }
        }
        

    }
}

@Composable
fun DisplayTruckRecords(truckRecord: TruckRecord,onFinish:()->Unit, isEnd: Boolean){
    Card(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth(),
    ) {
        Column {
            Row(Modifier.fillMaxWidth()){
                Row(
                    Modifier
                        .padding(20.dp)
                ) {
                    Text(
                        text = truckRecord.truckItem.truck.brand+" "+truckRecord.truckItem.truck.model,
                        fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                        modifier =  Modifier.width(165.dp),
                        lineHeight = 35.sp
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
                        Text(text = "Видача: ${truckRecord.startRentDate}")
                        Text(text = "Заверш. ${truckRecord.endRentDate}")
                    }

                }
            }
            Button(onClick =onFinish) {
                Text(text = "Завершити")
            }

            if(isEnd){
                Text(text = "Термін оренди закінчився!!!!")
            }
        }


    }
}


fun getCurrentDate():String{
    return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
}