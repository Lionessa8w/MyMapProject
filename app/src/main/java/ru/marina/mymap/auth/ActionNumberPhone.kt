package ru.marina_w.my_map.auth

sealed class ActionNumberPhone {
    class NumberPhoneSuccessAction: ActionNumberPhone()
    data class ErrorNumberPhone(val message: String): ActionNumberPhone()

}