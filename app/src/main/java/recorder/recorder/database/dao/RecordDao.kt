package recorder.recorder.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import recorder.recorder.database.entities.RecordPersistence


@Dao
interface RecordDao {
  @get:Query("SELECT * FROM record")
  val all: List<RecordPersistence>

  @Insert
  fun insert(record: RecordPersistence)
}