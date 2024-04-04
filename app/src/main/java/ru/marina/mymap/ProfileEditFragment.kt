package ru.marina.mymap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.marina.mymap.auth.UserProfileViewModel

class ProfileEditFragment : Fragment(){

    private var userProfileViewModel= UserProfileViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        userProfileViewModel=ViewModelProvider(this)[UserProfileViewModel::class.java]
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nameEdit: EditText=view.findViewById(R.id.account_name_edit)
        val userAvatarEdit: EditText=view.findViewById(R.id.user_avatar_edit)
        val buttonSave: Button= view.findViewById(R.id.button_save)
        val buttonDeleteProfile: Button= view.findViewById(R.id.button_deleted)
        nameEdit.setText(userProfileViewModel.getCurrentUserName())
        nameEdit.doOnTextChanged { text, start, before, count ->
            userProfileViewModel.setCurrentUserName(text.toString())
        }


        buttonDeleteProfile.setOnClickListener {
            userProfileViewModel.deleteUserProfile()
        }
        buttonSave.setOnClickListener {

        }

    }
}