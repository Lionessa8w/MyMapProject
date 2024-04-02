package ru.marina_w.my_map.auth

sealed class AuthNumberPhoneResponseState {
    data class Error(val message: String) : AuthNumberPhoneResponseState()

    class Success : AuthNumberPhoneResponseState()
}