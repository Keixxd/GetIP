package com.keixxdd.getip.data.api

import com.keixxdd.getip.domain.model.ipData
import retrofit2.Response
import retrofit2.http.GET

interface GetIpService {

    @GET("awstest-service")
    suspend fun getIp(): Response<ipData>

}