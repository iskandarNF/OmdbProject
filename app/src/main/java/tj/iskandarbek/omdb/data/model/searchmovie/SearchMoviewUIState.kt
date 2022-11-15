package tj.iskandarbek.omdb.data.model.searchmovie

import tj.iskandarbek.omdb.data.model.ModelSearchMovie

data class SearchMoviewUIState(
    val data: ModelSearchMovie?=null,
    val isLoading: Boolean = false,
    val error: String?=null,
    val networkError: String?=null
)
