package com.preloved.app.ui.fragment.homepage.home.category.all

import android.util.Log
import com.preloved.app.base.arch.BaseFragment
import com.preloved.app.base.model.Resource
import com.preloved.app.data.network.model.request.category.CategoryResponse
import com.preloved.app.databinding.FragmentCategoryAllBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoryAllFragment : BaseFragment<FragmentCategoryAllBinding, CategoryAllViewModel>(
    FragmentCategoryAllBinding::inflate
), CategoryAllContract.View {
    override val viewModel: CategoryAllViewModel by viewModel()

    override fun initView() {
        onClick()
    }

    private fun onClick() {
        getViewBinding().apply {

        }
    }

    override fun getDataCategory() {
        viewModel.apply {
            getDataAllCategory()
        }
    }

    override fun observeData() {
        viewModel.apply {
            getAllCategoryResult().observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        showError(true, it.message)
                        getData(it.data?.subList(id, Int.SIZE_BYTES))
                        Log.d("tes", "${it.data?.subList(0, Int.MAX_VALUE)}")
                    }
                    is Resource.Error -> {
                        showError(true, it.message)
                    }
                }
            }
        }
    }

    private fun getData(subList: MutableList<CategoryResponse.CategoryResponseItem>?) {
        getViewBinding().apply {
            val listCategoryAdapter = CategoryAllAdapter {

            }
            listCategoryAdapter.submitList(subList)
            rvListCategory.adapter = listCategoryAdapter
            Log.d("tes", subList.toString())
        }
    }

}