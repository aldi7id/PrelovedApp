package com.preloved.app.ui.profile.edit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.preloved.app.R
import com.preloved.app.base.arch.BaseFragment
import com.preloved.app.databinding.FragmentEditProfileBinding

class EditProfileFragment(override val viewModel: EditProfileViewModel) : BaseFragment<FragmentEditProfileBinding, EditProfileViewModel>(
    FragmentEditProfileBinding::inflate
), EditProfileContract.View {
    override fun initView() {

    }

}