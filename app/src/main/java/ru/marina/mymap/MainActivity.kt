package ru.marina.mymap

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.mapview.MapView
import ru.marina_w.my_map.MapFragment
import ru.marina_w.my_map.RegistrationFragment

class MainActivity : AppCompatActivity() {
// инициализировать firebase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            MapKitFactory.setApiKey("58777d91-ee4e-4d91-a65b-2fe73bdcaae5")
            MapKitFactory.initialize(this)

        }
        // навигация на фрагмент
 //       supportFragmentManager.beginTransaction().replace(R.id.container, MapFragment()).commit()
        supportFragmentManager.beginTransaction().replace(R.id.container, RegistrationFragment()).commit()

    }
    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }
}