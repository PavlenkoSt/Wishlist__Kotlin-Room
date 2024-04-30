package com.learning.wishlist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.learning.wishlist.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar (title: String, onBackPress: () -> Unit = {}, showBackButton: Boolean = false) {

    val navigationIcon: (@Composable () -> Unit)? = if(showBackButton) {
        {
            IconButton(onClick = onBackPress) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null,
                    tint = Color.White,
                )
            }
        }
    } else {
        null
    }

    if (navigationIcon != null) {
        TopAppBar(
            title = { Text(
                text = title,
                color = colorResource(id = R.color.white)
            )},
            navigationIcon = navigationIcon,
            modifier = Modifier.background(colorResource(id = R.color.primary)),
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        )
    }
}