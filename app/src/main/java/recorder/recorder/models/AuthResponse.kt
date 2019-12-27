package recorder.recorder.models

/**
 * This class defines the success AuthResponse
 * @property accessToken String?
 * @property strategy String?
 * @constructor
 */
data class AuthResponse(
    val accessToken: String?,
    val strategy: String? = "jwt"
)