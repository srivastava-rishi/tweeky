package com.rsstudio.tweeky.data.network.apis

import com.rsstudio.tweeky.data.network.model.Athlete
import retrofit2.http.GET

interface AthleteApiInterface {

    @GET("tweek.json")
    suspend fun getAthletes(
    ): Athlete
}