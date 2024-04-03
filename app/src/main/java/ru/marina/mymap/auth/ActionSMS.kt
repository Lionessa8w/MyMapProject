package ru.marina.mymap.auth

sealed class ActionSMS {
    class SmsSuccessAction: ActionSMS()
    data class ErrorSMS(val message: String): ActionSMS()
}