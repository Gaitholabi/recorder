package recorder.recorder.models

data class RecordElementsResponse(
        val id: Int,
        val filePath: String,
        val createdAt: Any,
        val updatedAt: Any,
        val userId: Int
)