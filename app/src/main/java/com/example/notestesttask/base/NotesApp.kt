package com.example.notestesttask.base

import android.app.Application
import com.example.notestesttask.data.SharedPreferencesManager

class NotesApp : Application() {

    companion object {
        lateinit var sharedPreferencesManager: SharedPreferencesManager
    }

    override fun onCreate() {
        super.onCreate()
        sharedPreferencesManager = SharedPreferencesManager(this)
    }
}