package ru.marina.mymap.auth

import ru.marina.mymap.room.BdUserHolder
import ru.marina.mymap.room.FavoritePlaceEntity

class PlaceMapRepository {

    private val placeRepository= BdUserHolder.getInstance().getDatabase().placeDao()


    // DataBase
    suspend fun allPlaceList(): List<FavoritePlaceEntity>{
        return placeRepository.getAllPlace()
    }
    suspend fun deletePlace(placeId: String){
        return placeRepository.deletedPlace(placeId)
    }
    suspend fun addPlace(entity: FavoritePlaceEntity ){
        return placeRepository.addNewPlaceMap(entity)
    }

    companion object {
        private var INSTANCE: PlaceMapRepository? = null

        fun getInstance(): PlaceMapRepository {
            return synchronized(this) {
                val currentInstance = INSTANCE ?: PlaceMapRepository()
                INSTANCE = currentInstance
                currentInstance
            }
        }
    }
}