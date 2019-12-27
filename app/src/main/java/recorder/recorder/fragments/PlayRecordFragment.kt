package recorder.recorder.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_play_record.*
import recorder.recorder.R
import recorder.recorder.activities.MainActivity
import recorder.recorder.activities.toast
import recorder.recorder.adapters.RecordsAdapter
import recorder.recorder.managers.SharedPreferencesManager.Companion.getAuthToken
import recorder.recorder.services.RecordServices
import recorder.recorder.utilities.ENV.Companion.SERVER_URL


class PlayRecordFragment : Fragment() {


  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_play_record, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    val parentActivity = activity as MainActivity
    val recordService = RecordServices(SERVER_URL, getAuthToken(parentActivity.applicationContext))

    setRecordsView(recordService, parentActivity)
    super.onViewCreated(view, savedInstanceState)
  }


  /**
   * This method fetches the records from the backend, and sets the list view adapter
   * @param recordService RecordServices
   * @param parentActivity MainActivity
   */
  private fun setRecordsView(recordService: RecordServices, parentActivity: MainActivity) {
    recordService.getAllRecords { recordResponse ->
      val adapter = RecordsAdapter(recordResponse.data, parentActivity.applicationContext)
      if (recordsListView != null) {
        recordsListView.adapter = adapter
      }

      if (recordResponse.data.isEmpty()) {
        parentActivity.toast(getString(R.string.no_records_error))
      }
    }

  }
}

