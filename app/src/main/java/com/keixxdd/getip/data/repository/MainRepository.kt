package com.keixxdd.getip.data.repository

import com.keixxdd.getip.domain.model.ipData
import com.keixxdd.getip.utils.Resource

interface MainRepository {

    suspend fun getIpData(): Resource<ipData>
}