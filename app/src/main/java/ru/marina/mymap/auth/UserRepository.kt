package ru.marina_w.my_map.auth

import android.app.Activity
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.auth
import java.util.concurrent.TimeUnit
import ru.marina_w.my_map.room.BdUserHolder
import ru.marina_w.my_map.room.UserInfoEntity


private const val TAG = "UserRepository"

class UserRepository private constructor() {
    private var activity: Activity? = null
    private val firebaseAuth = Firebase.auth
    private var storedVerificationId: String? = ""
    private val userListDao = BdUserHolder.getInstance().getDatabase().userListDao()


    fun bind(activity: Activity) {
        this.activity = activity
    }

    fun realise() {
        activity = null
    }

    fun sendFonNumber(numberPhone: String, callback: NumberPhoneCallback) {
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(numberPhone) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                // номер телефона пользователя успешно проверен
                override fun onVerificationCompleted(credential: PhoneAuthCredential) = Unit

                //Этот метод вызывается в ответ на недействительный запрос проверки,
                //например запрос, в котором указан неверный номер телефона или код проверки.

                override fun onVerificationFailed(e: FirebaseException) {
                    callback.setResultNumberPhoneResponseState(
                        AuthNumberPhoneResponseState.Error(
                            e.message ?: ""
                        )
                    )
                    Log.d("checkResult", "onVerificationFailed: $e")
                }
                //Этот метод вызывается после отправки кода подтверждения по SMS на указанный номер телефона.
                //
                //При вызове этого метода большинство приложений отображают
                // пользовательский интерфейс,
                // предлагающий пользователю ввести код подтверждения из SMS-сообщения.
                // (В то же время автоматическая проверка может выполняться в фоновом режиме.)

                override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(verificationId, token)
                    callback.setResultNumberPhoneResponseState(AuthNumberPhoneResponseState.Success())
                    Log.d("checkResult", "onCodeSent:$verificationId")
                    storedVerificationId = verificationId


                }

            })
            .build()
        //проверкa номера телефона пользователя
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    //войти в систему с учетными данными для аутентификации телефона
    fun sentSmsCode(code: String, callback: SmsCallback?) {
        val credential = PhoneAuthProvider.getCredential(storedVerificationId!!, code)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(activity!!) { task ->
                if (task.isSuccessful) {
                    callback?.setResultSmsCode(AuthSmsResponseState.Success())


                } else {
                    callback?.setResultSmsCode(AuthSmsResponseState.Error(task.exception?.message ?: ""))
                }
            }
    }

    //База данных
    suspend fun addUserId(entity: UserInfoEntity) {
        userListDao.addNewUser(entity)
    }

    suspend fun deleteUser(id: String) {
        userListDao.deletedIdUser(id)
    }

    suspend fun getUser(): UserInfoEntity {
        return userListDao.getUser()
    }


    companion object {
        private var INSTANCE: UserRepository? = null

        fun getInstance(): UserRepository {
            return synchronized(this) {
                val currentInstance = INSTANCE ?: UserRepository()
                INSTANCE = currentInstance
                currentInstance
            }
        }
    }
}