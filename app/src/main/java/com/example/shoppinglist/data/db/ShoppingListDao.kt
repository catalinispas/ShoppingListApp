package com.example.shoppinglist.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.shoppinglist.data.db.entities.ShoppingListItem

@Dao
interface ShoppingListDao {

    // Update & Insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: ShoppingListItem)

    @Delete
    suspend fun delete(item: ShoppingListItem)

    @Query("SELECT * FROM shoppingList_items")
    fun getAllShoppingListItems(): LiveData<List<ShoppingListItem>>
}