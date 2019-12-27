package recorder.recorder.network.RetrofitInterfaces

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import recorder.recorder.models.AuthResponse

/**
 * This interface defines the refresh authentication service call
 */

interface RefreshAuthService {
  @Headers("Accept: application/json",
      "Content-Type: application/json"
  )
  @POST("authentication")
  fun login(@Body authResponse: AuthResponse): Call<AuthResponse>
}