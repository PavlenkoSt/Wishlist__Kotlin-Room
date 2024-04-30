package com.learning.wishlist.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learning.wishlist.models.Graph
import com.learning.wishlist.models.Wish
import com.learning.wishlist.models.WishRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class WishViewModel(
    private val wishRepository: WishRepository = Graph.wishRepository
): ViewModel() {
    var wishTitle by mutableStateOf("")
    var wishDescription by mutableStateOf("")

    fun onWishTitleChanged (title: String) {
        wishTitle = title
    }

    fun onWishDescriptionChanged (description: String) {
        wishDescription = description
    }

    fun resetWishFormState() {
        wishTitle = ""
        wishDescription = ""
    }

    lateinit var getAllWishes: Flow<List<Wish>>

    init {
        viewModelScope.launch {
            getAllWishes = wishRepository.getAllWishes()
        }
    }

    fun getWishById(id: Long): Flow<Wish> {
        return wishRepository.getWishById(id = id)
    }

    fun addWish(wish: Wish) {
        viewModelScope.launch(Dispatchers.IO) {
            wishRepository.addWish(wish = wish)
        }
    }

    fun updateWish(wish: Wish) {
        viewModelScope.launch(Dispatchers.IO) {
            wishRepository.updateWish(wish = wish)
        }
    }

    fun deleteWish(wish: Wish) {
        viewModelScope.launch(Dispatchers.IO) {
            wishRepository.deleteWish(wish = wish)
        }
    }
}