package com.example.myshoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField

import androidx.compose.ui.text.style.TextDecoration

data class ShoppingItem(val name: String, val quantity: String, var isChecked: Boolean = false)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingListApp()
        }
    }
}

@Composable
fun ShoppingListApp() {
    var itemName by remember { mutableStateOf("") }
    var itemQuantity by remember { mutableStateOf("") }
    val shoppingList = remember { mutableStateListOf<ShoppingItem>() }

    Column (modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        // Input Fields for Item Name and Quantity
        TextField(
            value = itemName,
            onValueChange = { itemName = it },
            label = { Text("Item Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        TextField(
            value = itemQuantity,
            onValueChange = { itemQuantity = it },
            label = { Text("Quantity") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        // Add Item Button
        ElevatedButton(
            onClick = {
                if (itemName.isNotBlank() && itemQuantity.isNotBlank()) {
                    shoppingList.add(ShoppingItem(name = itemName, quantity = itemQuantity))
                    itemName = ""
                    itemQuantity = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display Shopping List
        LazyColumn(modifier = Modifier.fillMaxHeight()) { //chatGPT suggestion
            items(shoppingList.size) { index ->
                val item = shoppingList[index]
                ShoppingItemRow(
                    item = item,
                    onCheckedChange = { isChecked ->
                        shoppingList[index] = item.copy(isChecked = isChecked)
                    }
                )
            }
        }
    }
}

@Composable
fun ShoppingItemRow(item: ShoppingItem, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = item.name,
                textDecoration = if (item.isChecked) TextDecoration.LineThrough else TextDecoration.None
            )
            Text(text = "Quantity: ${item.quantity}")
        }
        Checkbox(
            checked = item.isChecked,
            onCheckedChange = onCheckedChange
        )
    }
}