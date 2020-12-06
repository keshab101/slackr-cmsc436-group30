package com.example.slackr

class Validators {

    fun validUserName(name: String?) : Boolean {
        if (name.isNullOrEmpty()) {
            return false
        }
        val userRegex = Regex("^.{3,30}\$")
        return userRegex.matches(name)
    }

    fun validEmail(email: String?) : Boolean {
        if (email.isNullOrEmpty()) {
            return false
        }

        // General Email Regex (RFC 5322 Official Standard)
        val emailRegex = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.+-]+\\.edu\$")
        return emailRegex.matches(email)
    }

    // TODO: Validate password
    // Passwords should be at least 4 characters with 1 letter and 1 number
    fun validPassword(password: String?) : Boolean {

        if (password.isNullOrEmpty()) {
            return false
        }
        // between 4 - 8 characters
        val passwordRegex = Regex("^.{4,8}\$")
        return passwordRegex.matches(password)
    }

    fun validText(text: String?): Boolean {
        return !text.isNullOrEmpty()
    }
}