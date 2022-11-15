package tj.iskandarbek.omdb.data.remote

import kotlinx.coroutines.flow.Flow
import tj.iskandarbek.omdb.data.model.ModelSearchMovie
import tj.iskandarbek.omdb.data.model.detailmovie.ModelDetailMovie
import tj.iskandarbek.omdb.utils.BaseUseCase
import tj.iskandarbek.omdb.utils.Resource
import javax.inject.Inject

class OmdbApiRepositoryImpl @Inject constructor(
    private val omdbApi: OmdbApi
) : OmdbApiRepository, BaseUseCase() {

    override suspend fun getSearchedMovie(
        search: String,
        key: String,
        page: String
    ): Flow<Resource<ModelSearchMovie>> =
        safeFlowApiCall {
            omdbApi.getSearchedMovie(search, key, page)
        }

    override suspend fun getDetailMovie(
        id: String,
        key: String
    ): Flow<Resource<ModelDetailMovie>> =
        safeFlowApiCall {
            omdbApi.getDetailMovie(id, key)
        }

}