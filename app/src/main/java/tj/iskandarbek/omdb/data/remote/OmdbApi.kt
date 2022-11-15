package tj.iskandarbek.omdb.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import tj.iskandarbek.omdb.data.model.ModelSearchMovie
import tj.iskandarbek.omdb.data.model.detailmovie.ModelDetailMovie

interface OmdbApi {


    @GET(".")
    suspend fun getSearchedMovie(
        @Query("s") search: String,
        @Query("apikey") key: String,
        @Query("page") page: String
    ): Response<ModelSearchMovie>
    @GET(".")
    suspend fun getDetailMovie(
        @Query("i") id: String,
        @Query("apikey") key: String
    ): Response<ModelDetailMovie>
}