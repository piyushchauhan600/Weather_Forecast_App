package com.pew.weatherforecast.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nonnull

@Entity("Setting_tbl")
data class Units (
    @Nonnull
    @PrimaryKey
    @ColumnInfo("Units")
    val units: String
    )