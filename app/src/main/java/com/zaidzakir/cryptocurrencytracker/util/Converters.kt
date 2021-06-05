package com.zaidzakir.cryptocurrencytracker.util

import androidx.room.TypeConverter

/**
 *Created by Zaid Zakir
 */
class Converters {
    @TypeConverter
    fun toListOfStrings(flatStringList: String): List<String> {
        return flatStringList.split(",")
    }
    @TypeConverter
    fun fromListOfStrings(listOfString: List<String>): String {
        return listOfString.joinToString(",")
    }
}