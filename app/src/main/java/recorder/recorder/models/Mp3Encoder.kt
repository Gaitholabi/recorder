package recorder.recorder.models

import com.naman14.androidlame.AndroidLame
import com.naman14.androidlame.LameBuilder
import recorder.recorder.interfaces.AudioEncoderInterface

/**
 * This class extends the functionality of the AndroidLame library
 * @property lameBuilder LameBuilder
 * @constructor
 */
data class Mp3Encoder(val lameBuilder: LameBuilder) : AndroidLame(), AudioEncoderInterface