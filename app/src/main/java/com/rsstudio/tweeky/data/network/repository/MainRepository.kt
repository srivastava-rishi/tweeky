package com.rsstudio.tweeky.data.network.repository

import com.rsstudio.tweeky.data.network.apis.AthleteApiInterface
import javax.inject.Inject

class MainRepository
@Inject
constructor(private val api: AthleteApiInterface) {

    suspend fun getAthleteData()  = api.getAthletes()

}