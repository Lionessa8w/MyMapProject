package ru.marina_w.my_map.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

//Ввод смс
class SmsAuthorizationViewModel : ViewModel() {

    private var smsCode: String? = null
    private val useCase = UserUseCase.getInstance()
    private var _actionFlowSms = MutableSharedFlow<ActionSMS>()
    val actionFlowSms = _actionFlowSms.asSharedFlow()

    private val smsCallback = object : SmsCallback {
        override fun setResultSmsCode(state: AuthSmsResponseState) {
            viewModelScope.launch {
                when(state){
                    is AuthSmsResponseState.Error -> {
                        _actionFlowSms.emit(ActionSMS.ErrorSMS(state.message))
                    }
                    is AuthSmsResponseState.Success -> {
                        _actionFlowSms.emit(ActionSMS.SmsSuccessAction())
                    }

                }
            }
        }


    }

    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { coroutineContext, throwable ->
        viewModelScope.launch {
            _actionFlowSms.emit(
                ActionSMS.ErrorSMS(throwable.localizedMessage ?: throwable.message ?: "")
            )
        }
    }
    init {
        useCase.addSmsCallback(smsCallback)
    }
    fun installSmsCallback(smsCode: String){
        return useCase.sendCode(smsCode)
    }
    fun getSmsCode(): String{
        return smsCode ?: ""
    }
    fun setCurrentSms( code: String){
        smsCode=code
    }


}