package com.example.notestesttask.data

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager constructor(context: Context) {
    private val empty = ""
    private val prefsStorageName = "SH_PREF_NOTES"
    private val userEmail = "USER_EMAIL"

    private var sharedPreferences: SharedPreferences
    private var editor: SharedPreferences.Editor

    init {
        sharedPreferences = context.getSharedPreferences(
            prefsStorageName,
            Context.MODE_PRIVATE
        ).apply {
            editor = this.edit()
        }
    }

    fun saveUserEmail(email: String?) {
        editor.putString(userEmail, email)
        editor.apply()
    }

    fun getUserEmail(): String = sharedPreferences.getString(userEmail, empty)!!

}