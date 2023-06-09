package com.example.swipe.repository

import android.util.Log
import android.widget.Toast
import com.example.swipe.api.SwipeApi
import com.example.swipe.model.ProductDataItem
import com.example.swipe.util.UiState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "PostRepositoryImpl"
class PostRepositoryImpl (private val swipeApi: SwipeApi) : PostRespository {

    override suspend fun addData(
        productName: String,
        price: Double,
        tax: Double,
        productType: String,
        result: (UiState<String>) -> Unit
    ) {
        Log.d(TAG, "addData: postrepository impl")
        swipeApi.addData("",price,productName,productType,tax).enqueue(object : Callback<ProductDataItem>{
            override fun onResponse(
                call: Call<ProductDataItem>,
                response: Response<ProductDataItem>
            ) {
                Log.d(TAG, "onResponse: ")
                result.invoke(UiState.Success("Data successfully added"))
            }

            override fun onFailure(call: Call<ProductDataItem>, t: Throwable) {
                Log.d(TAG, "onFailure: ")
                result.invoke(UiState.Failure("Error!!!"))
            }

        })
    }
}