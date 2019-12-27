package recorder.recorder.models

import com.naman14.androidlame.LameBuilder

/**
 * This class extends the main Mp3Encoder builder method
 */
class Mp3EncoderBuilder : LameBuilder() {


  /**
   * This method sets the desired quality of the encoding
   * @param quality Int
   * @return Mp3EncoderBuilder
   */
  override fun setQuality(quality: Int): Mp3EncoderBuilder {
    this.quality = quality
    return this
  }

  /**
   * This method sets the sample rate of the  input file
   * @param inSampleRate Int
   * @return Mp3EncoderBuilder
   */
  override fun setInSampleRate(inSampleRate: Int): Mp3EncoderBuilder {
    this.inSampleRate = inSampleRate
    return this
  }

  /**
   * This method sets the sample rate of the outputted encoded file
   * @param outSampleRate Int
   * @return Mp3EncoderBuilder
   */
  override fun setOutSampleRate(outSampleRate: Int): Mp3EncoderBuilder {
    this.outSampleRate = outSampleRate
    return this
  }

  /**
   * This method sets the bitrate of the file
   * @param bitrate Int
   * @return Mp3EncoderBuilder
   */
  override fun setOutBitrate(bitrate: Int): Mp3EncoderBuilder {
    this.outBitrate = bitrate
    return this
  }

  /**
   * This method sets the channels count
   * @param channels Int
   * @return Mp3EncoderBuilder
   */
  override fun setOutChannels(channels: Int): Mp3EncoderBuilder {
    this.outChannel = channels
    return this
  }

  /**
   * This method sets the title metadata
   * @param title String
   * @return Mp3EncoderBuilder
   */
  override fun setId3tagTitle(title: String): Mp3EncoderBuilder {
    this.id3tagTitle = title
    return this
  }

  /**
   * This method sets the artist metadata
   * @param artist String
   * @return Mp3EncoderBuilder
   */
  override fun setId3tagArtist(artist: String): Mp3EncoderBuilder {
    this.id3tagArtist = artist
    return this
  }

  /**
   * This method sets the album metadata
   * @param album String
   * @return Mp3EncoderBuilder
   */
  override fun setId3tagAlbum(album: String): Mp3EncoderBuilder {
    this.id3tagAlbum = album
    return this
  }

  /**
   * This method sets the comment metadata
   * @param comment String
   * @return Mp3EncoderBuilder
   */
  override fun setId3tagComment(comment: String): Mp3EncoderBuilder {
    this.id3tagComment = comment
    return this
  }

  /**
   * This method sets the year metadata
   * @param year String
   * @return Mp3EncoderBuilder
   */
  override fun setId3tagYear(year: String): Mp3EncoderBuilder {
    this.id3tagYear = year
    return this
  }

  /**
   * This method sets the scale input
   * @param scaleAmount Float
   * @return Mp3EncoderBuilder
   */
  override fun setScaleInput(scaleAmount: Float): Mp3EncoderBuilder {
    this.scaleInput = scaleAmount
    return this
  }

  /**
   * This method sets the mode
   * @param mode Mode
   * @return Mp3EncoderBuilder
   */
  override fun setMode(mode: Mode): Mp3EncoderBuilder {
    this.mode = mode
    return this
  }

  /**
   * This method sets the vbr mode
   * @param mode VbrMode
   * @return Mp3EncoderBuilder
   */
  override fun setVbrMode(mode: VbrMode): Mp3EncoderBuilder {
    this.vbrMode = mode
    return this
  }

  /**
   * This method sets the vbr quality
   * @param quality Int
   * @return Mp3EncoderBuilder
   */
  override fun setVbrQuality(quality: Int): Mp3EncoderBuilder {
    this.vbrQuality = quality
    return this
  }

  /**
   * This method sets the mean bitrate
   * @param bitrate Int
   * @return Mp3EncoderBuilder
   */
  override fun setAbrMeanBitrate(bitrate: Int): Mp3EncoderBuilder {
    this.abrMeanBitrate = bitrate
    return this
  }

  /**
   * This method sets the low pass frequency
   * @param freq Int
   * @return Mp3EncoderBuilder
   */
  override fun setLowpassFreqency(freq: Int): Mp3EncoderBuilder {
    this.lowpassFreq = freq
    return this
  }

  /**
   * This method sets te high pass frequency
   * @param freq Int
   * @return Mp3EncoderBuilder
   */
  override fun setHighpassFreqency(freq: Int): Mp3EncoderBuilder {
    this.highpassFreq = freq
    return this
  }

  /**
   * This method returns an instance of the Mp3Encoder
   * @return Mp3Encoder
   */
  override fun build(): Mp3Encoder {
    return Mp3Encoder(this)
  }

}
