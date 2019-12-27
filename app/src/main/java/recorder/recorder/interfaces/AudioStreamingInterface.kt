package recorder.recorder.interfaces

/**
 * This interface defines the functionalities that any streaming manager needs to include
 * The streaming manager concrete class is used to send data in realtime from the app to
 * The receiving backend
 */

interface AudioStreamingInterface {

  fun connect()

  fun connected(): Boolean

  fun disconnect()

  fun send(data: Any)
}
