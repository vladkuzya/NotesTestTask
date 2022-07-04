package com.example.notestesttask.util

class Constants {
    companion object {
        const val ERROR_INVALID_EMAIL = "The email address is badly formatted."
        const val ERROR_SHORT_PASSWORD = "The given password is invalid. [ Password should be at least 6 characters ]"
        const val ERROR_INVALID_USER = "There is no user record corresponding to this identifier. The user may have been deleted."
        const val ERROR_WRONG_PASSWORD = "The password is invalid or the user does not have a password."
        const val ERROR_INVALID_CONFIRM_PASSWORD = "ERROR_INVALID_CONFIRM_PASSWORD"
        const val ERROR_EMAIL_IS_ALREADY_IN_USE = "The email address is already in use by another account."

    }
}