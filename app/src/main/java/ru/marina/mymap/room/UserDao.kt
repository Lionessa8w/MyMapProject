package ru.marina.mymap.room

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RoomDatabase

@Dao
interface UserDao {

    @Query("SELECT  * FROM user_info_entity")
    fun getUser(): UserInfoEntity

    // удаление одного юзера из одного
    //убрать йди
    @Query("DELETE FROM user_info_entity WHERE id = :id")
    fun deletedIdUser(id: String)

    @Insert(entity = UserInfoEntity::class)
    fun addNewUser(entity: UserInfoEntity)


}

@Database(
    version = 2,
    entities = [UserInfoEntity::class, FavoritePlaceEntity::class]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userListDao(): UserDao
    abstract fun placeDao(): FavoritePlaceDao

}