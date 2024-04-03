package ru.marina.mymap.auth



class UserUseCase private constructor() {

    private val repository = UserRepository.getInstance()
    private var smsCallback: SmsCallback? = null
    private var numberPhoneCallback: NumberPhoneCallback? = null

    fun sendCode(code: String) {
        return repository.sentSmsCode(code, smsCallback)
    }

    fun fallNumberPhone(): AuthNumberPhoneResponseState.Error {
        return AuthNumberPhoneResponseState.Error("")
    }

    fun addNumberPhoneCallback(callback: NumberPhoneCallback){
        numberPhoneCallback=callback
    }

    fun installCallbackNumberPhone(numberPhone: String) {
        val callback = numberPhoneCallback ?: return
        return repository.sendFonNumber(numberPhone, callback)
    }

    fun deleteCallbackNumberPhone() {
        numberPhoneCallback = null

    }
    suspend fun getNumberPhone(): String{
        return repository.getUser()?.numberPhone.toString()
    }

    fun addSmsCallback(callbackSMS: SmsCallback) {
        smsCallback = callbackSMS
    }

    fun deleteCallbackSms() {
        smsCallback = null
    }



    companion object {
        private var INSTANCE: UserUseCase? = null

        fun getInstance(): UserUseCase {
            return synchronized(this) {
                val currentInstance = INSTANCE ?: UserUseCase()
                INSTANCE = currentInstance
                currentInstance
            }
        }
    }
}