package tj.iskandarbek.omdb.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ModelSearchMovie(
    @Json(name = "Response")
    val response: String = "",
    @Json(name = "Search")
    val search: List<Search> = listOf(),
    @Json(name = "totalResults")
    val totalResults: String = ""
)