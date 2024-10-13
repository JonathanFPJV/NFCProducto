package com.example.nfcproducto.ui.theme.Product

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.nfcproducto.CategoryModel
import com.example.nfcproducto.NfcModel
import com.example.nfcproducto.ProductoApiService
import com.example.nfcproducto.ProductoModel
import kotlinx.coroutines.delay

@Composable
fun ContenidoProductoFormulario(navController: NavHostController, servicio: ProductoApiService, productoId: Int = 0) {
    var id by remember { mutableStateOf(productoId) }
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var categoryId by remember { mutableStateOf("") }
    var nfcTag by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("Descripción") }
    var grabar by remember { mutableStateOf(false) }

    // Cargar los datos si estamos editando un producto existente
    if (id != 0) {
        LaunchedEffect(Unit) {
            val objProducto = servicio.selectProducto(id.toString())
            delay(100)
            name = objProducto.body()?.name ?: ""
            price = objProducto.body()?.price?.toString() ?: ""
            categoryId = objProducto.body()?.category?.id.toString() ?: ""
            nfcTag = objProducto.body()?.idNFC?.id_tag ?: ""
            description = objProducto.body()?.description ?: "Descripción"
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre del Producto") },
            singleLine = true
        )
        TextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Precio") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
        TextField(
            value = categoryId,
            onValueChange = { categoryId = it },
            label = { Text("ID de Categoría") },
            singleLine = true
        )
        TextField(
            value = nfcTag,
            onValueChange = { nfcTag = it },
            label = { Text("ID Tag NFC") },
            singleLine = true
        )

        Button(
            onClick = {
                grabar = true
            }
        ) {
            Text("Guardar", fontSize = 16.sp)
        }
    }

    // Manejar la acción de guardar los datos del producto
    if (grabar) {
        val nuevoProducto = ProductoModel(
            id = id,
            name = name,
            img = null, // Imagen nula por ahora
            price = price.toDoubleOrNull() ?: 0.0,
            description = description,
            category = CategoryModel(id = categoryId.toInt(), name = "Categoría", img = null),
            idNFC = if (nfcTag.isNotEmpty()) NfcModel(id = 0, id_tag = nfcTag, status = "activo", fecha_asignado = "2024-01-01") else null
        )

        LaunchedEffect(Unit) {
            if (id == 0) {
                servicio.insertProducto(nuevoProducto)
            } else {
                servicio.updateProducto(id.toString(), nuevoProducto)
            }
            navController.navigate("productos")
        }
        grabar = false
    }
}

