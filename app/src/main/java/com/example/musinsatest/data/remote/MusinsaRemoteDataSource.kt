package com.example.musinsatest.data.remote

import com.example.musinsatest.data.api.MusinsaNetwork
import com.example.musinsatest.data.dto.MusinsaDataResponse
import com.example.musinsatest.data.network.MusinsaApi
import retrofit2.Response

class MusinsaRemoteDataSource(private val api: MusinsaApi) : MusinsaDataSource {

    override suspend fun getMusinsaData(): Response<MusinsaDataResponse> {
        return api.getMusinsaData()
    }

}