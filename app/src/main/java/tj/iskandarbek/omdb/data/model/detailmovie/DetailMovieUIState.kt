package tj.iskandarbek.omdb.data.model.detailmovie

import tj.iskandarbek.omdb.data.model.ModelSearchMovie

data class DetailMovieUIState(
    val data: ModelDetailMovie?=null,
    val isLoading: Boolean = false,
    val error: String?=null
)
