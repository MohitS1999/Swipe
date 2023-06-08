package com.example.swipe.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.swipe.R
import com.example.swipe.databinding.ProductListBinding
import com.example.swipe.model.ProductData
import com.example.swipe.model.ProductDataItem
import java.security.AccessControlContext

private const val TAG = "ProductDataAdapter"
class ProductDataAdapter (
    private val context: Context,
    private val dataList:ArrayList<ProductDataItem>
        ) : RecyclerView.Adapter<ProductDataAdapter.ProductViewHolder>() , Filterable{

    private var fullDataList:ArrayList<ProductDataItem> = ArrayList(dataList)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductDataAdapter.ProductViewHolder {
        Log.d(TAG, "onCreateViewHolder: ")
        return ProductViewHolder(ProductListBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: ProductDataAdapter.ProductViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: ")
        val data = dataList[position]
        Glide.with(holder.itemView)
            .load(data.image)
            .fitCenter()
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .error(R.drawable.image_not_supported)
            .into(holder.imageView)
        holder.productName.text = data.product_name
        holder.productType.text = data.product_type
        holder.price.text = "$ " + data.price.toString()
        holder.tax.text = data.tax.toString() + " %TAX"

    }

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount: ")
        return dataList.size
    }

    override fun getFilter(): Filter {
        Log.d(TAG, "getFilter: ")
        return filterUser()
    }

    private fun filterUser() = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val searchText:String = constraint.toString().toLowerCase()
            val temp:ArrayList<ProductDataItem> = ArrayList()
            if (searchText.length == 0 || searchText.isEmpty()){
                temp.addAll(fullDataList)
            }else{
                for (document in fullDataList){
                    Log.d(TAG, "performFiltering: $document")
                    if (document.product_name.toLowerCase()?.contains(searchText) == true || document.product_type.toLowerCase()?.contains(searchText) == true){
                        temp.add(document)
                    }
                }
            }
            Log.d(TAG, "performFiltering: $temp ")
            val filterResult = FilterResults()
            filterResult.values = temp
            return filterResult
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            dataList.clear()
            if (results != null){
                dataList.addAll(results.values as Collection<ProductDataItem>)
            }
            notifyDataSetChanged()
        }

    }

    class ProductViewHolder(binding: ProductListBinding) :RecyclerView.ViewHolder(binding.root){

        val imageView = binding.imageView
        val productName = binding.productName
        val productType = binding.productType
        val price = binding.price
        val tax = binding.tax
    }


}