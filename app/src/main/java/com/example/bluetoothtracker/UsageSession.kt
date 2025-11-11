package com.example.bluetoothtracker

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UsageSession(
    @PrimaryKey val timestamp: Long,
    val minutes: Long
)
