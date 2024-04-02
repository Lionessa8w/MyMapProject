package ru.marina_w.my_map.auth

sealed class ViewModelState {
    data class Error(val message: String) : ViewModelState()

    class Success : ViewModelState()
}