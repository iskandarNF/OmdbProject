package tj.iskandarbek.omdb.data.model.detailmovie


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ModelDetailMovie(
    @Json(name = "Actors")
    val actors: String = "",
    @Json(name = "Awards")
    val awards: String = "",
    @Json(name = "BoxOffice")
    val boxOffice: String = "",
    @Json(name = "Country")
    val country: String = "",
    @Json(name = "DVD")
    val dVD: String = "",
    @Json(name = "Director")
    val director: String = "",
    @Json(name = "Genre")
    val genre: String = "",
    @Json(name = "imdbID")
    val imdbID: String = "",
    @Json(name = "imdbRating")
    val imdbRating: String = "",
    @Json(name = "imdbVotes")
    val imdbVotes: String = "",
    @Json(name = "Language")
    val language: String = "",
    @Json(name = "Metascore")
    val metascore: String = "",
    @Json(name = "Plot")
    val plot: String = "",
    @Json(name = "Poster")
    val poster: String = "",
    @Json(name = "Production")
    val production: String = "",
    @Json(name = "Rated")
    val rated: String = "",
    @Json(name = "Ratings")
    val ratings: List<Rating> = listOf(),
    @Json(name = "Released")
    val released: String = "",
    @Json(name = "Response")
    val response: String = "",
    @Json(name = "Runtime")
    val runtime: String = "",
    @Json(name = "Title")
    val title: String = "",
    @Json(name = "Type")
    val type: String = "",
    @Json(name = "Website")
    val website: String = "",
    @Json(name = "Writer")
    val writer: String = "",
    @Json(name = "Year")
    val year: String = ""
)