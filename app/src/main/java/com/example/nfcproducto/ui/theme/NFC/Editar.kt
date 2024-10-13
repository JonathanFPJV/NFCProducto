package com.example.nfcproducto.ui.theme.NFC

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
import com.example.nfcproducto.NfcApiService
import com.example.nfcproducto.NfcModel
import kotlinx.coroutines.delay

@Composable
fun ContenidoNfcEditar(navController: NavHostController, servicio: NfcApiService, nfcId: Int = 0) {
    var id by remember { mutableStateOf(nfcId) }
    var idTag by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }
    var fechaAsignado by remember { mutableStateOf("") }
    var grabar by remember { mutableStateOf(false) }

    if (id != 0) {
        LaunchedEffect(Unit) {
            val objNfc = servicio.selectNfc(id.toString())
            delay(100)
            idTag = objNfc.body()?.id_tag ?: ""
            status = objNfc.body()?.status ?: ""
            fechaAsignado = objNfc.body()?.fecha_asignado ?: ""
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        TextField(
            value = idTag,
            onValueChange = { idTag = it },
            label = { Text("ID Tag") },
            singleLine = true
        )
        TextField(
            value = status,
            onValueChange = { status = it },
            label = { Text("Status") },
            singleLine = true
        )
        TextField(
            value = fechaAsignado,
            onValueChange = { fechaAsignado = it },
            label = { Text("Fecha Asignado (YYYY-MM-DD)") },
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
        val objNfc = NfcModel(id, idTag, status, fechaAsignado)
        LaunchedEffect(Unit) {
            if (id == 0) {
                servicio.insertNfc(objNfc)
            } else {
                servicio.updateNfc(id.toString(), objNfc)
            }
        }
        grabar = false
        navController.navigate("nfc")
    }
}
