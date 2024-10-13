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
fun ContenidoProductoEliminar(navController: NavHostController, servicio: ProductoApiServiceC, id: Int) {
    var confirmDelete by remember { mutableStateOf(false) }

    if (confirmDelete) {
        LaunchedEffect(Unit) {
            // Llamamos al nuevo método deleteProducto de ProductoApiServiceC
            val response = servicio.deleteProducto(id.toString())
            if (response.isSuccessful) {
                navController.navigate("productos") // Navegamos de vuelta a la lista de productos tras el borrado
            } else {
                // Manejar error si es necesario
                // Puedes mostrar un mensaje o hacer un log de la respuesta
            }
        }
    }

    // Configuración de AlertDialog para confirmar la eliminación
    AlertDialog(
        onDismissRequest = { navController.navigate("productos") },
        confirmButton = {
            Button(onClick = { confirmDelete = true }) {
                Text(text = "Eliminar")
            }
        },
        dismissButton = {
            Button(onClick = { navController.navigate("productos") }) {
                Text(text = "Cancelar")
            }
        },
        title = { Text(text = "Eliminar Producto") },
        text = { Text(text = "¿Está seguro de eliminar este producto?") }
    )
}

