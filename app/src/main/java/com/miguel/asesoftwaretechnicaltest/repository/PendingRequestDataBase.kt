package com.miguel.asesoftwaretechnicaltest.repository

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase


@Entity(tableName = "pending_requests")
data class PendingRequest(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val photoId: Int
)

@Dao
interface PendingRequestDao {
    @Insert
    suspend fun insert(request: PendingRequest)

    @Query("SELECT * FROM pending_requests WHERE photoId = :photoId")
    suspend fun getPendingRequestByPhotoId(photoId: Int): PendingRequest?

    @Query("SELECT * FROM pending_requests")
    suspend fun getAllPendingRequests(): List<PendingRequest>

}

@Database(entities = [PendingRequest::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pendingRequestDao(): PendingRequestDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}