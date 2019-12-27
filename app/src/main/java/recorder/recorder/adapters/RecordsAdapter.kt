package recorder.recorder.adapters

import android.content.Context
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.record_list_item.view.*
import recorder.recorder.R
import recorder.recorder.managers.AudioPlayerManager
import recorder.recorder.models.RecordElementsResponse
import recorder.recorder.utilities.ENV.Companion.PLAY_SERVICE
import recorder.recorder.utilities.ENV.Companion.SERVER_URL

class RecordsAdapter(private val recordsList: List<RecordElementsResponse>, val context: Context) : BaseAdapter() {


  /**
   * This method gets the number of elements in the the passed list
   * @return Int
   */
  override fun getCount(): Int {
    return recordsList.size
  }

  /**
   * This method gets the item at aa certain position
   * @param position
   * @return Any
   */
  override fun getItem(position: Int): Any {
    return recordsList[position]
  }

  /**
   * This method returns the item id
   * @param position
   * @return Long
   */
  override fun getItemId(position: Int): Long {
    return position.toLong()
  }


  private val audioPlayerManager = AudioPlayerManager(context)

  /**
   * This method gets the view based on the position in the list view
   * @param position
   * @param convertView
   * @param parent
   * @return View
   */
  override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

    val view = View.inflate(context, R.layout.record_list_item, null)
    view.recordName.text = recordsList[position].filePath
    val uri = Uri.parse("$SERVER_URL$PLAY_SERVICE/${recordsList[position].filePath}")


    view.playButton.setOnClickListener {
      view.playButton.visibility = View.GONE
      view.pauseButton.visibility = View.VISIBLE
      try {
        playRecord(uri, view)
      } catch (e: Exception) {
        Toast.makeText(context,
            "A problem happened playing the record, please try again.",
            Toast.LENGTH_LONG).show()
      }
    }

    view.pauseButton.setOnClickListener {
      stopRecord(view)
    }
    return view
  }

  /**
   * This method stops the playback of the currently playing record
   * @param view
   */
  private fun stopRecord(view: View) {
    view.pauseButton.visibility = View.GONE
    view.playButton.visibility = View.VISIBLE
    audioPlayerManager.stop()
  }

  /**
   * This method initiates the play record functionality on a new thread
   * @param uri Uri
   * @param view: View
   */
  private fun playRecord(uri: Uri, view: View) {
    Thread(Runnable {
      audioPlayerManager.play(uri) {
        view.pauseButton.visibility = View.GONE
        view.playButton.visibility = View.VISIBLE
      }
    }).run()
  }
}