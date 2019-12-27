package recorder.recorder.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import recorder.recorder.database.dao.RecordDao
import recorder.recorder.database.entities.RecordPersistence

@Database(entities = [(RecordPersistence::class)], version = 1, exportSchema = false)
abstract class RoomDB : RoomDatabase() {
  abstract fun recordDao(): RecordDao


  companion object {

    private var dbInstance: RoomDB? = null

    @Synchronized
    fun getInstance(context: Context): RoomDB {
      if (dbInstance == null) {
        dbInstance = Room
            .databaseBuilder(context, RoomDB::class.java, "rtiDB")
            .fallbackToDestructiveMigration()
            .build()
      }
      return dbInstance!!
    }

  }
}