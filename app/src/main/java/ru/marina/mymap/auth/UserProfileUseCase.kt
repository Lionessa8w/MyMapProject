package ru.marina_w.my_map.auth

import ru.marina_w.my_map.UserModel
import ru.marina_w.my_map.UserModelMapper
import ru.marina_w.my_map.room.FavoritePlaceEntity
import ru.marina_w.my_map.room.UserInfoEntity

class UserProfileUseCase {
    private val repository = UserRepository.getInstance()
    private val mapper= UserModelMapper()
    private val repositoryPlace= PlaceMapRepository.getInstance()
    //База данных

    suspend fun addUser(entity: UserInfoEntity){
        repository.addUserId(entity)
    }
    suspend fun deleteUser(id: String){
        repository.deleteUser(id)
    }
    suspend fun getUser(): UserModel{
        return mapper.mapUserModel(repository.getUser(), repositoryPlace.allPlaceList().first())
    }

    suspend fun addPlace(entity: FavoritePlaceEntity){
        repositoryPlace.addPlace(entity)
    }
    suspend fun deletePlace(placeId: String){
        repositoryPlace.deletePlace(placeId)
    }
    suspend fun getAllPlaces():List<FavoritePlaceEntity>{
        return repositoryPlace.allPlaceList()

    }



}