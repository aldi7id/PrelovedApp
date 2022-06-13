package com.preloved.app.base.arch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class GenericViewModelFactory(private val viewModel: ViewModel): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(viewModel::class.java) -> {
                return viewModel as T
            }
            else -> throw IllegalArgumentException("Unknown class name")
        }
    }
}