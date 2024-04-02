package ru.marina_w.my_map.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.marina_w.my_map.room.FavoritePlaceEntity
import ru.marina_w.my_map.room.UserInfoEntity
import ru.marina_w.my_map.user_profile_view_model_state.UserProfileState

class UserProfileViewModel : ViewModel() {

    private val _flowUserState = MutableStateFlow<UserProfileState>(UserProfileState.Loading())
    val flowUserState = _flowUserState.asStateFlow()


    private val useCase = UserProfileUseCase()

    init {
        fetchProfile()
    }

    fun fetchProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            _flowUserState.emit(UserProfileState.Success(useCase.getUser()))
        }
    }

    fun deleteUserProfile(id: String, idPlace: String) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.deleteUser(id)
            useCase.deletePlace(idPlace)
        }
    }

    fun addUserProfile(entity: UserInfoEntity, entityPlaceEntity: FavoritePlaceEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.addUser(entity)
            useCase.addPlace(entityPlaceEntity)
        }
    }


}