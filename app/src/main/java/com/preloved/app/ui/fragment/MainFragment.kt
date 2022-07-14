package com.preloved.app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.preloved.app.R
import com.preloved.app.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private var bind: FragmentMainBinding? = null
    private val binding get() = bind!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            val navView : BottomNavigationView = navigationBar
            val navController = activity?.findNavController(R.id.fragmentContainerView)
            navController?.let {
                navView.setupWithNavController(navController)
            }
            navController?.addOnDestinationChangedListener{ _, destination, _ ->
                when(destination.id){
                    R.id.homeFragment -> {
                        navigationBar.visibility = View.VISIBLE
                    }
                    R.id.notificationFragment -> {
                        navigationBar.visibility = View.VISIBLE
                    }
                    R.id.sellFragment -> {
                        navigationBar.visibility = View.VISIBLE
                    }
                    R.id.saleFragment -> {
                        navigationBar.visibility = View.VISIBLE
                    }
                    R.id.accountFragment -> {
                        navigationBar.visibility = View.VISIBLE
                    }
                    else -> {
                        navigationBar.visibility = View.GONE
                    }
                }
            }
        }
    }
}