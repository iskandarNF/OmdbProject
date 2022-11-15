package tj.iskandarbek.omdb.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tj.iskandarbek.omdb.data.model.detailmovie.DetailMovieUIState
import tj.iskandarbek.omdb.data.model.searchmovie.SearchMoviewUIState
import tj.iskandarbek.omdb.data.remote.OmdbApiRepositoryImpl
import tj.iskandarbek.omdb.utils.Resource
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val serviceRepositoryImpl: OmdbApiRepositoryImpl
) : ViewModel() {
    var details: DetailMovieUIState by mutableStateOf(DetailMovieUIState())
    init {

        savedStateHandle.get<String>("caseid")?.let { id ->
            detailMovie(id)
        }

    }
    val API_KEY : String
        get() = "3b68595c"

    fun detailMovie(id: String) {
        viewModelScope.launch {
            serviceRepositoryImpl.getDetailMovie(
                id, API_KEY
            ).collect { result ->
                details = when (result) {
                    is Resource.Success -> {
                        DetailMovieUIState(data = result.content)
                    }
                    is Resource.Loading -> {
                        DetailMovieUIState(isLoading = true)
                    }
                    is Resource.Error -> {
                        DetailMovieUIState(error = result.message)
                    }
                    is Resource.NetworkError -> {
                        DetailMovieUIState(error = result.exception.toString())
                    }
                }
            }

        }

    }

}