package com.example.shoppinglist.ui.shoppinglist

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialog
import com.example.shoppinglist.R
import kotlinx.android.synthetic.main.dialog_add_shoppinglist_item.dialog_button_cancel
import kotlinx.android.synthetic.main.dialog_delete_all.*

class ClearShoppingListDialog(context: Context) : AppCompatDialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_delete_all)

        dialog_button_clear.setOnClickListener {
            dismiss()
        }

        dialog_button_cancel.setOnClickListener {
            cancel()
        }
    }

}