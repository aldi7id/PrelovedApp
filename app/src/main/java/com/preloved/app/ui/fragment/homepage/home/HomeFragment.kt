package com.preloved.app.ui.fragment.homepage.home

import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.preloved.app.R
import com.preloved.app.databinding.FragmentHomeBinding
import com.preloved.app.ui.fragment.homepage.home.category.all.CategoryAllFragment
import com.preloved.app.ui.fragment.homepage.home.category.computer.CategoryComputerFragment
import com.preloved.app.ui.fragment.homepage.home.category.electronic.CategoryElectronicFragment
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
        onClick()
        tabLayoutView()
    }

    private fun onClick() {
        binding.apply {
            svSearchItem.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_searchProductFragment)
            }
        }
    }

    private fun tabLayoutView() {
        binding.apply {

            val listTab = arrayListOf(
                CategoryAllFragment(),
                CategoryElectronicFragment(),
                CategoryFoodFragment(),
                CategoryComputerFragment()
            )

            val vpAdapter =
                HomeAdapter(listTab, requireActivity().supportFragmentManager, lifecycle)
            vpCategoryItem.adapter = vpAdapter
            TabLayoutMediator(tlCategoryItem, vpCategoryItem) { tab, position ->
                tab.text = when (position) {
                    0 -> { "Semua" }
                    1 -> { "Electronic" }
                    2 -> { "Food" }
                    3 -> { "Smartphone" }
                    4 -> { "Cloth for Men" }
                    5 -> { "Shoes for Men" }
                    6 -> { "Bag for Men" }
                    7 -> { "Accessories" }
                    8 -> { "Healthy" }
                    9 -> { "Hobby" }
                    10 -> { "Computer" }
                    11 -> { "Beauty" }
                    12 -> { "Home Supplies" }
                    13 -> { "Cloth for Women" }
                    14 -> { "Muslim Fashion" }
                    15 -> { "Baby Fashion" }
                    16 -> { "Mom and Baby" }
                    17 -> { "Shoes for Women" }
                    18 -> { "Bag for Women" }
                    19 -> { "Automotive" }
                    20 -> { "Workout" }
                    21 -> { "Book and Pen" }
                    22 -> { "Voucher" }
                    23 -> { "Souvenir" }
                    24 -> { "Photographer" }
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