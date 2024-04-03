package ru.marina.mymap

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.marina.mymap.auth.ActionSMS
import ru.marina.mymap.auth.SmsAuthorizationViewModel

const val SMS_CODE = 5

class AuthorizationFragment : Fragment() {

    private val smsViewModel= SmsAuthorizationViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_authorization, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val smsEditText: EditText = view.findViewById(R.id.sms_numbers_code)
        val smsButton: Button = view.findViewById(R.id.button_sms_code)
        smsEditText.setText(smsViewModel.getSmsCode())

        smsEditText.doOnTextChanged { text, start, before, count ->
            smsButton.isEnabled = (text?.length ?: 0) > SMS_CODE
            smsButton.isClickable = (text?.length ?: 0) > SMS_CODE
            smsViewModel.setCurrentSms(text.toString())

        }

        smsButton.setOnClickListener {
            Log.d("checkResult", "onCreateView: is Work")
            sendSmsCode(smsEditText.text.toString())

        }
        lifecycleScope.launch {
            smsViewModel.actionFlowSms.collect{action->
                when(action){
                    is ActionSMS.ErrorSMS -> {
                        Log.d("checkResult", "onViewCreated: ${action.message}")
                    }

                    is ActionSMS.SmsSuccessAction -> {
                        requireActivity()
                            .supportFragmentManager
                            .beginTransaction()
                            .replace(
                                R.id.container,
                                UserProfileFragment()
                            )
                            .addToBackStack(null)
                            .commit()
                       Log.d("checkResult", "onViewCreated: ActionSMS.SmsSuccessAction")

                    }
                }
            }
        }
    }
    private fun sendSmsCode(code: String) {
        return smsViewModel.installSmsCallback(code)
    }

}