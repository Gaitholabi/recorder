package recorder.recorder.managers

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class SharedPreferencesManager {

  companion object {

    /**
     * This method returns an instance of the shared preferences of the application context
     * @param context Context
     * @return SharedPreferences
     */
    private fun getSharedPreferences(context: Context): SharedPreferences {
      return PreferenceManager.getDefaultSharedPreferences(context)
    }

    /**
     * This method saves the current user auth token into the sharedpreferences storage
     * @param accessToken String
     * @param context Context
     */
    fun saveAuthToken(accessToken: String, context: Context) {
      val editor = getSharedPreferences(context).edit()
      editor.putString("accessToken", accessToken)
      editor.apply()
    }

    /**
     * This method fetches the authenticated user auth token from the database
     * @param context Context
     * @return String
     */
    fun getAuthToken(context: Context): String {
      val editor = getSharedPreferences(context)
      return editor.getString("accessToken", "")
    }

    /**
     * This method deletes the authentication token from the sharedpreferences storage
     * @param context Context
     */
    fun removeAuthToken(context: Context) {
      val editor = getSharedPreferences(context).edit()
      editor.remove("accessToken")
      editor.apply()
    }

    /**
     * This method checks if the shared preferences contains an access token
     * @param context Context
     * @return Boolean
     */
    fun authenticated(context: Context): Boolean {
      val editor = getSharedPreferences(context)
      return editor.getString("accessToken", "false") != "false"
    }
  }

}
