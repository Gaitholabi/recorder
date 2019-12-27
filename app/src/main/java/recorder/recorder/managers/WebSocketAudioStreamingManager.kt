package recorder.recorder.managers

import io.socket.client.IO
import io.socket.client.Socket
import recorder.recorder.interfaces.AudioStreamingInterface
import recorder.recorder.utilities.ENV.Companion.SOCKET_URL

class WebSocketAudioStreamingManager(private val serverURI: String,
                                     private val service: String) : AudioStreamingInterface {


  private val socketConnection: Socket = IO.socket(SOCKET_URL)


  /**
   * This method inits the connection with the WebSocket
   */
  override fun connect() {
    socketConnection.connect()
  }

  /**
   * This method checks if the client is connected with teh WebSocket Server
   * @return Boolean
   */
  override fun connected(): Boolean {
    return socketConnection.connected()
  }

  /**
   * This method disconnected the connection with the WebSocket Server
   */
  override fun disconnect() {
    socketConnection.disconnect()
  }

  /**
   * This method sends the data to the websocket server
   * @param data Any
   */
  override fun send(data: Any) {
    socketConnection.emit(this.service, data)
  }

}