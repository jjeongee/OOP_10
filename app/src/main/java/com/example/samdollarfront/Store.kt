package com.example.samdollarfront

import com.google.firebase.database.Exclude

data class StoreData(
    var name: String,
    var account: String,
    var bank: String,
    var ownername: String,
    var lat: Double,
    var lng: Double,
    var distance: Float = 0F
)

