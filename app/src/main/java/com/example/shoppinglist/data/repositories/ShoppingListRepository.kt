package com.example.shoppinglist.data.repositories

import com.example.shoppinglist.data.db.ShoppingListDatabase
import com.example.shoppinglist.data.db.entities.ShoppingListItem

class ShoppingListRepository(
    private val db: ShoppingListDatabase
) {
    // Database Methods
    suspend fun upsert(item: ShoppingListItem) = db.getShoppingListDao().upsert(item)
    suspend fun delete(item: ShoppingListItem) = db.getShoppingListDao().delete(item)

    fun getAllShoppingListItems() = db.getShoppingListDao().getAllShoppingListItems()
}