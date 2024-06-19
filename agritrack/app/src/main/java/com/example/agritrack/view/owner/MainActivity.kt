package com.example.agritrack.view.owner

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.agritrack.R
import com.example.agritrack.databinding.ActivityMainBinding
import com.example.agritrack.view.owner.forecasting.ForecastingFragment
import com.example.agritrack.view.owner.product.ProductInfoFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var currentFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val fragmentManager = supportFragmentManager
        val homeFragment = ProductInfoFragment()
        val fragment = fragmentManager.findFragmentByTag(ProductInfoFragment::class.java.simpleName)

        if (fragment !is ProductInfoFragment) {
            fragmentManager.commit {
                add(R.id.frame_container, homeFragment, ProductInfoFragment::class.java.simpleName)
            }
        }

        binding.bottomNav.inflateMenu(R.menu.owner_menu)
        currentFragment = ProductInfoFragment()

        binding.bottomNav.setOnItemSelectedListener {
            if (it.itemId == R.id.product) {
                currentFragment = ProductInfoFragment()
            } else if (it.itemId == R.id.forecast) {
                currentFragment = ForecastingFragment()
            }

            fragmentManager.commit {
                replace(R.id.frame_container, currentFragment)
            }

            true
        }
    }
}