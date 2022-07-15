package com.example.musinsatest.data.repository

import com.example.musinsatest.data.dto.MusinsaDataResponse
import retrofit2.Response

interface HomeRepository {

    suspend fun getMusinsaData(): Response<MusinsaDataResponse>

}