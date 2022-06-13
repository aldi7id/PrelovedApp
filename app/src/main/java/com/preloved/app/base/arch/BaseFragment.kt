package com.preloved.app.base.arch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import java.lang.IllegalArgumentException

abstract class BaseFragment<VB: ViewBinding, VM: ViewModel>(
    private val bindingFactory: (LayoutInflater) -> VB
): Fragment(), BaseContract.BaseView {

    private var bind: VB? = null
    private val binding: VB get() = bind as VB
    private lateinit var viewModelInstance: VM

    fun getViewBinding(): VB = binding
    fun getViewModel(): VM = viewModelInstance

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = bindingFactory.invoke(inflater)
        when (bind) {
            null -> throw IllegalArgumentException("Binding cannot be null")
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeData()
    }

    override fun onDestroy() {
        super.onDestroy()
        bind = null
    }

    abstract fun initView()
    override fun observeData() {
        //do nothing
    }

    override fun showContent(isVisible: Boolean) {
        //do nothing
    }

    override fun showLoading(isVisible: Boolean) {
        //do nothing
    }

    override fun showError(isErrorEnabled: Boolean, msg: String?) {
        when{
            isErrorEnabled -> {
                Toast.makeText(requireContext(), "Message: $msg", Toast.LENGTH_SHORT).show()
            }
        }
    }
}