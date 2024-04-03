package ru.marina.mymap.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoritePlaceDao {
    @Query("SELECT * FROM favorite_places")
    fun getAllPlace(): List<FavoritePlaceEntity>

    @Query("DELETE FROM favorite_places WHERE placeId = :placeId")
    fun deletedPlace(placeId: String)

    @Insert(entity = FavoritePlaceEntity::class)
    fun addNewPlaceMap(entity: FavoritePlaceEntity)



}

