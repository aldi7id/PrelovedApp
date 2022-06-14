package com.preloved.app.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.preloved.app.R
import com.preloved.app.base.arch.BaseFragment
import com.preloved.app.databinding.FragmentRegisterBinding


class RegisterFragment : BaseFragment<FragmentRegisterBinding, RegisterViewModel>(
    FragmentRegisterBinding::inflate
), RegisterContract.View {

    override fun initView() {
        observeData()
    }





    override fun observeData() {

    }


}