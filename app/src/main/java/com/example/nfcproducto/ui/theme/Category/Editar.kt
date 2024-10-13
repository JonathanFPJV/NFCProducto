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
fun ContenidoCategoryEditar(navController: NavHostController, servicio: CategoryApiService, categoryId: Int = 0) {
    var id by remember { mutableStateOf(categoryId) }
    var name by remember { mutableStateOf("") }
    var img by remember { mutableStateOf<String?>("") }
    var grabar by remember { mutableStateOf(false) }

    if (id != 0) {
        LaunchedEffect(Unit) {
            val objCategory = servicio.selectCategory(id.toString())
            delay(100)
            name = objCategory.body()?.name ?: ""
            img = objCategory.body()?.img
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre de la Categoría") },
            singleLine = true
        )
        TextField(
            value = img ?: "",
            onValueChange = { img = it },
            label = { Text("URL de Imagen (opcional)") },
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

    if (grabar) {
        val objCategory = CategoryModel(id, name, img)
        LaunchedEffect(Unit) {
            if (id == 0) {
                servicio.insertCategory(objCategory)
            } else {
                servicio.updateCategory(id.toString(), objCategory)
            }
        }
        grabar = false
        navController.navigate("categories")
    }
}
