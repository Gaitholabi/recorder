package recorder.recorder.managers

import android.content.Context
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.util.Base64
import android.util.Log
import org.json.JSONObject
import recorder.recorder.database.RoomDB
import recorder.recorder.database.entities.RecordPersistence
import recorder.recorder.interfaces.AudioEncoderInterface
import recorder.recorder.interfaces.AudioFileInterface
import recorder.recorder.interfaces.AudioRecordInterface
import recorder.recorder.interfaces.AudioStreamingInterface
import recorder.recorder.models.Record
import java.util.concurrent.atomic.AtomicBoolean

class AudioRecordManager(
        record: Record,
        private val encoder: AudioEncoderInterface,
        private val fileManager: AudioFileInterface,
        private val streamingManager: AudioStreamingInterface,
        private val context: Context,
        private val accessToken: String,
        private val audioFormat: Int = AudioFormat.ENCODING_PCM_16BIT,
        private val audioSource: Int = MediaRecorder.AudioSource.MIC
        ) : Thread(), AudioRecordInterface {

    private val minimumBuffer: Int
    private val shortBuffer: ShortArray
    private val byteBuffer: ByteArray
    private val audioRecord: AudioRecord

    private val isRecording: AtomicBoolean

    init {

        minimumBuffer = AudioRecord.getMinBufferSize(
                record.sampleRate,
                record.audioChannel,
                audioFormat
        )

        audioRecord = AudioRecord(
                audioSource,
                record.sampleRate,
                record.audioChannel,
                audioFormat,
                minimumBuffer * record.bytesPerEncoding
        )

        shortBuffer = ShortArray(record.sampleRate * record.bytesPerEncoding * record.bufferMultiplier)
        byteBuffer = ByteArray((7200 + shortBuffer.size.toDouble() * record.bytesPerEncoding * 1.25).toInt())
        isRecording = AtomicBoolean(false)
    }

    /**
     * This method starts the recording functionality
     */
    override fun startRecording() {
        Log.d("recording", " Started recording")
        isRecording.set(true)
        this.start()
    }

    /**
     * This method halts the file recording
     */
    override fun stopRecording() {
        Log.d("recording", " stop recording")
        isRecording.set(false)
        audioRecord.stop()
        audioRecord.release()
        encoder.close()
    }

    override fun isRecording(): Boolean {
        return isRecording.get()
    }


    /**
     * This method asynchronously listens to the device's mic and uses the Encoder implementation
     * to encode the file, then uses the streaming implementation to stream the fiel to the backend
     */
    override fun recordLooper() {

        var bytesRead: Int
        while (isRecording()) {
            bytesRead = audioRecord.read(shortBuffer, 0, minimumBuffer)
            if (bytesRead > 0) {
                val bytesEncoded = encoder.encode(shortBuffer, shortBuffer, bytesRead, byteBuffer)
                if (bytesEncoded > 0) {

                    fileManager.writeAcrossMemoryAndDisk(byteBuffer, bytesEncoded)
                    if (streamingManager.connected()) {
                        val json = getJsonObject()
                        streamingManager.send(json)
                        fileManager.flushMemory()
                    }
                }
            }
        }
    }

    private fun getJsonObject(): JSONObject {
        val string = Base64.encodeToString(fileManager.getMemoryFileBytes(), Base64.DEFAULT)
        return JSONObject().run {
            put("accessToken", accessToken)
            put("filename", fileManager.fileName())
            put("file", string)
        }
    }


    /**
     * This method flushes teh remnants of any recording leftovers in the buffer and writes them to
     * disk and memory
     */
    private fun flush() {
        val outputMp3Buff = encoder.flush(byteBuffer)
        if (outputMp3Buff > 0) {
            fileManager.writeAcrossMemoryAndDisk(byteBuffer, outputMp3Buff)
        }
        if (streamingManager.connected()) {
            val json = getJsonObject()
            streamingManager.send(json)
            RoomDB.getInstance(context).recordDao().insert(RecordPersistence(fileManager.fullPath(), fileManager.fileName(), true))
        } else {
            RoomDB.getInstance(context).recordDao().insert(RecordPersistence(fileManager.fullPath(), fileManager.fileName(), false))
        }

        fileManager.flushMemory()
    }

    /**
     * This method runs the recording thread
     */
    override fun run() {

        streamingManager.connect()
        this.audioRecord.startRecording()
        recordLooper()
        flush()
        fileManager.close()
        isRecording.set(false)
    }
}