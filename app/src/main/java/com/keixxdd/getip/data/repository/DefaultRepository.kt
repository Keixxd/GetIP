package com.keixxdd.getip.data.repository

import com.keixxdd.getip.data.api.GetIpService
import com.keixxdd.getip.domain.model.ipData
import com.keixxdd.getip.utils.Resource
import javax.inject.Inject

class DefaultRepository @Inject constructor(
    private val service: GetIpService
): MainRepository {
    override suspend fun getIpData(): Resource<ipData> {
        return try{
            val responce = service.getIp()
            val result = responce.body()
            if(responce.isSuccessful){
                Resource.Success(result)
            }else{
                Resource.Failure("Unexpected error!")
            }
        }catch(e: Exception){
            Resource.Failure(e.message ?: "An Error Occurred")
        }
    }
}