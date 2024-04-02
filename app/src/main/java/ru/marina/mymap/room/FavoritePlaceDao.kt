package ru.marina_w.my_map.room

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RoomDatabase
@Dao
interface FavoritePlaceDao {
    @Query("SELECT * FROM favorite_places")
    fun getAllPlace(): List<FavoritePlaceEntity>

    @Query("DELETE FROM favorite_places WHERE placeId = :placeId")
    fun deletedPlace(placeId: String)

    @Insert(entity = FavoritePlaceEntity::class)
    fun addNewPlaceMap(entity: FavoritePlaceEntity)



}

