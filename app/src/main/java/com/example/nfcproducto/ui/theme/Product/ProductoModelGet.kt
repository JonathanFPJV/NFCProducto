package com.example.nfcproducto.ui.theme.Product

import com.example.nfcproducto.CategoryModel
import com.example.nfcproducto.NfcModel
import com.google.gson.annotations.SerializedName

data class ProductoModelGet (
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("img")
    var img: String?,
    @SerializedName("price")
    var price: Double,
    @SerializedName("description")
    var description: String,
    @SerializedName("category")
    var category: CategoryModel, // Recibe el objeto completo de la categoría
    @SerializedName("idNFC")
    var idNFC: NfcModel?  // Puede ser nulo si no tiene NFC asociado
)
