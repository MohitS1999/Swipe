package com.example.swipe.api


import com.example.swipe.model.ProductData
import com.example.swipe.model.ProductDataItem
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface SwipeApi {

    companion object{
        const val BASE_URL =  "https://app.getswipe.in/api/public/"
    }

    @GET("get")
    suspend fun getData() :Response<ProductData>

    @FormUrlEncoded
    @POST("add")

    fun addData(
        @Field("image") imageName:String,
        @Field("price") price:Double,
        @Field("product_name") productName:String,
        @Field("product_type") productType:String,
        @Field("tax") tax:Double
    ) : Call<ProductDataItem>
}