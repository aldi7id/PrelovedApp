package com.preloved.app.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.preloved.app.R
import com.preloved.app.base.arch.BaseFragment
import com.preloved.app.databinding.FragmentProfileBinding


class ProfileFragment(override val viewModel: ProfileViewModel) : BaseFragment<FragmentProfileBinding, ProfileViewModel>(
    FragmentProfileBinding::inflate
), ProfileContract.View {
    override fun initView() {

    }


}