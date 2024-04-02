package ru.marina_w.my_map.auth

sealed class ActionSMS {
    class SmsSuccessAction: ActionSMS()
    data class ErrorSMS(val message: String): ActionSMS()
}