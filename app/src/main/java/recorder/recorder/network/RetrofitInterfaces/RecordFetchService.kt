package recorder.recorder.network.RetrofitInterfaces

import retrofit2.Call
import retrofit2.http.*
import recorder.recorder.models.RecordResponse

/**
 * This interface defines the record fetch service call
 */
interface RecordFetchService {
  @Headers("Accept: application/json",
      "Content-Type: application/json"
  )
  @GET("records")
  fun getRecords(@Header("Authorization") accessToken: String): Call<RecordResponse>
}