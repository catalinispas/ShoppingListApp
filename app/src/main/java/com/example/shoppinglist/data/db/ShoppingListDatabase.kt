package com.example.shoppinglist.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shoppinglist.data.db.entities.ShoppingListItem

@Database(
    entities = [ShoppingListItem::class],
    version = 1
)
abstract class ShoppingListDatabase: RoomDatabase() {

    abstract fun getShoppingListDao(): ShoppingListDao

    companion object {
        @Volatile
        private var instance: ShoppingListDatabase? = null
        private val LOCK = Any()

        // Executed whenever we create an instance of ShoppingListDatabase
        // Returns the instance but if null it will call a synchronized lock
        // Where we instantiate the database
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it}
        }

        // Instantiate Database
        private fun createDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
            ShoppingListDatabase::class.java,"ShoppingListDb.db").build()
    }
}