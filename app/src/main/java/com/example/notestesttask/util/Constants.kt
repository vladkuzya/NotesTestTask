package com.example.notestesttask.util

class Constants {
    companion object {

        const val TEST_IMAGE_URL = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/97/Circle-icons-art.svg/768px-Circle-icons-art.svg.png?20160314153306"
        const val ERROR_INVALID_EMAIL = "The email address is badly formatted."
        const val ERROR_SHORT_PASSWORD = "The given password is invalid. [ Password should be at least 6 characters ]"
        const val ERROR_INVALID_USER = "There is no user record corresponding to this identifier. The user may have been deleted."
        const val ERROR_WRONG_PASSWORD = "The password is invalid or the user does not have a password."
        const val ERROR_INVALID_CONFIRM_PASSWORD = "ERROR_INVALID_CONFIRM_PASSWORD"
        const val ERROR_EMAIL_IS_ALREADY_IN_USE = "The email address is already in use by another account."

        const val COLLECTION_NAME = "Notes"
        const val SUB_COLLECTION_NAME = "notes"
        const val DATE_FORMAT = "d MMM HH:mm"

        const val ACTIVE_FIELD = "active"
    }
}