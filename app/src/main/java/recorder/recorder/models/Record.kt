package recorder.recorder.models

import android.media.AudioFormat

/**
 * This class defines the main data attributes of a Record
 * @property sampleRate Int
 * @property audioChannel Int
 * @property bytesPerEncoding Int
 * @property bufferMultiplier Int
 * @property bitRate Int
 * @constructor
 */
data class Record(
        val sampleRate: Int = 44100,
        val audioChannel: Int = AudioFormat.CHANNEL_IN_MONO,
        val bytesPerEncoding: Int = 2,
        val bufferMultiplier: Int = 5,
        val bitRate: Int = 32
)