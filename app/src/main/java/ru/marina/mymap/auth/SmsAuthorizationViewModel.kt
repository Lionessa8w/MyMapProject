package ru.marina.mymap.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.marina.mymap.room.UserInfoEntity

//Ввод смс
class SmsAuthorizationViewModel : ViewModel() {

    private var smsCode: String? = null
    private val useCase = UserUseCase.getInstance()
    private val userProfileUseCase= UserProfileUseCase()
    private var _actionFlowSms = MutableSharedFlow<ActionSMS>()
    val actionFlowSms = _actionFlowSms.asSharedFlow()

    private val smsCallback = object : SmsCallback {
        override fun setResultSmsCode(state: AuthSmsResponseState) {
            viewModelScope.launch(Dispatchers.IO) {
                when (state) {
                    is AuthSmsResponseState.Error -> {
                        _actionFlowSms.emit(ActionSMS.ErrorSMS(state.message))
                    }

                    is AuthSmsResponseState.Success -> {
                        //// add user to bd через use case
                        userProfileUseCase.addUser(
                            UserInfoEntity(
                            id = Firebase.auth.currentUser?.uid.toString(),
                            numberPhone = useCase.getNumberPhone(),
                            userName = "AAA",
                            uriImageAvatar = "https://avatars.mds.yandex.net/get-kinopoisk-image/4483445/5d39486d-feda-49b8-a42d-b4b2d4234c27/3840x",
                            statusSound = "Good Sound"

                        )
                        )
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

    fun installSmsCallback(smsCode: String) {
        return useCase.sendCode(smsCode)
    }

    fun getSmsCode(): String {
        return smsCode ?: ""
    }

    fun setCurrentSms(code: String) {
        smsCode = code
    }


}