package com.learning.wishlist

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.learning.wishlist.utils.NavigationScreen
import com.learning.wishlist.viewModels.WishViewModel
import com.learning.wishlist.views.HomeView
import com.learning.wishlist.views.WishView

@Composable
fun App (
    wishViewModel: WishViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = NavigationScreen.HomeScreen.route ){
        composable(NavigationScreen.HomeScreen.route) {
            HomeView(wishViewModel, navController)
        }
        composable(
            NavigationScreen.WishScreen.route + "/{id}",
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.LongType
                        defaultValue = 0L
                        nullable = false
                    }
                )
            ) {
            val id = if(it.arguments != null) it.arguments!!.getLong("id") else 0L
            WishView(wishViewModel, navController, id)
        }
    }
}