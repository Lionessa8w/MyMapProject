package ru.marina_w.my_map.auth

interface SmsCallback {
    fun setResultSmsCode(state: AuthSmsResponseState)
}