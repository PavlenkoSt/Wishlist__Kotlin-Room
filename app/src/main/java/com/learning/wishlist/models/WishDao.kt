package com.learning.wishlist.models

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao()
abstract class WishDao {
    @Query("Select * from `wish-table`")
    abstract fun getAllWishes (): Flow<List<Wish>>

    @Query("Select * from `wish-table` where id=:id")
    abstract fun getWishById (id: Long) : Flow<Wish>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun addWish (wishEntity: Wish)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun updateWish (wishEntity: Wish)

    @Delete()
    abstract suspend fun deleteWish (wishEntity: Wish)
}