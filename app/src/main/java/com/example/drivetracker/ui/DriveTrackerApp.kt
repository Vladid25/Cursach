package com.example.drivetracker.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.drivetracker.data.entity.Car
import com.example.drivetracker.ui.adding.AddCarScreen
import com.example.drivetracker.ui.auth.LogInScreen
import com.example.drivetracker.ui.auth.SignInScreen
import com.example.drivetracker.ui.order.OrderVehicleScreen
import com.example.drivetracker.ui.order.OrderVehicleViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database

@Composable
fun DriveTrackerApp(
    navHostController: NavHostController = rememberNavController(),
    viewModel: OrderVehicleViewModel = remember { OrderVehicleViewModel() }
){
    val backStackEntry by navHostController.currentBackStackEntryAsState()
    val currentScreen = RentWheelsScreen.valueOf(
        backStackEntry?.destination?.route ?: RentWheelsScreen.OrderVehicles.name
    )
    var auth = Firebase.auth
    NavHost(
        navController = navHostController,
        startDestination = currentScreen.name
    ){
        composable(route = RentWheelsScreen.SignIn.name){
            SignInScreen(
                onLogInClick = {
                navHostController.navigate(RentWheelsScreen.LogIn.name)
                },
                auth = auth
            )
        }

        composable(route = RentWheelsScreen.LogIn.name){
            LogInScreen(
                onSignInClick = {
                navHostController.navigate(RentWheelsScreen.SignIn.name)
                },
                auth=auth,
                onLogInClick = {
                    navHostController.navigate(RentWheelsScreen.OrderVehicles.name)
                })
        }

        composable(route = RentWheelsScreen.OrderVehicles.name){
            OrderVehicleScreen(navHostController, viewModel)
        }

        composable(route = RentWheelsScreen.AddCar.name){
            AddCarScreen(viewModel, navHostController)
        }
    }

}

enum class RentWheelsScreen{
    SignIn,
    LogIn,
    OrderVehicles,
    MyVehicles,
    AddCar
}