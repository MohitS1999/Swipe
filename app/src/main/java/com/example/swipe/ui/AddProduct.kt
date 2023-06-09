package com.example.swipe.ui

import android.app.Activity
import android.app.UiAutomation
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.swipe.R
import com.example.swipe.api.SwipeApi
import com.example.swipe.databinding.FragmentAddProductBinding
import com.example.swipe.model.ProductDataItem
import com.example.swipe.util.UiState
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val TAG = "AddProduct"
@AndroidEntryPoint
class AddProduct : Fragment() {

    private lateinit var binding:FragmentAddProductBinding
    private var productTypes = ArrayList<String>()
    private lateinit var adapterItems:ArrayAdapter<String>
    private lateinit var imageUri:Uri
    private val viewModel by viewModels<AddProductViewModel>()


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


        binding.uploadBtn.setOnClickListener {
            ImagePicker.with(this)
                .cropSquare()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start()
        }

        binding.submitData.setOnClickListener {
            Log.d(TAG, "onViewCreated: submit data")
            if (isValidation()){
                val productName : String = binding.editProductName.text.toString()
                val price : Double= binding.editPrice.text.toString().toDouble()
                val tax :Double = binding.editTax.text.toString().toDouble()
                val productType = binding.autoComplete.editableText.toString()
                observer()
                viewModel.addProductData(productName,price,tax,productType)

            }
        }




    }

    private fun observer() {
        viewModel.addData.observe(viewLifecycleOwner){
            when(it){
                is UiState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.submitData.visibility = View.VISIBLE
                    Toast.makeText(context,it.data,Toast.LENGTH_SHORT).show()
                    binding.editProductName.text?.clear()
                    binding.editTax.text?.clear()
                    binding.editPrice.text?.clear()
                    findNavController().navigate(R.id.action_addProduct_to_showProduct)
                }
                is UiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.submitData.visibility = View.INVISIBLE
                }
                is UiState.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    binding.submitData.visibility = View.VISIBLE
                    Toast.makeText(context,it.error,Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun isValidation(): Boolean {
        Log.d(TAG, "isValidation: ")

        if(binding.editProductName.text.isNullOrEmpty()){
            binding.editProductName.setError("mandatory field",resources.getDrawable(R.drawable.info))
            Log.d(TAG, "isValidation: product name empty")
            Toast.makeText(context,"Product Name is Empty",Toast.LENGTH_SHORT).show()
            return false
        }
        else if (binding.editPrice.text.isNullOrEmpty()){
            binding.editPrice.setError("mandatory field",resources.getDrawable(R.drawable.info))
            Toast.makeText(context," price is empty",Toast.LENGTH_SHORT).show()
            Log.d(TAG, "isValidation: price is empty")
            return false
        }else if (binding.editTax.text.isNullOrEmpty()){
            binding.editTax.setError("mandatory field",resources.getDrawable(R.drawable.info))
            Toast.makeText(context," tax is empty",Toast.LENGTH_SHORT).show()
            Log.d(TAG, "isValidation: tax is empty")
            return false
        }else if (binding.editTax.text.toString().toDouble() > 100) {
            Log.d(TAG, "isValidation: tax is greater than 100")
            Toast.makeText(context," tax is greater then 100",Toast.LENGTH_SHORT).show()
            binding.editTax.setError("% should not greater than 100",resources.getDrawable(R.drawable.info))
            return false
        }
        return true
    }

    private fun saveDataOnServer(productName :String,price : Double,tax:Double,productType:String) {
        // for image
        /*val filesDir = context?.filesDir
        val file = File(filesDir,"image.png")
        val inputStream = context?.contentResolver?.openInputStream(imageUri)
        val outputStream = FileOutputStream(file)
        inputStream!!.copyTo(outputStream)
        Log.d(TAG, "saveDataOnServer: $outputStream")*/
        Log.d(TAG, "saveDataOnServer: ")



        /*inputStream.close()
        outputStream.close()*/


    }

    private fun addingProductTypes() {
        productTypes.add("Vegetables")
        productTypes.add("Deodrants")
        productTypes.add("Perfume")
        productTypes.add("Grocery")
        productTypes.add("Oil")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            imageUri = data?.data!!
            binding.uploadBtn.text = "Uploaded"
            Log.d(TAG, "onActivityResult: $imageUri")
            binding.editProductName.text?.clear()
            binding.editTax.text?.clear()
            binding.editPrice.text?.clear()
            // Use Uri object instead of File to avoid storage permissions
            //imgProfile.setImageURI(fileUri)
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }


}