package com.example.swipe.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.swipe.R
import com.example.swipe.adapter.ProductDataAdapter
import com.example.swipe.databinding.FragmentShowProductBinding
import com.example.swipe.model.ProductData
import com.example.swipe.model.ProductDataItem
import com.example.swipe.util.UiState
import dagger.hilt.android.AndroidEntryPoint


private const val TAG = "ShowProduct"
@AndroidEntryPoint
class ShowProduct : Fragment() {

    private lateinit var productAdapter: ProductDataAdapter
    private lateinit var binding: FragmentShowProductBinding
    private lateinit var dataList : ArrayList<ProductDataItem>
    private val viewModel by viewModels<ShowProductViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView: ")
        binding = FragmentShowProductBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataList = ArrayList()
        observer()

        binding.searchView.clearFocus()
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                try{
                    productAdapter.filter.filter(newText.toString())
                } catch (e:Exception){
                    Log.d(TAG, "onQueryTextChange: ${e.printStackTrace()}")
                }
                return false
            }

        })

        binding.floatingButton.setOnClickListener(){
            findNavController().navigate(R.id.action_showProduct_to_addProduct,Bundle().apply {  })
        }
    }

    private fun observer() {
        viewModel.getData.observe(viewLifecycleOwner){
            when(it){
                is UiState.Success -> {
                    Log.d(TAG, "onViewCreated: Success")
                    dataList = it.data
                    Log.d(TAG, "onViewCreated: $dataList")
                    updateRecyclerView()
                }
                is UiState.Loading -> {
                    Log.d(TAG, "onViewCreated: Loading")
                }
                is UiState.Failure -> {
                    Log.d(TAG, "onViewCreated: Failure")
                }
            }
        }
    }

    private fun updateRecyclerView() {
        Log.d(TAG, "updateRecyclerView: ")
        binding.productListRV.setHasFixedSize(true)
        binding.productListRV.setItemViewCacheSize(20)
        binding.productListRV.addItemDecoration(
            DividerItemDecoration(context,
                DividerItemDecoration.VERTICAL)
        )
        binding.productListRV.layoutManager = LinearLayoutManager(context)
        productAdapter = ProductDataAdapter(requireContext(),dataList)
        binding.productListRV.adapter = productAdapter
    }


}