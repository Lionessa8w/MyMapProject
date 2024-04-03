package ru.marina.mymap.auth

sealed class AuthSmsResponseState {
    data class Error(val message: String) : AuthSmsResponseState()

    class Success : AuthSmsResponseState()
}