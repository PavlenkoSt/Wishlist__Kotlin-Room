package com.learning.wishlist.models

import android.content.Context
import androidx.room.Room

object Graph {
    private lateinit var database: WishDatabase

    val wishRepository by lazy {
        WishRepository(wishDao = database.wishDao())
    }

    fun provide(context: Context) {
        database = Room.databaseBuilder(context = context, WishDatabase::class.java, "wishlist-db").build()
    }
}