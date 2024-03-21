package com.example.drivetracker.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.drivetracker.data.VehicleRepository
import com.example.drivetracker.ui.adding.AddCarScreen
import com.example.drivetracker.ui.adding.AddTruckScreen
import com.example.drivetracker.ui.auth.LogInScreen
import com.example.drivetracker.ui.auth.SignInScreen
import com.example.drivetracker.ui.order.OrderVehicleScreen
import com.example.drivetracker.ui.order.OrderVehicleViewModel
import com.example.drivetracker.ui.vehicleDetails.CarDetailsScreen
import com.example.drivetracker.ui.vehicleDetails.TruckDetailsScreen
import com.example.drivetracker.ui.vehicleDetails.VehicleDetailsViewModel
import com.google.firebase.Firebase
import com.google.firebase.appcheck.appCheck
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import com.google.firebase.auth.auth
import com.google.firebase.initialize

@Composable
fun DriveTrackerApp(
    navHostController: NavHostController = rememberNavController(),
    viewModel: OrderVehicleViewModel = remember { OrderVehicleViewModel(VehicleRepository()) }
){
    val backStackEntry by navHostController.currentBackStackEntryAsState()
    val currentScreen = RentWheelsScreen.valueOf(
        backStackEntry?.destination?.route ?: RentWheelsScreen.LogIn.name
    )
    Firebase.initialize(context = LocalContext.current)
    Firebase.appCheck.installAppCheckProviderFactory(
        PlayIntegrityAppCheckProviderFactory.getInstance()
    )
    val detailsViewModel = remember {
        VehicleDetailsViewModel()
    }
    val auth = Firebase.auth
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
            OrderVehicleScreen(
                navHostController,
                viewModel,
                onCarClicked = {
                    detailsViewModel.setCar(it)
                    navHostController.navigate(RentWheelsScreen.CarDetails.name)
                },
                onTruckClicked = {
                    detailsViewModel.setTruck(it)
                    navHostController.navigate(RentWheelsScreen.TruckDetails.name)
                })
        }

        composable(route = RentWheelsScreen.AddCar.name){
            AddCarScreen(viewModel, navHostController)
        }

        composable(route = RentWheelsScreen.AddTruck.name){
            AddTruckScreen(viewModel, navHostController)
        }

        composable(route = RentWheelsScreen.CarDetails.name){
            CarDetailsScreen(
                viewModel = detailsViewModel,
                navHostController = navHostController
            )
        }
        composable(route = RentWheelsScreen.TruckDetails.name){
            TruckDetailsScreen(
                viewModel = detailsViewModel,
                navHostController = navHostController
            )
        }
    }

}

enum class RentWheelsScreen{
    SignIn,
    LogIn,
    OrderVehicles,
    MyVehicles,
    AddCar,
    AddTruck,
    CarDetails,
    TruckDetails
}