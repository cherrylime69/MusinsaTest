package com.example.musinsatest.data.repository

import com.example.musinsatest.data.dto.MusinsaDataResponse
import com.example.musinsatest.data.remote.MusinsaDataSource
import retrofit2.Response
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(private val musinsaDataSource: MusinsaDataSource) : HomeRepository {

    override suspend fun getMusinsaData(): Response<MusinsaDataResponse> {
        return musinsaDataSource.getMusinsaData()
    }

}