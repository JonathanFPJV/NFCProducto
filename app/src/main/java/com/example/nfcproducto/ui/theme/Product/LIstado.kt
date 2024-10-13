package com.example.nfcproducto.ui.theme.Product

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.nfcproducto.ProductoApiService
import com.example.nfcproducto.ProductoModel
import kotlinx.coroutines.delay

@Composable
fun ContenidoProductosListado(navController: NavHostController, servicio: ProductoApiService) {
    var listaProductos: SnapshotStateList<ProductoModel> = remember { mutableStateListOf() }

    LaunchedEffect(Unit) {
        val productos = servicio.selectProductos()
        listaProductos.addAll(productos)
    }

    LazyColumn {
        items(listaProductos) { producto ->
            Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                Text(text = producto.name, modifier = Modifier.weight(0.6f))
                Text(text = producto.price.toString(), modifier = Modifier.weight(0.2f))
                Text(text = producto.category.name, modifier = Modifier.weight(0.2f)) // Mostrar la categoría
                producto.idNFC?.let {
                    Text(text = it.id_tag) // Mostrar el NFC si está presente
                }
            }
        }
    }
}
