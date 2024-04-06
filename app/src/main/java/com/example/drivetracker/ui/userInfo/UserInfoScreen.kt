package com.example.drivetracker.ui.userInfo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.navigation.NavHostController
import com.example.drivetracker.R
import com.example.drivetracker.data.records.CarRecord
import com.example.drivetracker.data.records.TruckRecord
import com.example.drivetracker.ui.order.BottomAppBarWithThreeSections

@Composable
fun UserInfoScreen(
    navHostController: NavHostController,
    viewModel: UserInfoViewModel
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
            
            val carList = viewModel.getCarRecords()
            carList.forEach {
                DisplayCarRecord(carRecord = it)
            }
            
            val truckList = viewModel.getTruckRecords()
            truckList.forEach { 
                DisplayTruckRecords(truckRecord = it)
            }


        }
        BottomAppBarWithThreeSections(navHostController)
    }
}

@Preview
@Composable
fun UserInfoScreenPreview(){
    DisplayCarRecord(carRecord = CarRecord())
}

@Composable
fun DisplayCarRecord(carRecord: CarRecord){
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
                    text = carRecord.carItem.car.brand+" ",
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize
                )
                Text(
                    text = carRecord.carItem.car.model,
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize
                )
            }
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Bottom
            ) {
                Column {
                    Text(
                        text = "Видача: ${carRecord.startRentDate}"
                    )
                    Text(text = "Закінчення: ${carRecord.endRentDate}")
                }

            }
        }

    }
}

@Composable
fun DisplayTruckRecords(truckRecord: TruckRecord){
    Card(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth(),
    ) {
        Row(Modifier.fillMaxWidth()){
            Row(
                Modifier
                    .padding(20.dp)
            ) {
                Text(
                    text = truckRecord.truckItem.truck.brand+" ",
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize
                )
                Text(
                    text = truckRecord.truckItem.truck.model,
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
                    Text(text = "Видача: ${truckRecord.startRentDate}")
                    Text(text = "Завершення ${truckRecord.endRentDate}")
                }

            }
        }

    }
}
