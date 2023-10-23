package com.slaytertv.firegym

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.slaytertv.firegym.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.include2.toolbar)
        drawerLayout = binding.drawer
        navigationView = binding.navigationView

        // Configurar el NavController
        navController = findNavController(R.id.fragmentContainerView)

        // Configurar la AppBarConfiguration
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.calculatorFragment,
                R.id.exerciseListFragment,
                R.id.myOwnWorkoutFragment
            ),
            drawerLayout
        )
        // Configurar la ActionBar con el NavController y la AppBarConfiguration
        setupActionBarWithNavController(navController, drawerLayout)
        // Configurar la NavigationView con el NavController
        navigationView.setupWithNavController(navController)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentContainerView)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
 }