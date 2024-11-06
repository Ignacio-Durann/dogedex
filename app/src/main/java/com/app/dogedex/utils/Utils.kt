package com.app.dogedex.utils

import android.util.Patterns

class Utils {
    companion object{
        final fun isValidEmail(email: String?): Boolean{
            return !email.isNullOrEmpty() &&
                    Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }
}

