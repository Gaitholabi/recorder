package recorder.recorder.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * This class wraps the RetroFitlibrary and uses the required functionalities
 * @property baseURL String
 * @property logging HttpLoggingInterceptor
 * @property httpClient Builder
 * @constructor
 */
class RetrofitWrapper(private val baseURL: String) {

  val logging: HttpLoggingInterceptor
  val httpClient: OkHttpClient.Builder

  init {
    logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY
    httpClient = OkHttpClient.Builder()
    httpClient.addInterceptor(logging)
  }

  /**
   * This method builds the retrofit instance
   * @return Retrofit
   */
  fun getInstance(): Retrofit {
    return retrofit2.Retrofit.Builder()
        .baseUrl(this.baseURL)
        .client(httpClient.build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
  }


}