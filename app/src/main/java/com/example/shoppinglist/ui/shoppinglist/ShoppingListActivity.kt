package com.example.shoppinglist.ui.shoppinglist

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppinglist.R
import com.example.shoppinglist.data.db.ShoppingListDatabase
import com.example.shoppinglist.data.db.entities.ShoppingListItem
import com.example.shoppinglist.data.other.ShoppingListItemAdapter
import com.example.shoppinglist.data.repositories.ShoppingListRepository
import kotlinx.android.synthetic.main.shoppinglist_main.*


class ShoppingListActivity : AppCompatActivity() {
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
            adapter.notifyDataSetChanged()
        })


        // Add a new item
        button_add_item.setOnClickListener {
            AddShopingListItemDialog(this,
                    object : AddDialogListener {
                        override fun onButtonClicked(item: ShoppingListItem) {
                            viewModel.upsert(item)
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
        // Action bar clicks

        val database = ShoppingListDatabase(this)
        val repository = ShoppingListRepository(database)
        val factory = ShoppingViewModelFactory(repository)
        val viewModel = ViewModelProviders.of(this, factory).get(ShoppingListViewModel::class.java)
        val adapter = ShoppingListItemAdapter(listOf(), viewModel)

        // Update recyclerview upon change
        viewModel.getAllAlphabetically().observe(this, Observer {
            adapter.items = it
            adapter.notifyDataSetChanged()
        })

        val id = item.getItemId()

        if (id == R.id.action_settings) {
            Toast.makeText(this, "Settings Clicked", Toast.LENGTH_SHORT).show()
            return true
        }

        // Delete all items
        if (id == R.id.action_clear_list) {
            viewModel.deleteAllShoppingListItems()
            Toast.makeText(this, "List cleared!", Toast.LENGTH_SHORT).show()
        }
        if (id == R.id.btn_sort_list) {
            viewModel.getAllAlphabetically()
            Toast.makeText(this, "Sorted Alphabetically", Toast.LENGTH_SHORT).show()
            return true
        }

        return super.onOptionsItemSelected(item)

    }

}