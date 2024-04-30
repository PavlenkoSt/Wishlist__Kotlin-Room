package com.learning.wishlist.models

import kotlinx.coroutines.flow.Flow

class WishRepository (private val wishDao: WishDao) {
    fun getAllWishes(): Flow<List<Wish>> {
        return wishDao.getAllWishes()
    }

    fun getWishById(id: Long): Flow<Wish> {
        return wishDao.getWishById(id)
    }

    suspend fun addWish(wish: Wish) {
        wishDao.addWish(wish)
    }

    suspend fun updateWish(wish: Wish) {
        wishDao.updateWish(wish)
    }

    suspend fun deleteWish(wish: Wish) {
        wishDao.deleteWish(wish)
    }
}