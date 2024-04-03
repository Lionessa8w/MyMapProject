package ru.marina.mymap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import ru.marina.mymap.auth.UserProfileViewModel

import ru.marina.mymap.user_profile_view_model_state.UserProfileState

class UserProfileFragment : Fragment() {

    private val userProfileViewModel = UserProfileViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_account_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val iconProfile: ImageView = view.findViewById(R.id.user_avatar)
        val nameUser: TextView = view.findViewById(R.id.account_name)
        val numberPhoneUser: TextView = view.findViewById(R.id.number_phone)
        val statusSong: TextView = view.findViewById(R.id.song_status)
        val buttonMapLike: Button = view.findViewById(R.id.button_map_like)

        buttonMapLike.setOnClickListener {
            //открытие карты
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.container,
                    MapFragment()
                )
                .addToBackStack(null)
                .commit()
        }
        lifecycleScope.launch {
            userProfileViewModel.flowUserState.collect { state ->
                when (state) {
                    is UserProfileState.Error -> Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                    is UserProfileState.Loading -> Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                    is UserProfileState.Success -> {
                        nameUser.text = state.userModel.userName
                        numberPhoneUser.text = state.userModel.numberPhone
                        Glide
                            .with(requireContext())
                            .load(state.userModel.userImageUrl)
                            .centerCrop()
                            .into(iconProfile)
                    }

                }

            }
        }



    }
}