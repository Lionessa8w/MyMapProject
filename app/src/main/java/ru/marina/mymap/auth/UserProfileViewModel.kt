package ru.marina.mymap.auth
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.marina.mymap.room.FavoritePlaceEntity
import ru.marina.mymap.room.UserInfoEntity
import ru.marina.mymap.user_profile_view_model_state.UserProfileState

class UserProfileViewModel : ViewModel() {

    private val _flowUserState = MutableStateFlow<UserProfileState>(UserProfileState.Loading())
    val flowUserState = _flowUserState.asStateFlow()


    private val useCase = UserProfileUseCase()

    init {
        fetchProfile()
    }

    private fun fetchProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            val userModel = useCase.getUser()
            if (userModel != null) {
                _flowUserState.emit(UserProfileState.Success(userModel))
            } else {
                _flowUserState.emit(UserProfileState.Error("Error"))
            }
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