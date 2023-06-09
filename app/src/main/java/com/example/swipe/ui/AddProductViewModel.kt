package com.example.swipe.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swipe.model.ProductDataItem
import com.example.swipe.repository.PostRepositoryImpl
import com.example.swipe.repository.PostRespository
import com.example.swipe.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "AddProductViewModel"
@HiltViewModel
class AddProductViewModel @Inject constructor(private val repository: PostRespository) : ViewModel(){

    private val _addData = MutableLiveData<UiState<String>>()
    val addData : LiveData<UiState<String>>
        get() = _addData


    fun addProductData(
        productName: String,
        price: Double,
        tax: Double,
        productType: String,){
            _addData.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            repository.addData(productName,price,tax,productType){
                Log.d(TAG, "addProductData: $it")
                _addData.value = it
            }
        }

    }
}