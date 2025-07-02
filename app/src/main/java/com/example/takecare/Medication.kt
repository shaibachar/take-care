package com.example.takecare

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Medication(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val description: String,
    val reason: String,
    val dailyDose: Int,
    val startQuantity: Int,
    val imageUri: String?
)
