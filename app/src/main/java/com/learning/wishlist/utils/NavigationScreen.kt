package com.learning.wishlist.utils

sealed class NavigationScreen (val route: String) {
    data object HomeScreen: NavigationScreen("home_screen")
    data object WishScreen: NavigationScreen("wish_screen")
}