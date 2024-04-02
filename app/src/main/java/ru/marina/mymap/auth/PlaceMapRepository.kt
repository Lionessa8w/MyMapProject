package ru.marina_w.my_map.auth


import ru.marina_w.my_map.room.BdUserHolder
import ru.marina_w.my_map.room.FavoritePlaceEntity

class PlaceMapRepository {

    private val placeRepository=BdUserHolder.getInstance().getDatabase().placeDao()


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