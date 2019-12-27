package recorder.recorder.managers


import android.os.Environment
import android.util.Log
import recorder.recorder.interfaces.AudioFileInterface
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

/**
 * This class manages the file read and write from disk and memory
 * @property fileName String
 * @property directory String
 * @property diskFileStream FileOutputStream
 * @property memoryFileStream ByteArrayOutputStream
 * @constructor
 */

class AudioFileManager(
    private val fileName: String = System.currentTimeMillis().toString() + ".mp3")
  : AudioFileInterface {

  private val directory: String = "${Environment.getExternalStorageDirectory()}/rti"
  private var diskFileStream: FileOutputStream? = null
  private var memoryFileStream: ByteArrayOutputStream? = null


  /**
   * This method creates the application directory if it is not available, and
   * Prepares the memory and disk streams
   */
  fun prepare(): AudioFileManager {
    val dir = File(directory)
    if (!dir.exists()) {
      dir.mkdir()
    }
    diskFileStream = FileOutputStream(File("$directory/$fileName"))
    memoryFileStream = ByteArrayOutputStream()
    return this
  }

  override fun fullPath(): String {
    return "${Environment.getExternalStorageDirectory()}/rti/$fileName"
  }


  /**
   * This method gets the filePath of the written file
   * @return String
   */
  override fun fileName(): String {
    return this.fileName
  }

  /**
   * This method writes a buffer into a file in the device's memory
   * @param buffer ByteArray
   * @param bufferLength Int
   * @param offset Int
   */
  override fun writeToMemory(buffer: ByteArray, bufferLength: Int, offset: Int) {
    memoryFileStream?.write(buffer, offset, bufferLength)
  }

  /**
   * This method writes a buffer into a file on the device's disk
   * @param buffer ByteArray
   * @param bufferLength Int
   * @param offset Int
   */
  override fun writeToDisk(buffer: ByteArray, bufferLength: Int, offset: Int) {
    diskFileStream?.write(buffer, offset, bufferLength)
  }

  /**
   * This method writes a buffer into a file in both the device's memory and disk
   * @param buffer ByteArray
   * @param bufferLength Int
   * @param offset Int
   */
  override fun writeAcrossMemoryAndDisk(buffer: ByteArray, bufferLength: Int, offset: Int) {
    Log.d("writing", "writing to disk")
    writeToDisk(buffer, bufferLength, offset)
    writeToMemory(buffer, bufferLength, offset)
  }

  /**
   * This method gets the on memory file's content as a ByteArray
   * @return ByteArray
   */
  override fun getMemoryFileBytes(): ByteArray {
    return memoryFileStream?.toByteArray()!!
  }

  /**
   * This method gets the on disk file's content as a ByteArray
   * @return ByteArray
   */

  override fun getDiskFileBytes(): ByteArray {
    return File("$directory/$fileName").readBytes()
  }

  /**
   * This method flushes the file on memory
   */
  override fun flushMemory() {
    memoryFileStream?.reset()
    memoryFileStream?.flush()
  }

  /**
   * This method closes the read/write stream of both the disk file and memory file
   */

  override fun close() {
    diskFileStream?.close()
    memoryFileStream?.close()
  }

}
