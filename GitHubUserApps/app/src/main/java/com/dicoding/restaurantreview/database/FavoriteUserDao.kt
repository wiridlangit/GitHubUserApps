package com.dicoding.restaurantreview.database

//import androidx.room.Delete
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface FavoriteUserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: FavoriteUser)


    @Query("SELECT * FROM favorite_users")
    fun getAllFavoriteUsers(): LiveData<List<FavoriteUser>>

    @Update
    fun update(user: FavoriteUser)


    @Query("DELETE FROM favorite_users WHERE username = :username")
    fun removeFromFavorites(username: String)

    @Query("SELECT * FROM favorite_users  WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser?>

}
