package com.example.shoppinglist.data.other

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.data.db.entities.ShoppingListItem
import com.example.shoppinglist.ui.shoppinglist.ShoppingListViewModel
import kotlinx.android.synthetic.main.shoppinglist_item.view.*

class ShoppingListItemAdapter(
    var items: List<ShoppingListItem>,
    private val viewModel: ShoppingListViewModel
): RecyclerView.Adapter<ShoppingListItemAdapter.ShoppingListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shoppinglist_item, parent, false)
        return ShoppingListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShoppingListViewHolder, position: Int) {
        val currentShoppingItem = items[position]

        holder.itemView.text_name.text = currentShoppingItem.name
        holder.itemView.text_quantity.text = "${currentShoppingItem.quantity}"

        // Delete item
        holder.itemView.icon_delete.setOnClickListener {
            viewModel.delete(currentShoppingItem)
        }

        // Increase item quantity
        holder.itemView.icon_plus.setOnClickListener {
            currentShoppingItem.quantity++
            viewModel.upsert(currentShoppingItem)
        }

        // Decrease item quantity ( or remove if 0 )
        holder.itemView.icon_minus.setOnClickListener {
            if (currentShoppingItem.quantity > 0) {
                currentShoppingItem.quantity--
                viewModel.upsert(currentShoppingItem)
            } else {
                viewModel.delete(currentShoppingItem)
            }
        }

        // Get sorted
    }
    
    override fun getItemCount(): Int {
        return items.size
    }

    inner class ShoppingListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

}