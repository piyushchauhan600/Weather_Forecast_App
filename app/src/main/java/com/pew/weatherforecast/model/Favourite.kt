package com.pew.weatherforecast.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nonnull

@Entity(tableName = "fav_tbl")
data class Favourite(
    @Nonnull
    @PrimaryKey
    @ColumnInfo("City")
    val city: String,
    @ColumnInfo("Country")
    val country: String,
)
