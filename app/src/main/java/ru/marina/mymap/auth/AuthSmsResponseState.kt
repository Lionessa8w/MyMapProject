package ru.marina_w.my_map.auth

import ru.marina_w.my_map.UserModel

sealed class AuthSmsResponseState {
    data class Error(val message: String) : AuthSmsResponseState()

    class Success : AuthSmsResponseState()
}