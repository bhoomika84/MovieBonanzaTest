package com.sky.moviebonanza.data

data class DataOrException<T,  Boolean, E: String>(
    var data: T? = null,
    var loading: Boolean? = null,
    var exception: String? = null
)