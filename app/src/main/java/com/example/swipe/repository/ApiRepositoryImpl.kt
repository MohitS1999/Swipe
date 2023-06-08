package com.example.swipe.repository

import android.util.Log
import com.example.swipe.api.SwipeApi
import com.example.swipe.model.ProductData
import com.example.swipe.util.UiState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

private const val TAG = "ApiRepositoryImpl"
class ApiRepositoryImpl (private val swipeApi: SwipeApi)  : ApiRepository{

    override suspend fun getData() = swipeApi.getData()
}