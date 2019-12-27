package recorder.recorder.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "record")
class RecordPersistence(
        @ColumnInfo(name = "fullPath") val fullPath: String,
        @ColumnInfo(name = "filePath") val filePath: String,
        @ColumnInfo(name = "sent") val sent: Boolean
    ) {

  @PrimaryKey(autoGenerate = true)
  var rid: Int = 0

}