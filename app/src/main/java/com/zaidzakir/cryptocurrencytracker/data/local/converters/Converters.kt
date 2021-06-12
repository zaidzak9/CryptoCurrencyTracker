package com.zaidzakir.cryptocurrencytracker.data.local.converters

import androidx.room.TypeConverter
import com.zaidzakir.cryptocurrencytracker.data.remote.newsResponse.Source

/**
 *Created by Zaid Zakir
 */
class Converters {
    @TypeConverter
    fun fromSource(source: Source):String{
        return source.name
    }

    @TypeConverter
    fun toSource(name:String): Source {
        return Source(name, name)
    }
}