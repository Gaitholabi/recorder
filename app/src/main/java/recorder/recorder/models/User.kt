package recorder.recorder.models

/**
 *
 * @property email String
 * @property password String
 * @property strategy String
 * @constructor
 */
data class User(val email: String, val password: String, val strategy: String = "local")