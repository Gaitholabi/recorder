package recorder.recorder.managers

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import recorder.recorder.models.AuthResponse
import recorder.recorder.models.User
import recorder.recorder.models.UserRegistrationResponse
import recorder.recorder.network.RetrofitInterfaces.AuthService
import recorder.recorder.network.RetrofitInterfaces.RefreshAuthService
import recorder.recorder.network.RetrofitInterfaces.UserRegistrationService
import recorder.recorder.network.RetrofitWrapper

/**
 * This class handles the authentication main functionalities
 * @property user User
 * @property retrofit RetrofitWrapper
 * @constructor
 */
class AuthenticationManager(private val user: User,
                            private val retrofit: RetrofitWrapper
) {


  /**
   * This method handles the create account request to the backend and then handling the response
   * functionality
   * @param callback (UserRegistrationResponse?) -> Unit
   */
  fun createAccount(callback: (UserRegistrationResponse?) -> Unit) {
    val retrofitInstance = this.retrofit.getInstance().create(UserRegistrationService::class.java)
    retrofitInstance.register(user).enqueue(object : Callback<UserRegistrationResponse> {
      override fun onFailure(call: Call<UserRegistrationResponse>?, t: Throwable?) {
        callback(UserRegistrationResponse("invalid", -1))

      }

      override fun onResponse(call: Call<UserRegistrationResponse>?, authResponse: Response<UserRegistrationResponse>?) {
        callback(authResponse?.body())
      }
    })
  }

  /**
   * This method handles the the authentication request to the backend and then handling the response
   * funtionality
   * @param callback (AuthResponse?) -> Unit
   */
  fun authenticate(callback: (AuthResponse?) -> Unit) {
    val retrofitInstance = this.retrofit.getInstance().create(AuthService::class.java)
    retrofitInstance.login(user).enqueue(object : Callback<AuthResponse> {
      override fun onFailure(call: Call<AuthResponse>?, t: Throwable?) {
        callback(AuthResponse("invalid"))
      }

      override fun onResponse(call: Call<AuthResponse>?, authResponse: Response<AuthResponse>?) {
        callback(authResponse?.body())
      }
    })
  }

  /**
   * This method handles the authentication refresh request to the backend and then handling hte reponse
   * funtionality
   * @param authResponse AuthResponse
   * @param callback (AuthResponse?) -> Unit
   */
  fun refreshAuthentication(authResponse: AuthResponse, callback: (AuthResponse?) -> Unit) {
    val retrofitInstance = this.retrofit.getInstance().create(RefreshAuthService::class.java)
    retrofitInstance.login(authResponse).enqueue(object : Callback<AuthResponse> {
      override fun onFailure(call: Call<AuthResponse>?, t: Throwable?) {
        callback(AuthResponse("invalid"))
      }

      override fun onResponse(call: Call<AuthResponse>?, authResponse: Response<AuthResponse>?) {
        callback(authResponse?.body())
      }
    })
  }

}
