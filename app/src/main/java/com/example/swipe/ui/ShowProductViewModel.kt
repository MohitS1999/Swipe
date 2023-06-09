package com.example.swipe.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swipe.model.ProductData
import com.example.swipe.model.ProductDataItem
import com.example.swipe.repository.ApiRepository
import com.example.swipe.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject
import kotlin.math.log

private const val TAG = "ShowProductViewModel"
@HiltViewModel
class ShowProductViewModel @Inject constructor(private val repository:ApiRepository) : ViewModel() {


    private val _getData = MutableLiveData<UiState<ArrayList<ProductDataItem>>>()
    val getData : LiveData<UiState<ArrayList<ProductDataItem>>>
        get() = _getData

    init {
        getAllData()
    }



    private fun getAllData() {
        _getData.value =UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            repository.getData().let {
                if (it.isSuccessful){
                    _getData.postValue(UiState.Success(it.body()!!))
                }else{

                    Log.d(TAG, "getAllData: ${it.message()}")
                }
            }
        }
    }

}