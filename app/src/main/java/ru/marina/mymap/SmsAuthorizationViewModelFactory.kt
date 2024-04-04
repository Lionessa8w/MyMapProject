package ru.marina.mymap

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.marina.mymap.auth.SmsAuthorizationViewModel

@Suppress("UNCHECKED_CAST")
class SmsAuthorizationViewModelFactory(private val phoneNumber: String): ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SmsAuthorizationViewModel(phoneNumber) as T
    }
}