package com.example.drivetracker.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.drivetracker.ui.auth.LogInScreen
import com.example.drivetracker.ui.auth.SignInScreen

@Composable
fun DriveTrackerApp(
    navHostController: NavHostController = rememberNavController()
){
    val backStackEntry by navHostController.currentBackStackEntryAsState()
    val currentScreen = RentWheelsScreen.valueOf(
        backStackEntry?.destination?.route ?: RentWheelsScreen.SignIn.name
    )
    
    NavHost(
        navController = navHostController,
        startDestination = currentScreen.name
    ){
        composable(route = RentWheelsScreen.SignIn.name){
            SignInScreen(onLogInClick = {
                navHostController.navigate(RentWheelsScreen.LogIn.name)
            })
        }

        composable(route = RentWheelsScreen.LogIn.name){
            LogInScreen(onSignInClick = {
                navHostController.navigate(RentWheelsScreen.SignIn.name)
            })
        }
    }

}

enum class RentWheelsScreen{
    SignIn,
    LogIn,
    OrderVehicles,
    MyVehicles
}