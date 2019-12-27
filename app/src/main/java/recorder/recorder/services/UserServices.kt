package recorder.recorder.services

import android.content.Context
import recorder.recorder.managers.AuthenticationManager
import recorder.recorder.managers.SharedPreferencesManager
import recorder.recorder.models.AuthResponse
import recorder.recorder.models.User
import recorder.recorder.network.RetrofitWrapper

/**
 * This class defines the user required services
 * @property user User
 * @property baseURL String
 * @property context Context
 * @property retrofit RetrofitWrapper
 * @property authenticationManager AuthenticationManager
 * @constructor
 */

class UserServices(
    private val user: User,
    private val serverURL: String,
    private val context: Context
) {

  private val retrofit: RetrofitWrapper
  private val authenticationManager: AuthenticationManager

  /**
   * initiate the required objects, and inject dependencies
   */
  init {
    retrofit = RetrofitWrapper(serverURL)
    authenticationManager = AuthenticationManager(user, retrofit)
  }

  /**
   * This method authenticates the user with the backend
   * @param callback (Boolean) -> Unit
   */
  fun auth(callback: (Boolean) -> Unit) {
    authenticationManager.authenticate { authResponse ->
      if (authResponse?.accessToken != null && authResponse.accessToken != "invalid") {
        SharedPreferencesManager.saveAuthToken(authResponse.accessToken, context)
        callback(true)
      } else {
        callback(false)
      }
    }
  }

  /**
   * This method refreshes the user authentication with the database
   * @param authResponse AuthResponse
   * @param callback (Boolean) -> Unit
   */
  fun refreshAuth(callback: (Boolean) -> Unit) {
    val authResponse = AuthResponse(SharedPreferencesManager.getAuthToken(context))
    authenticationManager.refreshAuthentication(authResponse) { authApiResponse ->
      if (authApiResponse?.accessToken != null && authApiResponse.accessToken != "invalid") {
        SharedPreferencesManager.saveAuthToken(authApiResponse.accessToken, context)
        callback(true)
      } else {
        SharedPreferencesManager.removeAuthToken(context)
        callback(false)
      }
    }
  }

  /**
   * This method creates a user on the backend, then gets the authentication token
   * @param callback (Boolean) -> Unit
   */
  fun createUser(callback: (Boolean) -> Unit) {
    authenticationManager.createAccount { userRegistrationResponse ->
      if (userRegistrationResponse?.email != null && userRegistrationResponse.email != "invalid") {
        this.auth { result ->
          callback(result)
        }
      } else {
        callback(false)
      }
    }
  }

}
