package recorder.recorder.fragments


import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_record_audio.*
import recorder.recorder.R
import recorder.recorder.activities.MainActivity
import recorder.recorder.activities.toast
import recorder.recorder.managers.SharedPreferencesManager.Companion.getAuthToken
import recorder.recorder.services.StreamServices
import recorder.recorder.utilities.ENV.Companion.SOCKET_URL
import recorder.recorder.utilities.ENV.Companion.STREAM_RECORD_SERVICE
import recorder.recorder.utilities.Utilities.Companion.isRecordPermissionGranted
import recorder.recorder.utilities.Utilities.Companion.isStoragePermissionGranted


/**
 * A simple [Fragment] subclass.
 *
 */
class RecordAudioFragment : Fragment() {

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_record_audio, container, false)
  }

  var streamServices: StreamServices? = null

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    val parentActivity = activity as MainActivity

    recordButton.setOnClickListener {

      if (setButtonDelayHandler(parentActivity)) {
        return@setOnClickListener
      }

      if (requestRequiredPermissions(parentActivity)) {
        return@setOnClickListener
      }


      streamServices = StreamServices(
          SOCKET_URL,
          STREAM_RECORD_SERVICE,
          parentActivity.applicationContext,
          getAuthToken(parentActivity.applicationContext)
      )

      recordButton.visibility = View.GONE
      pauseButton.visibility = View.VISIBLE
      streamServices?.record()
    }


    pauseButton.setOnClickListener {
      pauseButton.visibility = View.GONE
      recordButton.visibility = View.VISIBLE
      if (streamServices?.isRecording()!!) {
        streamServices?.stopRecord()
      }
    }

    super.onViewCreated(view, savedInstanceState)
  }

  private fun requestRequiredPermissions(parentActivity: MainActivity): Boolean {
    if (!isRecordPermissionGranted(requireContext())) {
      parentActivity.run {
        requestRecordPermission()
        toast(getString(R.string.record_permission))
      }

      return true
    }

    if (!isStoragePermissionGranted(requireContext())) {
      parentActivity.run {
        requestWritePermission()
        toast(getString(R.string.storage_permission))
      }
      return true
    }
    return false
  }

  private var mLastClickTime: Long = 0

  /**
   * This method adds a 2 seconds delay between the ability for the user to click the button
   * @param parentActivity
   * @return Boolean
   */
  private fun setButtonDelayHandler(parentActivity: MainActivity): Boolean {
    if (SystemClock.elapsedRealtime() - mLastClickTime < 2000) {
      parentActivity.toast(getString(R.string.click_error))
      return true
    }
    mLastClickTime = SystemClock.elapsedRealtime()
    return false
  }

  override fun onDetach() {
    stopRecord()
    super.onDetach()
  }

  fun stopRecord() {
    if (streamServices != null && streamServices?.isRecording()!!) {
      streamServices?.stopRecord()
      pauseButton.visibility = View.GONE
      recordButton.visibility = View.VISIBLE

    }
  }

  override fun onDestroy() {
    stopRecord()
    super.onDestroy()
  }
}


