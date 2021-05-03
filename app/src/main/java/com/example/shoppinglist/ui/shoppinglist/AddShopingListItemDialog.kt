package com.example.shoppinglist.ui.shoppinglist

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import com.example.shoppinglist.R
import com.example.shoppinglist.data.db.entities.ShoppingListItem
import kotlinx.android.synthetic.main.dialog_add_shoppinglist_item.*

class AddShopingListItemDialog(context: Context, var addDialogListener: AddDialogListener) : AppCompatDialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_add_shoppinglist_item)

        dialog_button_add.setOnClickListener {
            val name = dialog_editText_item_name.text.toString()
            val quantity = dialog_editText_item_quantity.text.toString()

            if(name.isEmpty() || quantity.isEmpty()) {
                Toast.makeText(context, "Please fill in the input fields!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val item = ShoppingListItem(name, quantity.toInt())
            addDialogListener.onButtonClicked(item)
        }

        dialog_button_cancel.setOnClickListener {
            cancel()
        }
    }

}