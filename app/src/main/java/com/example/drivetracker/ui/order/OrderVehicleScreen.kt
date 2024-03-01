package com.example.drivetracker.ui.order

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun OrderVehicleScreen(){
    Surface(modifier = Modifier.fillMaxSize()) {
        Box{
            val tabs = listOf("Cars", "Trucks")
            var selectedTabIndex by remember { mutableIntStateOf(0) }
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                tabs.forEachIndexed { index, text ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = {
                            selectedTabIndex = index
                        },
                        text = {
                            Text(text)
                        }
                    )
                }
            }
        }
        Box(contentAlignment = Alignment.BottomCenter){
            BottomAppBar(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Rent")
                Text("My vehicles")
                Text(text = "Account")
            }
        }
    }
}

@Preview
@Composable
fun PreviewOrderVehicleScreen(){
    OrderVehicleScreen()
}