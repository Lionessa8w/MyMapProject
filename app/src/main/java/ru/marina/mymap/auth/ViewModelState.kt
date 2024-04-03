package ru.marina.mymap.auth

sealed class ViewModelState {
    data class Error(val message: String) : ViewModelState()

    class Success : ViewModelState()
}