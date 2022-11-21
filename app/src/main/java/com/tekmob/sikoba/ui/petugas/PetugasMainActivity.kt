package com.tekmob.sikoba.ui.petugas

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import com.tekmob.sikoba.R
import com.tekmob.sikoba.auth.UserPreference
import com.tekmob.sikoba.dataStore
import com.tekmob.sikoba.databinding.ActivityPetugasMainBinding
import com.tekmob.sikoba.ui.ViewModelFactory
import com.tekmob.sikoba.ui.login.LoginViewModel
import com.tekmob.sikoba.ui.pengguna.MainActivity

class PetugasMainActivity : AppCompatActivity(){

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityPetugasMainBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPetugasMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarPetugasMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_petugas_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_daftar_bencana, R.id.nav_logout
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_logout -> {
                    viewModel.logout()
                    true
                }
                else -> true
            }
        }

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[LoginViewModel::class.java]

        viewModel.getUser().observe(this) {
            if (!it.isLogin) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_petugas_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

//    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.nav_logout -> {
//                Log.d("PetugasMainActivity", "clicked logout")
//                //viewModel.logout()
//            }
//            R.id.nav_daftar_bencana -> {
//                Log.d("PetugasMainActivity", "clicked daftar bencana")
//            }
//        }
//
//        binding.drawerLayout.closeDrawer(GravityCompat.START)
//        return true
//    }
}