package com.preloved.app.ui.fragment.homepage.home.bid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.preloved.app.base.model.Resource
import com.preloved.app.data.network.model.request.bid.BidRequest
import com.preloved.app.data.network.model.response.bid.post.PostBidResponse
import com.preloved.app.databinding.FragmentPopUpBidBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PopUpBidFragment(
    private val productId: Int
): BottomSheetDialogFragment() {
    private var bind: FragmentPopUpBidBinding? = null
    private val binding get() = bind!!
    private val viewModel : PopUpBidViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentPopUpBidBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        onClick()
        observerData()
    }

    private fun onClick() {
        binding.apply {
            btnTawar.setOnClickListener {
                viewModel.postBidProductOrderById(
                    BidRequest(
                        productId = productId,
                        bidPrice = etTawaran.text.toString().toInt()
                    )
                )
            }
        }
    }

    private fun observerData() {
        viewModel.apply {
            postBidProductOrderByIdResult().observe(viewLifecycleOwner) {
                when(it) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        postData(it.data)
                    }
                    is Resource.Error -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        dismiss()
                    }
                }
            }
        }
    }

    private fun postData(data: PostBidResponse?) {
        binding.apply {
            when(data?.status) {
                "pending" -> {
                    Toast.makeText(requireContext(), "Your bid product Success", Toast.LENGTH_SHORT).show()
                    dismiss()
                }
            }
        }
    }

}