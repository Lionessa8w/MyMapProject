package ru.marina.mymap.user_profile_view_model_state

import ru.marina.mymap.UserModel

sealed class UserProfileState {
    data class Error(val message: String) : UserProfileState()
    data class Success(val userModel: UserModel) : UserProfileState()
    class Loading : UserProfileState()
}