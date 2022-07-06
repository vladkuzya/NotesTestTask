package com.example.notestesttask

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.container, LoginFragment.newInstance())
//                .commitNow()
//        }
        val navController = findNavController(this, R.id.auth_nav_host_fragment)
        if (null == savedInstanceState) {
            navController.setGraph(R.navigation.main_navigation)
        }
    }
}