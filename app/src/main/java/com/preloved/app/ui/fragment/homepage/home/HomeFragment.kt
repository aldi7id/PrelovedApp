package com.preloved.app.ui.fragment.homepage.home

import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.preloved.app.databinding.FragmentHomeBinding
import com.preloved.app.ui.fragment.homepage.home.category.accessories.CategoryAccessoriesFragment
import com.preloved.app.ui.fragment.homepage.home.category.all.CategoryAllFragment
import com.preloved.app.ui.fragment.homepage.home.category.automotive.CategoryAutomotiveFragment
import com.preloved.app.ui.fragment.homepage.home.category.babyfashion.CategoryBabyFashionFragment
import com.preloved.app.ui.fragment.homepage.home.category.beauty.CategoryBeautyFragment
import com.preloved.app.ui.fragment.homepage.home.category.bookandpen.CategoryBookAndPenFragment
import com.preloved.app.ui.fragment.homepage.home.category.computer.CategoryComputerFragment
import com.preloved.app.ui.fragment.homepage.home.category.electronic.CategoryElectronicFragment
import com.preloved.app.ui.fragment.homepage.home.category.fashionmuslim.CategoryFashionMuslimFragment
import com.preloved.app.ui.fragment.homepage.home.category.food.CategoryFoodFragment
import com.preloved.app.ui.fragment.homepage.home.category.healthy.CategoryHealthyFragment
import com.preloved.app.ui.fragment.homepage.home.category.hobby.CategoryHobbyFragment
import com.preloved.app.ui.fragment.homepage.home.category.homesupplies.CategoryHomeSuppliesFragment
import com.preloved.app.ui.fragment.homepage.home.category.man.bag.CategoryBagMenFragment
import com.preloved.app.ui.fragment.homepage.home.category.man.cloth.CategoryClothMenFragment
import com.preloved.app.ui.fragment.homepage.home.category.man.shoes.CategoryShoesMenFragment
import com.preloved.app.ui.fragment.homepage.home.category.momandbaby.CategoryMomAndBabyFragment
import com.preloved.app.ui.fragment.homepage.home.category.photographer.CategoryPhotographerFragment
import com.preloved.app.ui.fragment.homepage.home.category.smartphone.CategorySmartphoneFragment
import com.preloved.app.ui.fragment.homepage.home.category.souvenir.CategorySouvenirFragment
import com.preloved.app.ui.fragment.homepage.home.category.voucher.CategoryVoucherFragment
import com.preloved.app.ui.fragment.homepage.home.category.woman.bag.CategoryBagWomenFragment
import com.preloved.app.ui.fragment.homepage.home.category.woman.cloth.CategoryClothWomenFragment
import com.preloved.app.ui.fragment.homepage.home.category.woman.shoes.CategoryShoesWomenFragment
import com.preloved.app.ui.fragment.homepage.home.category.workout.CategoryWorkoutFragment

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
        onView()
        onClick()
        tabLayoutView()
    }

    private fun onView() {
        binding.apply {
            swipeRefreshLayout.setOnRefreshListener {
                Handler(Looper.getMainLooper()).postDelayed({
                    swipeRefreshLayout.isRefreshing = false
                    tabLayoutView()
                }, 2000)
            }
        }
    }

    private fun onClick() {
        binding.apply {
            with(svSearchItem) {
                setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(p0: String?): Boolean {
                        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSearchProductFragment(
                            search = p0.toString()
                        ))
                        return false
                    }
                    override fun onQueryTextChange(p0: String?): Boolean {
                        return false
                    }
                })
            }
        }
    }

    private fun tabLayoutView() {
        binding.apply {

            val listTab = arrayListOf(
                CategoryAllFragment(),
                CategoryElectronicFragment(),
                CategoryComputerFragment(),
                CategorySmartphoneFragment(),
                CategoryClothMenFragment(),
                CategoryShoesMenFragment(),
                CategoryBagMenFragment(),
                CategoryAccessoriesFragment(),
                CategoryHealthyFragment(),
                CategoryHobbyFragment(),
                CategoryFoodFragment(),
                CategoryBeautyFragment(),
                CategoryHomeSuppliesFragment(),
                CategoryClothWomenFragment(),
                CategoryFashionMuslimFragment(),
                CategoryBabyFashionFragment(),
                CategoryMomAndBabyFragment(),
                CategoryShoesWomenFragment(),
                CategoryBagWomenFragment(),
                CategoryAutomotiveFragment(),
                CategoryWorkoutFragment(),
                CategoryBookAndPenFragment(),
                CategoryVoucherFragment(),
                CategorySouvenirFragment(),
                CategoryPhotographerFragment()
            )

            val vpAdapter =
                HomeAdapter(listTab, requireActivity().supportFragmentManager, lifecycle)
            vpCategoryItem.adapter = vpAdapter
            TabLayoutMediator(tlCategoryItem, vpCategoryItem) { tab, position ->
                tab.text = when (position) {
                    0 -> { "Semua" }
                    1 -> { "Electronic" }
                    2 -> { "Computer" }
                    3 -> { "Smartphone" }
                    4 -> { "Cloth for Men" }
                    5 -> { "Shoes for Men" }
                    6 -> { "Bag for Men" }
                    7 -> { "Accessories" }
                    8 -> { "Healthy" }
                    9 -> { "Hobby" }
                    10 -> { "Food" }
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