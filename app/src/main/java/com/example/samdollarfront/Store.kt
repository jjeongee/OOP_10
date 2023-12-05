package com.example.samdollarfront

import com.google.firebase.database.Exclude

data class StoreData(
    val name: String,
    val account: String,
    val bank: String,
    val ownername: String,
    var lat: Double,
    var lng: Double,
    var distance: Double = 0.0
)

