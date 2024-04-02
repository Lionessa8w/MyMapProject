package ru.marina_w.my_map.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

// Ввод номера телефона
private const val TAG = "RegistrationViewModel"

class RegistrationPhoneViewModel : ViewModel() {

    private val useCase = UserUseCase.getInstance()
    private val _actionFlow = MutableSharedFlow<ActionNumberPhone>()
    val actionFlow = _actionFlow.asSharedFlow()
    private val numberPhoneCallback = object : NumberPhoneCallback {
        override fun setResultNumberPhoneResponseState(state: AuthNumberPhoneResponseState) {
            viewModelScope.launch {
                when (state) {
                    is AuthNumberPhoneResponseState.Error -> {
                        _actionFlow.emit(ActionNumberPhone.ErrorNumberPhone(state.message))
                    }

                    is AuthNumberPhoneResponseState.Success -> {
                        _actionFlow.emit(ActionNumberPhone.NumberPhoneSuccessAction())
                    }
                }
            }
        }

    }

    private var currentPhoneNumber: String? = null

    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { coroutineContext, throwable ->
            viewModelScope.launch {
                _actionFlow.emit(
                    ActionNumberPhone.ErrorNumberPhone(
                        throwable.localizedMessage ?: throwable.message ?: ""
                    )
                )
            }
        }

    fun installCallbackNumberPhone(numberPhone: String) {
        return useCase.installCallbackNumberPhone(numberPhone)
    }


    init {
        useCase.addNumberPhoneCallback(numberPhoneCallback)
    }

    fun getUserNumberPhone(): String {
        return currentPhoneNumber ?: ""
    }

    fun setCurrentNumber(number: String) {
        currentPhoneNumber = number
    }

}