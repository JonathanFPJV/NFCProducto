package com.example.nfcproducto.ui.theme.Category

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.nfcproducto.CategoryApiService
import com.example.nfcproducto.CategoryModel
import kotlinx.coroutines.delay


@Composable
fun ContenidoCategoryEliminar(navController: NavHostController, servicio: CategoryApiService, categoryId: Int) {
    var showDialog by remember { mutableStateOf(true) }
    var borrar by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) } // Para manejar errores

    // Diálogo de confirmación
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Confirmar Eliminación") },
            text = { Text("¿Está seguro de eliminar la Categoría?") },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false
                        borrar = true
                    }
                ) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                Button(onClick = {
                    showDialog = false
                    navController.navigate("categorias") // Navegar de vuelta si se cancela
                }) {
                    Text("Cancelar")
                }
            }
        )
    }

    // Eliminación de la categoría
    if (borrar) {
        LaunchedEffect(Unit) {
            try {
                val response = servicio.deleteCategory(categoryId.toString())
                if (response.isSuccessful) {
                    navController.navigate("categorias") {
                        popUpTo("categorias") { inclusive = true } // Regresar a la lista de categorías
                    }
                } else {
                    error = "Error al eliminar la categoría"
                }
            } catch (e: Exception) {
                error = "Error: ${e.localizedMessage}"
            } finally {
                borrar = false
            }
        }
    }

    // Mostrar mensaje de error si lo hay
    error?.let {
        AlertDialog(
            onDismissRequest = { error = null },
            title = { Text(text = "Error") },
            text = { Text(it) },
            confirmButton = {
                Button(onClick = { error = null }) {
                    Text("Aceptar")
                }
            }
        )
    }
}

