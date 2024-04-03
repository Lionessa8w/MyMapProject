package ru.marina.mymap

import ru.marina.mymap.room.FavoritePlaceEntity
import ru.marina.mymap.room.UserInfoEntity

class UserModelMapper {
    fun mapUserModel(entity: UserInfoEntity, entityPlace: FavoritePlaceEntity?): UserModel {
        return UserModel(userName = entity.userName,
            numberPhone = entity.numberPhone,
            userImageUrl = entity.uriImageAvatar,
            userPublicStatus = entity.statusSound,
            userGeoPosition = entityPlace?.geoPosition)

    }
}