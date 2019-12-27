package recorder.recorder.managers

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import recorder.recorder.managers.SharedPreferencesManager.Companion.getAuthToken

class AudioPlayerManager(private val context: Context) {


  val mediaPlayer: MediaPlayer
  val headers: MutableMap<String, String> = mutableMapOf()

  /**
   * Init the media player, and set the authentication token
   */
  init {
    mediaPlayer = MediaPlayer()
    headers["Authorization"] = getAuthToken(context)
  }

  /**
   * This method starts the audio playback process
   */
  fun play(uri: Uri, callback: () -> Unit) {
    mediaPlayer.reset()
    mediaPlayer.setDataSource(context, uri, headers)
    mediaPlayer.prepare()
    mediaPlayer.setOnCompletionListener { callback() }
    mediaPlayer.start()

  }

  /**
   * This method stops the record playback
   */
  fun stop() {
    mediaPlayer.reset()
  }


}