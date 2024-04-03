package ru.marina.mymap

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.yandex.mapkit.MapKitFactory
import ru.marina.mymap.auth.UserRepository
import ru.marina.mymap.room.BdUserHolder

class MainActivity : AppCompatActivity() {
// инициализировать firebase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            MapKitFactory.setApiKey("58777d91-ee4e-4d91-a65b-2fe73bdcaae5")
            MapKitFactory.initialize(this)
            BdUserHolder.getInstance().init(this)
            UserRepository.getInstance().bind(this)
        }
    Log.d("checkResult", "onCreate: firebase id ${Firebase.auth.currentUser?.uid}")
    Log.d("checkResult", "onCreate: сюда зашел ${MainActivity::class}")

    // навигация на фрагмент
//        supportFragmentManager.beginTransaction().replace(R.id.container, MapFragment()).commit()
        supportFragmentManager.beginTransaction().replace(R.id.container, RegistrationNumberPhoneFragment()).commit()
//        supportFragmentManager.beginTransaction().replace(R.id.container, UserProfileFragment()).commit()

    }
    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }
}