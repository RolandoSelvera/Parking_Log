package com.rolandoselvera.parkinglog.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Cars")
data class Car(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "Brand")
    val brand: String?,
    @ColumnInfo(name = "Model")
    val model: String?,
    @ColumnInfo(name = "Car_Plate")
    val carPlate: String?,
    @ColumnInfo(name = "Color")
    val color: String?,
    @ColumnInfo(name = "Owner")
    val owner: String?,
    @ColumnInfo(name = "Front_Side")
    val frontSide: String?,
    @ColumnInfo(name = "Back_Side")
    val backSide: String?,
    @ColumnInfo(name = "Left_Side")
    val leftSide: String?,
    @ColumnInfo(name = "Right_Side")
    val rightSide: String?,
)

data class CarEntity(
    var id: Int?,
    val brand: String?,
    val model: String?,
    val carPlate: String?,
    val color: String?,
    val owner: String?,
    val frontSide: String?,
    val backSide: String?,
    val leftSide: String?,
    val rightSide: String?,
)
