package com.rolandoselvera.parkinglog.data.models

data class Car(
    val id: Int,
    val brand: String?,
    val model: String?,
    val carPlate: String?,
    val color: String?,
    val owner: String? = ""
)
