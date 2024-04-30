package com.learning.wishlist.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.learning.wishlist.R
import com.learning.wishlist.components.TopBar
import com.learning.wishlist.models.Wish
import com.learning.wishlist.utils.NavigationScreen
import com.learning.wishlist.viewModels.WishViewModel

@Composable
fun HomeView (
    wishViewModel: WishViewModel,
    navController: NavHostController
) {
    Scaffold (
        topBar = { TopBar(stringResource(id = R.string.app_name))},
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(NavigationScreen.WishScreen.route + "/0L")
                },
                modifier = Modifier.padding(20.dp),
                contentColor = Color.White,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        },
    ) { paddingValues ->
        val wishlist = wishViewModel.getAllWishes.collectAsState(initial = listOf())
        if(wishlist.value.isNotEmpty()) {
            LazyColumn (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .offset(y = 4.dp),
            ) {
                items(wishlist.value, key = {wish -> wish.id}) { it ->
                    WishItem(
                        wish = it,
                        onClick = {
                            val id = it.id
                            navController.navigate(NavigationScreen.WishScreen.route + "/$id")
                        },
                        onDismiss = {
                            wishViewModel.deleteWish(wish = it)
                        }
                    )
                }
            }
        }else{
            Text(
                text = "No wishes yet",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .offset(y = 24.dp),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun WishItem (wish: Wish, onClick: () -> Unit, onDismiss: () -> Unit) {
    val dismissState = rememberDismissState(
        confirmValueChange = {dismissValue ->
            if(
                dismissValue == DismissValue.DismissedToEnd ||
                dismissValue == DismissValue.DismissedToStart
            ) {
                onDismiss()
            }
            true
        },
    )

    SwipeToDismiss(
        state = dismissState,
        directions = setOf(DismissDirection.StartToEnd),
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
        background = {
            Row (
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Red, ShapeDefaults.Medium),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "delete",
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }
        },
        dismissContent = {
            Card (
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                shape = ShapeDefaults.Medium,
                onClick = onClick,
            ) {
                Column (
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                ) {
                    Text(text = wish.title, fontWeight = FontWeight.ExtraBold)
                    Text(text = wish.description)
                }
            }
        }
    )
}