package de.cispa.shoppinglist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

data class shoppingList (
        val Id: Int,
        var name: String,
        var quantity: Int,
        var isEnabled: Boolean = false
        )

@Composable
fun ShoppingList(){
    var showDialog by remember { mutableStateOf(false ) }
    var inputItemName by remember { mutableStateOf("") }
    var inputItemQuantity by remember { mutableStateOf("1") }
    var sItems by remember { mutableStateOf(listOf<shoppingList>()) }

    //The below code is to let users enter only values in quantity text box
    val onValueChangeQty: (String) -> Unit = {newText ->
        val regex = """^-?\d*\.?\d*$""".toRegex()
        if (regex.matches(newText)){
            inputItemQuantity = newText
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { showDialog = true }) {
            Text(text = "Add Item")
        }
        Spacer(
            modifier = Modifier.height(16.dp)
        )
        LazyColumn(modifier = Modifier.fillMaxSize()){
            items(sItems){item ->
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                        .border(
                            border = BorderStroke(2.dp, Color.Black),
                            shape = RoundedCornerShape(20)
                        )
                        .padding(8.dp, 8.dp, 8.dp, 8.dp)
                ) {
                    Text(text = item.name, Modifier.padding(8.dp))
                    Text(text = "Qty: ${item.quantity}", Modifier.padding(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(imageVector = Icons.Default.Edit, contentDescription = "")
                        }
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = "")
                        }
                    }
                }
            }
        }
    }
    if(showDialog){
        AlertDialog(onDismissRequest = { showDialog = false }, confirmButton = { 
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Add Shopping Item")
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(value = inputItemName,
                    onValueChange = {newValue -> if (newValue.all { it.isLetter() }){inputItemName = newValue} },
                    label = { Text(text = "Enter Item")})
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(value = inputItemQuantity, onValueChange = onValueChangeQty, label = { Text(text = "Enter Quantity")})
            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = {
                    if (inputItemName.isNotBlank()){
                        val addNewItem = shoppingList(
                            Id = sItems.size + 1,
                            name = inputItemName,
                            quantity = inputItemQuantity.toInt())
                        sItems = sItems + addNewItem
                        showDialog = false
                        inputItemName = ""
                        inputItemQuantity = "1"
                    }
                }) {
                    Text(text = "Add")
                }
                Spacer(modifier = Modifier.width(105.dp))
                Button(onClick = { showDialog = false }) {
                    Text(text = "Cancel")
                }
            }
        })
    }
}
