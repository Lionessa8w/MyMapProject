package ru.marina.mymap.auth

sealed class ActionNumberPhone {
    class NumberPhoneSuccessAction: ActionNumberPhone()
    data class ErrorNumberPhone(val message: String): ActionNumberPhone()

}