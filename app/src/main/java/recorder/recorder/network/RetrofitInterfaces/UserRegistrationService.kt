package recorder.recorder.network.RetrofitInterfaces

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import recorder.recorder.models.User
import recorder.recorder.models.UserRegistrationResponse

/**
 * This interface defines the user registration service call
 */
interface UserRegistrationService {

  @Headers("Accept: application/json", "Content-Type: application/json")
  @POST("users")
  fun register(@Body user: User): Call<UserRegistrationResponse>


}