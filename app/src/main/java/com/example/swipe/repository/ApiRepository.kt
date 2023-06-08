package com.example.swipe.repository

import com.example.swipe.model.ProductData
import com.example.swipe.util.UiState
import retrofit2.Response

interface ApiRepository {

    suspend fun getData() : Response<ProductData>
}