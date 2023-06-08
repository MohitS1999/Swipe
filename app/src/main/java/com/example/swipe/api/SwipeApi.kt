package com.example.swipe.api


import com.example.swipe.model.ProductData
import retrofit2.Response
import retrofit2.http.GET

interface SwipeApi {

    companion object{
        const val BASE_URL =  "https://app.getswipe.in/api/public/"
    }

    @GET("get")
    suspend fun getData() :Response<ProductData>
}