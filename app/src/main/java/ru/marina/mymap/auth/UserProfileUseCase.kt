package ru.marina.mymap.auth


import ru.marina.mymap.UserModel
import ru.marina.mymap.UserModelMapper
import ru.marina.mymap.room.FavoritePlaceEntity
import ru.marina.mymap.room.UserInfoEntity

class UserProfileUseCase {
    private val repository = UserRepository.getInstance()
    private val mapper = UserModelMapper()
    private val repositoryPlace = PlaceMapRepository.getInstance()
    //База данных

    suspend fun addUser(entity: UserInfoEntity) {
        repository.addUser(entity)
    }

    suspend fun deleteUser(id: String) {
        repository.deleteUser(id)
    }

    suspend fun getUser(): UserModel? {
        val userInfoEntity = repository.getUser() ?: return null
        return mapper.mapUserModel(userInfoEntity, repositoryPlace.allPlaceList().firstOrNull())
    }

    suspend fun addPlace(entity: FavoritePlaceEntity) {
        repositoryPlace.addPlace(entity)
    }

    suspend fun deletePlace(placeId: String) {
        repositoryPlace.deletePlace(placeId)
    }

    suspend fun getAllPlaces(): List<FavoritePlaceEntity> {
        return repositoryPlace.allPlaceList()

    }


}