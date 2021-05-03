package com.example.shoppinglist.ui.shoppinglist

import com.example.shoppinglist.data.db.entities.ShoppingListItem

interface AddDialogListener {
    fun onButtonClicked(item: ShoppingListItem)
}