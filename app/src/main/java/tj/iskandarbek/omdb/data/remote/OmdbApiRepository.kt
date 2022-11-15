package tj.iskandarbek.omdb.data.remote

import kotlinx.coroutines.flow.Flow
import retrofit2.http.Query
import tj.iskandarbek.omdb.data.model.ModelSearchMovie
import tj.iskandarbek.omdb.data.model.detailmovie.ModelDetailMovie
import tj.iskandarbek.omdb.utils.BaseUseCase
import tj.iskandarbek.omdb.utils.Resource
import javax.inject.Inject

interface OmdbApiRepository {
    suspend fun getSearchedMovie(
        search:String,  key:String,  page:String
    ): Flow<Resource<ModelSearchMovie>>

    suspend fun getDetailMovie(
        id:String,  key:String
    ): Flow<Resource<ModelDetailMovie>>
}