package com.example.anotherdice

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun DiceApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "start") {
        composable("start") { StartScreen(navController) }
        composable("roll/{dice}") { backStackEntry ->
            val dice = backStackEntry.arguments?.getString("dice")
            RollScreen(navController, dice)
        }
    }
}