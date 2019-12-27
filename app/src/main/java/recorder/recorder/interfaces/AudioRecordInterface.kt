package recorder.recorder.interfaces

/**
 * This interface defines the functionalities that any record manager needs to include
 * The audio record manager concrete class is used to record data from the device mic
 */

interface AudioRecordInterface {

  fun startRecording()

  fun stopRecording()

  fun recordLooper()

  fun isRecording() : Boolean
}