package recorder.recorder.activities

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import kotlinx.android.synthetic.main.main_activity.*
import recorder.recorder.R
import recorder.recorder.fragments.PlayRecordFragment
import recorder.recorder.fragments.RecordAudioFragment
import recorder.recorder.managers.SharedPreferencesManager.Companion.authenticated
import recorder.recorder.managers.SharedPreferencesManager.Companion.removeAuthToken


class MainActivity : AppCompatActivity() {

  private val AUDIO_ECHO_REQUEST = 0
  private val WRITE_EXTERNAL_STORAGE_REQUEST = 1
  private val recordAudioFragment = RecordAudioFragment()
  private val playRecordFragment = PlayRecordFragment()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main_activity)
    setRecordFragment()

    bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
      if (menuItem.title == getString(R.string.record)) {
        setRecordFragment()
      } else {
        setPlayRecordFragment()
      }
      return@setOnNavigationItemSelectedListener true
    }
  }

  override fun onResume() {
    if (!authenticated(this)) {
      startActivity(Intent(this, LoginActivity::class.java))
    }
    super.onResume()
  }

  /**
   * This method sets the play record fragment, and changes the bottom navigation bar
   * UI colors
   */
  private fun setPlayRecordFragment() {
    val fragmentTransaction = supportFragmentManager.beginTransaction()
    fragmentTransaction.replace(R.id.fragmentContainer, playRecordFragment).commitAllowingStateLoss()
    recordAudioFragment.stopRecord()
    recordFragmentUIView.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorPrimary, null))
    listenFragmentUIView.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorRed, null))
  }

  /**
   * This method sets the record fragment, and changes the bottom navigation bar
   * UI colors
   */
  private fun setRecordFragment() {
    val fragmentTransaction = supportFragmentManager.beginTransaction()
    fragmentTransaction.replace(R.id.fragmentContainer, recordAudioFragment).commitAllowingStateLoss()
    recordFragmentUIView.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorRed, null))
    listenFragmentUIView.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorPrimary, null))
  }

  /**
   * This method requests the microphone access permission
   */
  fun requestRecordPermission() {
    ActivityCompat.requestPermissions(
        this,
        arrayOf(Manifest.permission.RECORD_AUDIO),
        AUDIO_ECHO_REQUEST)
  }

  /**
   * This method requests the disk write permission
   */
  fun requestWritePermission() {
    ActivityCompat.requestPermissions(
        this,
        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
        WRITE_EXTERNAL_STORAGE_REQUEST)
  }

  /**
   * This method inflates the logout menu
   * @param menu
   * @return Boolean
   */
  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return true
  }

  /**
   * This method handles the onmenu click behaviour
   * @param item MenuItem
   * @return Boolean
   */
  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    if (item?.title == getString(R.string.logout)) {
      removeAuthToken(this)
      startActivity(Intent(this, LoginActivity::class.java))
    }

    return true
  }

  /**
   * This method handles the permission requests
   * @param requestCode
   * @param permissions
   * @param grantResults
   */
  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                          grantResults: IntArray) {
    if (AUDIO_ECHO_REQUEST != requestCode) {
      super.onRequestPermissionsResult(requestCode, permissions, grantResults)
      return
    }
  }


  /**
   *
   * This procedure overrides teh typical back button passed by the user, to not return to the launch activity
   * rather than that, it closes the applicatino
   *
   */
  private var doubleBackToExitPressedOnce: Boolean = false
  private val mHandler = Handler()
  private val mRunnable = Runnable { doubleBackToExitPressedOnce = false }
  override fun onBackPressed() {

    if (doubleBackToExitPressedOnce) {
      val exitAppIntent = Intent(Intent.ACTION_MAIN)
      exitAppIntent.addCategory(Intent.CATEGORY_HOME)
      exitAppIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
      startActivity(exitAppIntent)
    }

    this.doubleBackToExitPressedOnce = true
    toast(getString(R.string.click_error))
    mHandler.postDelayed(mRunnable, 2000)
  }


  override fun onStop() {
    recordAudioFragment.stopRecord()
    super.onStop()
  }

}










