package com.sky.moviebonanza.utils

import com.google.gson.Gson
import com.sky.moviebonanza.model.MovieItem

fun formatJSONtoString(movieItem: MovieItem): String{
    return Gson().toJson(movieItem)
}

fun formatStringToJSON(movieItem: String): MovieItem{
    return  Gson().fromJson(movieItem, MovieItem::class.java)
}