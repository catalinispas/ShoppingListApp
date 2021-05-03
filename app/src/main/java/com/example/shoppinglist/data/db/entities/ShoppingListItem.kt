package com.example.shoppinglist.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shoppingList_items")
class ShoppingListItem(
    @ColumnInfo(name = "item_name")
    var name: String,
    @ColumnInfo(name = "item_quantity")
    var quantity: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}