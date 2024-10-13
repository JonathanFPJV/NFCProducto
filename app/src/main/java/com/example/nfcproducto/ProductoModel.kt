package com.example.nfcproducto

import com.google.gson.annotations.SerializedName

data class ProductoModel (
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("img")
    var img: String?, // La imagen puede ser nula
    @SerializedName("price")
    var price: Double,
    @SerializedName("description")
    var description: String,
    @SerializedName("category")
    var category: CategoryModel, // Relación con el modelo Category
    @SerializedName("idNFC")
    var idNFC: NfcModel? // Puede ser nulo si el producto no tiene un NFC asociado
)