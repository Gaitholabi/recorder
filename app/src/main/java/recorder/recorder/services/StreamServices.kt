package recorder.recorder.services

import android.content.Context
import recorder.recorder.interfaces.AudioEncoderInterface
import recorder.recorder.interfaces.AudioFileInterface
import recorder.recorder.interfaces.AudioStreamingInterface
import recorder.recorder.managers.AudioFileManager
import recorder.recorder.managers.AudioRecordManager
import recorder.recorder.managers.WebSocketAudioStreamingManager
import recorder.recorder.models.Mp3EncoderBuilder
import recorder.recorder.models.Record

/**
 * This class defines the recording service and integrates encoding, recording, file managament,
 * and streaming capabilities
 * @property serverURL String
 * @property service String
 * @property accessToken String
 * @property record RecordPersistence
 * @property encoder AudioEncoderInterface
 * @property fileManager AudioFileInterface
 * @property audioStreamer AudioStreamingInterface
 * @property audioRecorder AudioRecordManager
 * @constructor
 */
class StreamServices(
        private val socketURL: String,
        private val service: String,
        private val context: Context,
        private val accessToken: String
) {

  private val record: Record
  private val encoder: AudioEncoderInterface
  private val fileManager: AudioFileInterface
  private val audioStreamer: AudioStreamingInterface
  private val audioRecorder: AudioRecordManager

  /**
   * initiate the required objects and inject dependencies
   */
  init {
    record = Record()
    encoder = Mp3EncoderBuilder()
        .setInSampleRate(record.sampleRate)
        .setOutChannels(record.audioChannel)
        .setOutBitrate(record.bitRate)
        .setOutSampleRate(record.sampleRate)
        .build()

    fileManager = AudioFileManager().prepare()
    audioStreamer = WebSocketAudioStreamingManager(socketURL, service)
    audioRecorder = AudioRecordManager(
        record,
        encoder,
        fileManager,
        audioStreamer,
        context,
        accessToken
    )
  }

  /**
   * This method returns the state of the recording process
   */
  fun isRecording(): Boolean {
    return audioRecorder.isRecording()
  }

  /**
   * This method starts the recording ( whole functionality, record, save, and stream)
   */
  fun record() {
    audioRecorder.startRecording()
  }

  /**
   * this method stops the recording ( whole functionality, record, save, and stream)
   */
  fun stopRecord() {
    audioRecorder.stopRecording()
  }

}
