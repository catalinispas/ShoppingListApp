package com.example.shoppinglist.ui.shoppinglist

import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.db.entities.ShoppingListItem
import com.example.shoppinglist.data.repositories.ShoppingListRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShoppingListViewModel (
    private val repository: ShoppingListRepository
): ViewModel() {
    fun upsert(item: ShoppingListItem) = CoroutineScope(Dispatchers.Main).launch {
        repository.upsert(item)
    }

    fun delete(item: ShoppingListItem) = CoroutineScope(Dispatchers.Main).launch {
        repository.delete(item)
    }

    fun getAllShoppingListItems() = repository.getAllShoppingListItems()

    // Sorts
    fun getAllAlphabetically() = repository.getAllAlphabetically()
    fun getAllQty() = repository.getAllQty()

    fun deleteAllShoppingListItems() = CoroutineScope(Dispatchers.Main).launch {
        repository.deleteAll()
    }


}