package com.example.inventory

import androidx.lifecycle.*
import com.example.inventory.data.Item
import com.example.inventory.data.ItemDAO
import kotlinx.coroutines.launch

class InventoryViewModel(private val itemDAO: ItemDAO): ViewModel() {

    val allItems: LiveData<List<Item>> = itemDAO.getListItems().asLiveData()

    private fun insert(item: Item) {
        viewModelScope.launch {
            itemDAO.insert(item)
        }
    }

    private fun getNewItemEntry(itemName: String, itemPrice: String, itemCount: String): Item {
        return Item(
            itemName = itemName,
            itemPrice = itemPrice.toDouble(),
            quantityInStock = itemCount.toInt()
        )
    }

    fun addNewItem(itemName: String, itemPrice: String, itemCount: String) {
        insert(
            getNewItemEntry(itemName, itemPrice, itemCount)
        )
    }

    public fun isEntryValid(itemName: String, itemPrice: String, itemCount: String): Boolean {
        if (itemName.isBlank() || itemPrice.isBlank() || itemCount.isBlank()) {
            return false
        }
        return true
    }

    fun retrieveItem(id: Int): LiveData<Item> {
        return itemDAO.getItem(id).asLiveData()
    }

    fun updateItem(
        itemId: Int,
        itemName: String,
        itemPrice: String,
        itemCount: String
    ) {
        val updatedItem = getUpdatedItemEntry(itemId, itemName, itemPrice, itemCount)
        updateItem(updatedItem)
    }

    fun updateItem(item: Item) {
        viewModelScope.launch {
            itemDAO.update(item)
        }
    }

    fun sellItem(item: Item) {
        if (isStockAvailable(item)) {
            val newItem = item.copy(quantityInStock = item.quantityInStock - 1)
            viewModelScope.launch {
                itemDAO.update(newItem)
            }
        }
    }

    fun deleteItem(item: Item) {
        viewModelScope.launch {
            itemDAO.delete(item)
        }
    }

    fun isStockAvailable(item: Item): Boolean {
        return (item.quantityInStock > 0)
    }

    private fun editItem() {
        viewModelScope.launch {

        }
    }

    private fun getUpdatedItemEntry(
        itemId: Int,
        itemName: String,
        itemPrice: String,
        itemCount: String
    ): Item {
        return Item(
            id = itemId,
            itemName = itemName,
            itemPrice = itemPrice.toDouble(),
            quantityInStock = itemCount.toInt()
        )
    }
}

class InventoryViewModelFactory(private val itemDao: ItemDAO) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InventoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InventoryViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}