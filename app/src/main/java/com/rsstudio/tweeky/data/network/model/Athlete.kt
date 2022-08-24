package com.rsstudio.tweeky.data.network.model

import java.io.Serializable

data class Athlete(
    val organisation: String,
    val athletes: List<Data>
): Serializable
