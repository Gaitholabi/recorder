package recorder.recorder.services

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import recorder.recorder.models.RecordResponse
import recorder.recorder.network.RetrofitInterfaces.RecordFetchService
import recorder.recorder.network.RetrofitWrapper

/**
 * This class defines the features that are needed to facilitate the records
 * @property serverURL
 * @property accessToken
 * @property retrofit
 * @constructor
 */
class RecordServices(private val serverURL: String,
                     private val accessToken: String) {


  private val retrofit: RetrofitWrapper


  init {
    retrofit = RetrofitWrapper(serverURL)
  }

  /**
   * This method fetches all the available records from the backend api endpoint
   * @param callback
   */
  // TODO abstract the networking event handling
  fun getAllRecords(callback: (RecordResponse) -> Unit) {
    val retrofitInstance = this.retrofit.getInstance().create(RecordFetchService::class.java)
    retrofitInstance.getRecords(accessToken).enqueue(object : Callback<RecordResponse> {
      override fun onResponse(call: Call<RecordResponse>, response: Response<RecordResponse>) {
        if (response.body() != null) {
          callback(response.body()!!)
        } else {
          callback(RecordResponse(0, 0, 0, ArrayList()))

        }
      }

      override fun onFailure(call: Call<RecordResponse>, t: Throwable) {
        callback(RecordResponse(0, 0, 0, ArrayList()))
      }
    }
    )
  }

}