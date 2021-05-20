package com.example.shoppinglist.ui.shoppinglist

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.iterator
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppinglist.R
import com.example.shoppinglist.data.db.ShoppingListDatabase
import com.example.shoppinglist.data.db.entities.ShoppingListItem
import com.example.shoppinglist.data.other.ShoppingListItemAdapter
import com.example.shoppinglist.data.repositories.ShoppingListRepository
import kotlinx.android.synthetic.main.dialog_delete_all.*
import kotlinx.android.synthetic.main.shoppinglist_main.*

class ShoppingListActivity : AppCompatActivity() {
    private val shareableList = arrayListOf<String>()
    var themeIsLight = true

    fun createShareableList(adapter: ShoppingListItemAdapter) {
        shareableList.clear()
        for ( item in adapter.items ) {
            shareableList.add("${item.quantity} ${item.name}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shoppinglist_main)

        val database = ShoppingListDatabase(this)
        val repository = ShoppingListRepository(database)
        val factory = ShoppingViewModelFactory(repository)
        val viewModel = ViewModelProviders.of(this, factory).get(ShoppingListViewModel::class.java)
        val adapter = ShoppingListItemAdapter(listOf(), viewModel)

        recyclerView_ShoppingListItems.layoutManager = LinearLayoutManager(this)
        recyclerView_ShoppingListItems.adapter = adapter

        // Update recyclerview upon change
        viewModel.getAllShoppingListItems().observe(this, Observer {
            adapter.items = it
            createShareableList(adapter)
            adapter.notifyDataSetChanged()
        })


        // Add a new item
        button_add_item.setOnClickListener {
            AddShopingListItemDialog(this, object : AddDialogListener {
                        override fun onButtonClicked(item: ShoppingListItem) {
                            viewModel.upsert(item)
                            createShareableList(adapter)
                        }
                    }).show()
        }
    }


    // Menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val database = ShoppingListDatabase(this)
        val repository = ShoppingListRepository(database)
        val factory = ShoppingViewModelFactory(repository)
        val viewModel = ViewModelProviders.of(this, factory).get(ShoppingListViewModel::class.java)
        val adapter = ShoppingListItemAdapter(listOf(), viewModel)

        val id = item.getItemId()


        // Action bar clicks by ID
        // Night/Light mode
        if (id == R.id.action_settings) {
            if (themeIsLight === true ) {
                themeIsLight = false
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                Toast.makeText(this, "Night mode activated", Toast.LENGTH_SHORT).show()
            } else {
                themeIsLight = true
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                Toast.makeText(this, "Light mode activated", Toast.LENGTH_SHORT).show()
            }
        }

        // Delete all items
        if (id == R.id.action_clear_list) {
            val dialog = ClearShoppingListDialog(this ).also {
                it.show()
            }
            dialog.dialog_button_clear.setOnClickListener {
                viewModel.deleteAllShoppingListItems()
                dialog.dismiss()
            }
        }

        // Sort
        if (id == R.id.btn_sort_list) {
            Toast.makeText(this, "Sorted Alphabetically", Toast.LENGTH_SHORT).show()
            return true
        }

        // Share intent
        if (id == R.id.action_share) {
            val sendIntent: Intent = Intent().apply {
                val itemList = mutableListOf<String>()
                for (item in recyclerView_ShoppingListItems) {

                }

                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "${shareableList.joinToString(", ").replace("[", "").replace("]", "")}")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        return super.onOptionsItemSelected(item)

    }

}