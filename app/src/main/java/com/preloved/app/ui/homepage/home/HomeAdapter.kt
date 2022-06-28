package com.preloved.app.ui.homepage.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.preloved.app.base.arch.BaseFragment
import com.preloved.app.base.arch.BaseViewModellmpl

class HomeAdapter(
    private val listFragment: ArrayList<BaseFragment<out ViewBinding, out BaseViewModellmpl>>,
    fm: FragmentManager,
    lf: Lifecycle
) : FragmentStateAdapter(fm, lf) {
    override fun getItemCount(): Int {
        return listFragment.size
    }

    override fun createFragment(position: Int): Fragment {
        return listFragment[position]
    }

}