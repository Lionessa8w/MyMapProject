package ru.marina.mymap.auth

interface SmsCallback {
    fun setResultSmsCode(state: AuthSmsResponseState)
}