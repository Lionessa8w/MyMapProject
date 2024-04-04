package ru.marina.mymap

import android.os.Build.VERSION_CODES.S
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import java.lang.IllegalArgumentException
import kotlinx.coroutines.launch
import ru.marina.mymap.auth.ActionSMS
import ru.marina.mymap.auth.SmsAuthorizationViewModel

const val SMS_CODE = 5
private const val NUMBER_PHONE = "number_phone"

class AuthorizationFragment : Fragment() {

    private lateinit var smsViewModel: SmsAuthorizationViewModel
    private lateinit var phoneNumber: String

    override fun onCreate(savedInstanceState: Bundle?) {


        // выбрасывает исключение
        phoneNumber = arguments?.getString(NUMBER_PHONE) ?: throw IllegalArgumentException("не передан номер телефона")
        smsViewModel = ViewModelProvider(
            this,
            SmsAuthorizationViewModelFactory(phoneNumber)
        )[SmsAuthorizationViewModel::class.java]

        super.onCreate(savedInstanceState)
    }

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
            Log.d("checkResult", "smsButton.setOnClickListener: is Work")
            sendSmsCode(smsEditText.text.toString())

        }
        lifecycleScope.launch {
            Log.d("checkResult", "lifecycleScope.launch: is Work")

            smsViewModel.actionFlowSms.collect { action ->
                Log.d("checkResult", "smsViewModel.actionFlowSms.collect: is Work")

                when (action) {
                    is ActionSMS.ErrorSMS -> {
                        Log.d("checkResult", "onViewCreated: ${action.message}")
                    }

                    is ActionSMS.SmsSuccessAction -> {
                        requireActivity()
                            .supportFragmentManager
                            .beginTransaction()
                            .replace(
                                R.id.container,
                                UserProfileFragment.newInstance(phoneNumber)
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