package ru.marina_w.my_map

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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.marina.mymap.R
import ru.marina_w.my_map.auth.ActionNumberPhone
import ru.marina_w.my_map.auth.RegistrationPhoneViewModel

const val NUMBER_LENGTH = 10

class RegistrationFragment : Fragment() {

    private val viewModelPhoneNumber = RegistrationPhoneViewModel()
    private var timerJob: Job? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    private fun sendNumberPhone(number: String) {
        return viewModelPhoneNumber.installCallbackNumberPhone(number)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val numberPhone: EditText = view.findViewById(R.id.phone_number)
        val buttonSMS: Button = view.findViewById(R.id.button_sms)
        numberPhone.setText(viewModelPhoneNumber.getUserNumberPhone())

        numberPhone.doOnTextChanged { text, start, before, count ->
            buttonSMS.isEnabled = (text?.length ?: 0) > NUMBER_LENGTH
            buttonSMS.isClickable = (text?.length ?: 0) > NUMBER_LENGTH
            viewModelPhoneNumber.setCurrentNumber(text.toString())

        }


        buttonSMS.setOnClickListener {
            buttonSMS.isEnabled = false
            buttonSMS.isClickable = false
            timerJob = lifecycleScope.launch(Dispatchers.IO) {
                delay(5000)
                withContext(Dispatchers.Main) {
                    buttonSMS.isEnabled = true
                    buttonSMS.isClickable = true
                }
            }
            Log.d("checkResult", "onCreateView: is Work")
            sendNumberPhone(numberPhone.text.toString())
        }
        lifecycleScope.launch {
            viewModelPhoneNumber.actionFlow.collect { action ->
                when (action) {
                    is ActionNumberPhone.ErrorNumberPhone ->
//                        Toast.makeText(
//                        requireContext(),
//                        action.message,
//                        Toast.LENGTH_SHORT
//                    ).show()
                        Log.d("checkResult", "onViewCreated: ${action.message}")

                    is ActionNumberPhone.NumberPhoneSuccessAction -> {
                        requireActivity()
                            .supportFragmentManager
                            .beginTransaction()
                            .replace(
                                R.id.container,
                                AuthorizationFragment()
                            )
                            .addToBackStack(null)
                            .commit()
                    }
                }
            }
        }

    }

    override fun onDestroy() {
        timerJob = null
        super.onDestroy()
    }
}