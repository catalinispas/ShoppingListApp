package com.example.shoppinglist.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.shoppinglist.data.db.entities.ShoppingListItem

@Dao
interface ShoppingListDao {

    // Update & Insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: ShoppingListItem)

    // Delete item
    @Delete
    suspend fun delete(item: ShoppingListItem)

    // Get all items
    @Query("SELECT * FROM shoppingList_items")
    fun getAllShoppingListItems(): LiveData<List<ShoppingListItem>>

    // Get all items ( alphabetically )
    @Query ("SELECT * FROM shoppingList_items ORDER BY item_name")
    fun getAllAlphabetically(): LiveData<List<ShoppingListItem>>

    // Get all items ( quantity )
    @Query ("SELECT * FROM shoppingList_items ORDER BY item_quantity DESC")
    fun getAllQty(): LiveData<List<ShoppingListItem>>

    // Delete all items
    @Query("DELETE FROM shoppingList_items")
    suspend fun deleteAll()
}