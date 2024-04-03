package ru.marina.mymap.auth

sealed class AuthNumberPhoneResponseState {
    data class Error(val message: String) : AuthNumberPhoneResponseState()

    class Success : AuthNumberPhoneResponseState()
}