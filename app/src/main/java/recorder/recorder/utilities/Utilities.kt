package recorder.recorder.utilities

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.text.TextUtils
import androidx.core.app.ActivityCompat

/**
 * This class contains utilities that are needed to facilitate the application workflow
 * Such as checking permissions, and input validation
 */
class Utilities {

  companion object {

    /**
     * This procedure checks if the email is in valid form
     * @param email
     * @return true if the email is valid, false if not
     */
    fun isValidEmail(email: String): Boolean {
      return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    /**
     * This procedure checks if the device currently have an internet connection
     * @param context
     * @return true if there is an internet connection, false if not
     */
    fun haveInternetConnection(context: Context): Boolean {
      var haveConnectedWifi = false
      var haveConnectedMobile = false

      val connectivityManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
      val networkInfo = connectivityManager.activeNetworkInfo

      if (networkInfo?.typeName.equals("WIFI", true) && networkInfo.isConnected) {
        haveConnectedWifi = true
      }
      if (networkInfo?.typeName.equals("MOBILE", true) && networkInfo.isConnected) {
        haveConnectedMobile = true
      }

      return haveConnectedMobile || haveConnectedWifi
    }

    /**
     * This method checks if a password is valid
     * @param password
     * @return true if the password is valid, false if not
     */
    fun isValidPassword(password: String): Boolean {
      // TODO add strict password measures
      return password.length > 6
    }

    /**
     * This method checks if the record permissions are granted
     * @param context
     * @return true if the permission is given, false if not
     */
    fun isRecordPermissionGranted(context: Context): Boolean {
      return ActivityCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * This method checks if the storage permissions are granted
     * @param context
     * @return true if the permission is given, false if not
     */
    fun isStoragePermissionGranted(context: Context): Boolean {
      return ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }

  }
}