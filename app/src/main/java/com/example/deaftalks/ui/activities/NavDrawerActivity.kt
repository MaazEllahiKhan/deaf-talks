package com.example.deaftalks.ui.activities

import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.deaftalks.R
import com.example.deaftalks.utlis.SharedPref
import com.google.android.material.navigation.NavigationView

class NavDrawerActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var userNameNaVHeader: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_drawer)


        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)

        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment_content_dashboard)
        val headerView = navView.getHeaderView(0)
        userNameNaVHeader= headerView.findViewById(R.id.userNameNaVHeader)

        if(SharedPref.getInstance(this).getUserName()!="admin") {
            val navMenu = navView.menu
            navMenu.findItem(R.id.nav_interceptor)?.isVisible = false
            val topLevelDestinations = setOf(R.id.nav_home, R.id.Profiles)
            appBarConfiguration = AppBarConfiguration.Builder(topLevelDestinations)
                .setOpenableLayout(drawerLayout)
                .build()
        }else {
            appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.nav_home, R.id.nav_interceptor, R.id.Profiles
                ), drawerLayout
            )
        }
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
         updateUI()



    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logoutItem -> {
             SharedPref.getInstance(this).setUserName("")
             SharedPref.getInstance(this).setIsLoggedIn(false)
                val intent = Intent(this,LandingActivity::class.java)
                startActivity(intent)
                finishAffinity()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateUI() {
        userNameNaVHeader.text = SharedPref.getInstance(this).getUserName()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.dashboard, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_dashboard)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}