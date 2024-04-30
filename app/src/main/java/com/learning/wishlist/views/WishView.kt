package com.learning.wishlist.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.learning.wishlist.R
import com.learning.wishlist.components.TopBar
import com.learning.wishlist.models.Wish
import com.learning.wishlist.viewModels.WishViewModel
import kotlinx.coroutines.launch

@Composable
fun WishView (
    wishViewModel: WishViewModel,
    navController: NavHostController,
    wishIdToEdit: Long = 0L,
) {
    val snackMessage = remember {
        mutableStateOf("")
    }
    val coroutineScope = rememberCoroutineScope()

    if(wishIdToEdit != 0L) {
        val wish = wishViewModel.getWishById(wishIdToEdit).collectAsState(initial = Wish(0L, "", ""))
        wishViewModel.wishTitle = wish.value.title
        wishViewModel.wishDescription = wish.value.description
    }else{
        wishViewModel.resetWishFormState()
    }

    Scaffold (
         topBar = {TopBar(
             title = if (wishIdToEdit == 0L) {
                 stringResource(id = R.string.add_wish)
             }else {
                 stringResource(id = R.string.update_wish)
             },
             showBackButton = true,
             onBackPress = { navController.popBackStack() }
        )},
    ) { paddingValues ->
        Column (
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            WishTextField(
                label = "Title",
                value = wishViewModel.wishTitle,
                onValueChange = {value -> wishViewModel.onWishTitleChanged(value)},
                singleLine = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            WishTextField(
                label = "Description",
                value = wishViewModel.wishDescription,
                onValueChange = {value -> wishViewModel.onWishDescriptionChanged(value)}
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    if(wishViewModel.wishTitle.isEmpty() || wishViewModel.wishDescription.isEmpty()) {
                        snackMessage.value = "Fill all fields"
                        return@Button
                    }

                    if(wishIdToEdit != 0L) {
                        wishViewModel.updateWish(
                            Wish(
                                id = wishIdToEdit,
                                title = wishViewModel.wishTitle.trim(),
                                description = wishViewModel.wishDescription.trim()
                            )
                        )
                    }else{
                        wishViewModel.addWish(
                            Wish(
                                title = wishViewModel.wishTitle.trim(),
                                description = wishViewModel.wishDescription.trim()
                            )
                        )
                    }
                    wishViewModel.resetWishFormState()
                    coroutineScope.launch {
                        navController.navigateUp()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                )
            ) {
                Text(text = if(wishIdToEdit == 0L) "Add" else "Update", color = Color.White)
            }
        }
    }
}

@Composable
fun WishTextField (
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    singleLine: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label, color = MaterialTheme.colorScheme.primary) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            keyboardType = KeyboardType.Text,
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
            cursorColor = MaterialTheme.colorScheme.primary,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        singleLine = singleLine,

    )
}