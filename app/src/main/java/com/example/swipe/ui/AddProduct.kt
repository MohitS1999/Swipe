package com.example.swipe.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.swipe.R
import com.example.swipe.databinding.FragmentAddProductBinding
import dagger.hilt.android.AndroidEntryPoint


private const val TAG = "AddProduct"
@AndroidEntryPoint
class AddProduct : Fragment() {

    private lateinit var binding:FragmentAddProductBinding
    private var productTypes = ArrayList<String>()
    private lateinit var adapterItems:ArrayAdapter<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddProductBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addingProductTypes()
        Log.d(TAG, "onViewCreated: $productTypes")
        adapterItems = ArrayAdapter(requireContext(),R.layout.list_item,productTypes)
        binding.autoComplete.setAdapter(adapterItems)

        binding.autoComplete.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            Toast.makeText(
                context,
                "" + binding.autoComplete.getText().toString(),
                Toast.LENGTH_SHORT
            ).show()
        }


    }

    private fun addingProductTypes() {
        productTypes.add("Vegetables")
        productTypes.add("Deodrants")
        productTypes.add("Perfume")
        productTypes.add("Grocery")
        productTypes.add("Oil")
    }


}