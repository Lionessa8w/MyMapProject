package ru.marina_w.my_map.user_profile_view_model_state

import ru.marina_w.my_map.UserModel

sealed class UserProfileState {
    data class Error(val message: String) : UserProfileState()
    data class Success(val userModel: UserModel) : UserProfileState()
    class Loading : UserProfileState()
}