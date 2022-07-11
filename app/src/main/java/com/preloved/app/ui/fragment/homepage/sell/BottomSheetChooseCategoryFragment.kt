package com.preloved.app.ui.fragment.homepage.sell

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.preloved.app.R
import com.preloved.app.base.arch.BaseFragment
import com.preloved.app.base.model.Resource
import com.preloved.app.databinding.FragmentBottomSheetChooseCategoryBinding
import com.preloved.app.databinding.FragmentSellBinding
import com.preloved.app.ui.listCategory
import com.preloved.app.ui.listCategoryId
import org.koin.androidx.viewmodel.ext.android.viewModel


class BottomSheetChooseCategoryFragment(private val update: ()->Unit) : BottomSheetDialogFragment()  {
    private var _binding : FragmentBottomSheetChooseCategoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SellViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetChooseCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            binding.btnKirimCategory.setOnClickListener {
                viewModel.addCategory(listCategory)
                Handler().postDelayed({
                    binding.pbLoading.visibility = View.GONE
                    update.invoke()
                    dismiss()
                }, 1000)
            }
                viewModel.getCategoryData()
                viewModel.getCategoryLiveData().observe(viewLifecycleOwner){ response ->
                    when(response){
                        is Resource.Loading -> {

                        }
                        is Resource.Success -> {
                            binding.pbLoading.visibility = View.GONE
                            if(response.data != null){
                                val adapter = BottomSheetCategoryAdapter(
                                    selected = { selected ->
                                        listCategory.add(selected.name)
                                        listCategoryId.add(selected.id)
                                    },
                                    unselected = { unselected ->
                                        listCategory.remove(unselected.name)
                                        listCategoryId.removeAll(listOf(unselected.id))
                                    }
                                )
                                adapter.submitData(response.data)
                                binding.rvPilihKategori.adapter = adapter
                            }
                        }
                        is Resource.Error -> {
                            AlertDialog.Builder(requireContext())
                                .setMessage(response.message)
                                .setPositiveButton("Ok"){ dialog, _ ->
                                    dialog.dismiss()
                                    findNavController().popBackStack()
                                }
                                .show()
                        }
                    }

            }
    }
}