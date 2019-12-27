package recorder.recorder.models

data class RecordResponse(
        val total: Int,
        val limit: Int,
        val skip: Int,
        val data: List<RecordElementsResponse>
)