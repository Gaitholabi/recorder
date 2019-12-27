package recorder.recorder.interfaces

/**
 * This interface defines the functionalities that any file manager has to implement
 * The file manager concrete class is used to wrtie the encoded file on both
 * The disk and the memory, or each on its own
 */
interface AudioFileInterface {

  fun fileName(): String

  fun fullPath(): String

  fun writeToMemory(buffer: ByteArray, bufferLength: Int, offset: Int = 0)

  fun writeToDisk(buffer: ByteArray, bufferLength: Int, offset: Int = 0)

  fun writeAcrossMemoryAndDisk(buffer: ByteArray, bufferLength: Int, offset: Int = 0)

  fun getMemoryFileBytes(): ByteArray

  fun getDiskFileBytes(): ByteArray

  fun close()

  fun flushMemory()

}
