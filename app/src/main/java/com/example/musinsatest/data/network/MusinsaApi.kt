package com.example.musinsatest.data.network

import com.example.musinsatest.data.dto.MusinsaDataResponse
import retrofit2.Response
import retrofit2.http.GET

interface MusinsaApi {

    @GET("interview/list.json")
    suspend fun getMusinsaData(): Response<MusinsaDataResponse>

}