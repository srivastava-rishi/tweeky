package com.rsstudio.tweeky.data.network.model

import java.io.Serializable


data class Data(
    val id: String? = null,
    val name: String? = null,
    val score: Int? = null,
    val runup: Int? = null,
    val jump: Int? = null,
    val image: String? = null,
    val bfc: Int? = null,
    val ffc: Int? = null,
    val release: Int? = null
): Serializable {

    override fun equals(other: Any?): Boolean {
        return if (other is Data) {
            other.id == id
        }
        else {
            false
        }
    }

}
