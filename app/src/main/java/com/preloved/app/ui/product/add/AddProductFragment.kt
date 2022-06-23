package com.preloved.app.ui.product.add


import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.lifecycle.LifecycleOwner
import com.preloved.app.base.arch.BaseFragment
import com.preloved.app.base.model.Resource
import com.preloved.app.data.network.model.response.CategoryResponseItem
import com.preloved.app.data.network.model.response.PostProductResponse
import com.preloved.app.databinding.FragmentAddProductBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File


class AddProductFragment : BaseFragment<FragmentAddProductBinding, AddProductViewModel>(
    FragmentAddProductBinding::inflate
), AddProductContract.View {
    private var selectedPicture: File? = null
    override val viewModel: AddProductViewModel by viewModel()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
    }
    override fun initView() {

    }

    override fun checkFormValidation(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setDataToView(data: List<CategoryResponseItem>) {
        getViewBinding().apply {
            //etNamaProduk.setText(data.name)
        }
        val type = data.toTypedArray().map { it.name }

        val adapter = activity?.let {
            ArrayAdapter(
                it, com.preloved.app.R.layout.category_item,
                type
            )
        }

        val editTextFilledExposedDropdown: AutoCompleteTextView =
            getViewBinding().filledCategory
        editTextFilledExposedDropdown.setAdapter(adapter)
    }

    override fun getData() {
        viewModel.getCategoryData()
    }

    override fun setOnClickListeners() {
        TODO("Not yet implemented")
    }
    override fun observeData() {
        super.observeData()
        viewModel.getCategoryLiveData().observe(viewLifecycleOwner) { response ->
            when(response){
                is Resource.Loading -> {
                    showLoading(true)
                    showContent(false)
                }
                is Resource.Success -> {
                    showLoading(false)
                    showContent(true)
                    response.data?.let { setDataToView(it) }
                }
                is Resource.Error -> {
                    showLoading(false)
                    showContent(true)
                    showError(true, response.message)
                }
            }
        }
    }
}