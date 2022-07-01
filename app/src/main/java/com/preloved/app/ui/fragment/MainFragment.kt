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
        val navView : BottomNavigationView = binding.navigationBar
        val navController = activity?.findNavController(R.id.fragmentContainerView)
        navController?.let {
            navView.setupWithNavController(navController)
        }
        navController?.addOnDestinationChangedListener{ _, destination, _ ->
            when(destination.id){
                R.id.loginFragment3 -> {
                    binding.navigationBar.visibility = View.GONE
                }
                R.id.editProfileFragment -> {
                    binding.navigationBar.visibility = View.GONE
                }
                R.id.registerFragment2 -> {
                    binding.navigationBar.visibility = View.GONE
                }
                else -> {
                    binding.navigationBar.visibility = View.VISIBLE
                }
            }

        }
    }
}