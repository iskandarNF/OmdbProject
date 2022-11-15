package tj.iskandarbek.omdb.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tj.iskandarbek.omdb.data.model.searchmovie.SearchMoviewUIState
import tj.iskandarbek.omdb.data.remote.OmdbApiRepositoryImpl
import tj.iskandarbek.omdb.utils.Resource
import javax.inject.Inject

@HiltViewModel
class OmdbViewModel @Inject constructor(
    private val serviceRepositoryImpl: OmdbApiRepositoryImpl
) : ViewModel() {
    var searchList: SearchMoviewUIState by mutableStateOf(SearchMoviewUIState())

    val API_KEY : String
        get() = "3b68595c"


     fun searchMovie(search: String,  page:String) {
        viewModelScope.launch {
            serviceRepositoryImpl.getSearchedMovie(
                search, API_KEY, page
            ).collect { result ->
                searchList = when (result) {
                    is Resource.Success -> {
                        SearchMoviewUIState(data = result.content)
                    }
                    is Resource.Loading -> {
                        SearchMoviewUIState(isLoading = true)
                    }
                    is Resource.Error -> {
                        SearchMoviewUIState(error = result.message)
                    }
                    is Resource.NetworkError -> {
                        SearchMoviewUIState(error = result.exception.toString())
                    }
                }
            }

        }

    }

}