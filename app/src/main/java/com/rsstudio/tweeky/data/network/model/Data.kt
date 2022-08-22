package com.rsstudio.tweeky.data.network.model

data class Data(
    val id: String,
    val name: String,
    val score: Int,
    val runup: Int,
    val jump: Int,
    val image: String,
    val bfc: Int,
    val ffc: Int,
    val release: Int
)
