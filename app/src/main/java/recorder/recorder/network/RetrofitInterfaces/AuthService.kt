package recorder.recorder.network.RetrofitInterfaces

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import recorder.recorder.models.AuthResponse
import recorder.recorder.models.User

/**
 * This interface defines the retrofit authentication service call
 */

interface AuthService {
  @Headers("Accept: application/json",
      "Content-Type: application/json"
  )
  @POST("authentication")
  fun login(@Body user: User): Call<AuthResponse>

}
