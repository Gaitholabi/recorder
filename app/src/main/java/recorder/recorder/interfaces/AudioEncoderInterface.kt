package recorder.recorder.interfaces

/**
 * This interface defines the functionalities that any audio encoder has to include
 * The audio encoder concrete class encodes an audio file and returns the encoded files as a
 * ByteArray
 */

interface AudioEncoderInterface {

  fun encode(
          buffer_left: ShortArray,
          buffer_right: ShortArray,
          samples: Int,
          buffer: ByteArray
    ): Int

  fun flush(buffer: ByteArray): Int

  fun close()

}
