package com.example.swipe.repository

import com.example.swipe.model.ProductDataItem
import com.example.swipe.util.UiState
import retrofit2.Call

interface PostRespository {

    suspend fun addData(productName:String,price:Double,tax:Double,productType:String,result:(UiState<String>) -> Unit)
}