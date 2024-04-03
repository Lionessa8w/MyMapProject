package ru.marina.mymap.auth

sealed class UserPhoneViewModelState {

    data class Error(val message: String) : UserPhoneViewModelState()

    class Success : UserPhoneViewModelState()
}