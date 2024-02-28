package com.miguel.asesoftwaretechnicaltest.repository

import android.content.Context
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Entity(tableName = "photos")
data class PhotoEntity(
    @PrimaryKey val id: Int,
    val albumId: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String
)

@Dao
interface PhotoDao {
    @Query("SELECT * FROM photos")
    suspend fun getPhotos(): List<PhotoEntity>

    @Query("SELECT * FROM photos WHERE id = :id")
    suspend fun getPhotoById(id: Int): PhotoEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhotos(photos: List<PhotoEntity>)

}


@Database(entities = [PhotoEntity::class], version = 1)
abstract class PhotoDatabase : RoomDatabase() {
    abstract fun photoDao(): PhotoDao

    companion object {
        @Volatile
        private var instance: PhotoDatabase? = null

        fun getInstance(context: Context): PhotoDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): PhotoDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                PhotoDatabase::class.java,
                "photo_database"
            ).build()
        }
    }
}

