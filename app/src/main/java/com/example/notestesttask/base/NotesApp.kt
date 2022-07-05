package com.example.notestesttask.base

import android.app.Application
import com.example.notestesttask.data.SharedPreferencesManager

class NotesApp : Application() {

    companion object {
        lateinit var sharedPreferencesManager: SharedPreferencesManager

        fun getSharedPreferences(): SharedPreferencesManager = sharedPreferencesManager
    }

    override fun onCreate() {
        super.onCreate()
        sharedPreferencesManager = SharedPreferencesManager(this)
    }
}