package com.example.nfcproducto.ui.theme.Product

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.nfcproducto.ProductoApiService
import com.example.nfcproducto.ProductoModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContenidoProductosListado(navController: NavHostController, servicio: ProductoApiServiceC) {
    var listaProductos: SnapshotStateList<ProductoModelGet> = remember { mutableStateListOf() }

    LaunchedEffect(Unit) {
        val productos = servicio.selectProductos()
        listaProductos.addAll(productos)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Barra superior
        TopAppBar(
            title = { Text(text = "Listado de Productos") },
            actions = {
                // Botón de agregar
                IconButton(onClick = {
                    // Navegar a la vista de agregar un nuevo producto
                    navController.navigate("productoNuevo")
                }) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar Producto")
                }
            },
        )

        // Lista de productos
        LazyColumn(modifier = Modifier.padding(4.dp)) {  // Reduce el padding para ver más productos
            items(listaProductos) { producto ->
                // Cada producto se muestra en una tarjeta
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)  // Reduce el padding entre tarjetas
                        .clickable {
                            // Navegar a la vista de detalles al hacer clic en la tarjeta
                            navController.navigate("productoVer/${producto.id}")
                        },
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(Color(0xFFE5BE87))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),  // Reduce el padding dentro de la tarjeta
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Mostrar la información del producto
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = producto.name,
                                style = MaterialTheme.typography.titleMedium,  // Reduce el tamaño de la fuente
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = "Price: $${producto.price}",
                                style = MaterialTheme.typography.bodyMedium,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = "Category: ${producto.category.name}",
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                text = "Stock: ${producto.stock}",  // Muestra el nuevo campo Stock
                                style = MaterialTheme.typography.bodySmall,
                                color = if (producto.stock > 0) Color.Black else Color.Red  // Destaca si hay bajo stock
                            )
                            producto.idNFC?.let {
                                Text(
                                    text = "NFC: ${it.id_tag}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }

                        // Botones de Editar y Eliminar
                        Row {
                            IconButton(onClick = {
                                // Navegar a la vista de editar
                                navController.navigate("productoEditar/${producto.id}")
                            }) {
                                Icon(Icons.Default.Edit, contentDescription = "Editar")
                            }
                            IconButton(onClick = {
                                // Navegar a la vista de eliminar
                                navController.navigate("productoDel/${producto.id}")
                            }) {
                                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                            }
                        }
                    }
                }
            }
        }
    }
}




