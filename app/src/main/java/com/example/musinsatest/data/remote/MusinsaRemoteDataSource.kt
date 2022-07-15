package com.example.musinsatest.data.remote

import com.example.musinsatest.data.dto.MusinsaDataResponse
import com.example.musinsatest.data.network.MusinsaApi
import retrofit2.Response
import javax.inject.Inject

class MusinsaRemoteDataSource @Inject constructor(private val api: MusinsaApi) : MusinsaDataSource {

    override suspend fun getMusinsaData(): Response<MusinsaDataResponse> {
        return api.getMusinsaData()
    }

}