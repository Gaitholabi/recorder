package recorder.recorder.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import recorder.recorder.R
import recorder.recorder.managers.SharedPreferencesManager.Companion.authenticated
import recorder.recorder.models.User
import recorder.recorder.services.UserServices
import recorder.recorder.utilities.ENV
import recorder.recorder.utilities.Utilities.Companion.isValidEmail
import recorder.recorder.utilities.Utilities.Companion.isValidPassword

class LoginActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)

    if (authenticated(applicationContext)) {
      startActivity(Intent(this, MainActivity::class.java))
    }

    /**
     * This button sends an authentication request to the database
     */
    loginButton.setOnClickListener { view ->
      progressBar.visibility = View.VISIBLE
      val emailString = emailEditText.text.toString()
      val passwordString = passwordEditText.text.toString()

      if (!isValidEmail(emailString)) {
        toast(getString(R.string.email_error))
        progressBar.visibility = View.GONE
        return@setOnClickListener
      }

      sendAuthRequest(emailString, passwordString)
    }

    /**
     * This button sends a registration request to the backend
     */
    registerButton.setOnClickListener {
      progressBar.visibility = View.VISIBLE
      val emailString = emailEditText.text.toString()
      val passwordString = passwordEditText.text.toString()

      if (!isValidEmail(emailString) || !isValidPassword(passwordString)) {
        toast(getString(R.string.email_or_password_error))
        progressBar.visibility = View.GONE
        return@setOnClickListener
      }
      sendRegisterRequest(emailString, passwordString)
    }

  }

  /**
   * This method sends a registration request to the backend
   * @param emailString String
   * @param passwordString String
   */
  private fun sendRegisterRequest(emailString: String, passwordString: String) {
    val userServices = UserServices(User(emailString, passwordString), ENV.SERVER_URL, applicationContext)

    userServices.createUser { result ->
      if (result) {
        startActivity(Intent(this, MainActivity::class.java))
      } else {
        toast(getString(R.string.credentials_error))
        progressBar.visibility = View.GONE
      }
    }
  }

  /**
   * This method sends an auth request to the backend
   * @param emailString String
   * @param passwordString String
   */
  private fun sendAuthRequest(emailString: String, passwordString: String) {
    val userServices = UserServices(User(emailString, passwordString), ENV.SERVER_URL, applicationContext)
    userServices.auth { result ->
      if (result) {
        startActivity(Intent(this, MainActivity::class.java))
      } else {
        toast(getString(R.string.credentials_error))
        progressBar.visibility = View.GONE
      }
    }
  }

  /**
   *
   * This procedure overrides teh typical back button passed by the user, to not return to the launch activity
   * rather than that, it closes the applicatino
   */
  private var doubleBackToExitPressedOnce: Boolean = false
  private val mHandler = Handler()
  private val mRunnable = Runnable { doubleBackToExitPressedOnce = false }
  override fun onBackPressed() {

    if (doubleBackToExitPressedOnce) {
      val a = Intent(Intent.ACTION_MAIN)
      a.addCategory(Intent.CATEGORY_HOME)
      a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
      startActivity(a)
    }

    this.doubleBackToExitPressedOnce = true
    toast(getString(R.string.click_error))

    mHandler.postDelayed(mRunnable, 2000)
  }

}

/**
 * This extension simplifies the toast function to only take a char sequence
 */
fun Context.toast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()