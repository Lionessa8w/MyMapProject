package ru.marina.mymap.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_info_entity")
data class UserInfoEntity(

    @PrimaryKey() val numberPhone: String,
    @ColumnInfo(name = "user_name") val userName: String,
    @ColumnInfo(name = "uri_image_avatar") val uriImageAvatar: String,
    @ColumnInfo(name = "status_sound") val statusSound: String
)

@Entity(tableName = "favorite_places")
data class FavoritePlaceEntity(
    @PrimaryKey val placeId: String,
    @ColumnInfo(name = "user_id") val userId: String,
    @ColumnInfo(name = "place_name") val placeName: String,
    @ColumnInfo(name = "geo_position") val geoPosition: String
)
