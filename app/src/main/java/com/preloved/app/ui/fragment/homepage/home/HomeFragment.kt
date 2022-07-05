package com.preloved.app.ui.fragment.homepage.home

import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.preloved.app.databinding.FragmentHomeBinding
import com.preloved.app.ui.fragment.homepage.home.category.all.CategoryAllFragment
import com.preloved.app.ui.fragment.homepage.home.category.food.CategoryFoodFragment

class HomeFragment : Fragment() {
    private var bind: FragmentHomeBinding? = null
    private val binding get() = bind!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tabLayoutView()
    }

    private fun tabLayoutView() {
        binding.apply {

            val listTab = arrayListOf(
                CategoryAllFragment(),
                CategoryFoodFragment()
            )

            val vpAdapter =
                HomeAdapter(listTab, requireActivity().supportFragmentManager, lifecycle)
            vpCategoryItem.adapter = vpAdapter
            TabLayoutMediator(tlCategoryItem, vpCategoryItem) { tab, position ->
                tab.text = when (position) {
                    0 -> {
                        "Semua"
                    }
                    1 -> {
                        "Food"
                    }
                    else -> {
                        throw Resources.NotFoundException("Position Not Found")

                    }
                }
            }.attach()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        bind = null
    }

}