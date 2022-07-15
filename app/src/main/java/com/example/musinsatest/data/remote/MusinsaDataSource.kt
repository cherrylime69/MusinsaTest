package com.example.musinsatest.data.remote

import com.example.musinsatest.data.dto.MusinsaDataResponse
import retrofit2.Response

interface MusinsaDataSource {

    suspend fun getMusinsaData(): Response<MusinsaDataResponse>

}