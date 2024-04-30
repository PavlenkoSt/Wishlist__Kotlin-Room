package com.learning.wishlist

import android.app.Application
import com.learning.wishlist.models.Graph

class WishlistApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}