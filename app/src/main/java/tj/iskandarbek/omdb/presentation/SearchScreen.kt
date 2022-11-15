package tj.iskandarbek.omdb.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import tj.iskandarbek.omdb.Navigationomdb
import tj.iskandarbek.omdb.R
import tj.iskandarbek.omdb.ui.theme.CardBackground
import tj.iskandarbek.omdb.ui.theme.DarkBlue
import tj.iskandarbek.omdb.ui.theme.WallpaperColor
import tj.iskandarbek.omdb.viewmodel.OmdbViewModel


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchScreen(navController: NavController, viewModel: OmdbViewModel = hiltViewModel()) {
    val moviewUIState = viewModel.searchList
    val defaultTitle = remember {
        mutableStateOf("Movies")
    }
    var defaultCount by remember {
        mutableStateOf(2)
    }
    val scope = rememberCoroutineScope()
    val bsState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    Log.e("TAG", "moviewUIState: ${moviewUIState}" )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(WallpaperColor),
        topBar = {
            val appBarHorizontalPadding = 4.dp
            val titleIconModifier = Modifier
                .fillMaxHeight()
                .width(72.dp - appBarHorizontalPadding)

            TopAppBar(
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                modifier = Modifier.fillMaxWidth()
            ) {

                //TopAppBar Content
                Box(Modifier.height(32.dp)) {

                    //Navigation Icon
                    Row(titleIconModifier, verticalAlignment = Alignment.CenterVertically) {
                        CompositionLocalProvider(
                            LocalContentAlpha provides ContentAlpha.high,
                        ) {
                            IconButton(
                                onClick = {
                                    scope.launch {
                                        bsState.animateTo(ModalBottomSheetValue.Expanded)
                                    }
                                },
                                enabled = true,
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.search),
                                    contentDescription = "Search",
                                )
                            }
                        }
                    }
                    Row(
                        Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        ProvideTextStyle(value = MaterialTheme.typography.h6) {
                            CompositionLocalProvider(
                                LocalContentAlpha provides ContentAlpha.high,
                            ) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    maxLines = 1,
                                    text = defaultTitle.value
                                )
                            }
                        }
                    }


                }
            }
        }
    ) {

        ModalBottomSheetLayout(
            sheetContent = {
            SearchBottomSheet(onClick = { /*TODO*/ }, sheetState = bsState, searchConditions = {
                it, it1->
                defaultTitle.value = it
                defaultCount = it1.toInt()
                viewModel.searchMovie(it,it1)
            })
            },
            sheetState = bsState,
            sheetElevation = 0.dp,
            sheetBackgroundColor = Color.Transparent,
        ) {
            Column {
                if (moviewUIState.data != null) {
                    LazyColumn(contentPadding = PaddingValues(top = 4.dp)) {
                        itemsIndexed(
                            moviewUIState.data.search ?: emptyList()
                        ) {index, it->
                            if (index<defaultCount){
                                ItemMovie(
                                    search = it,
                                    onClick = {
                                        navController.navigate(Navigationomdb.DetailScreen.withArgs(it))
                                    }
                                )
                            }

                        }

                    }
                    if (moviewUIState.data.search.isEmpty()){
                        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "EMPTY LIST")
                        }
                    }

                }

            }
        }
    }
    if (moviewUIState.isLoading){
        LoadingDialog()
    }
    if (moviewUIState.error!=null){
        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = moviewUIState.error)
        }
    }
    if (moviewUIState.data==null && !moviewUIState.isLoading && moviewUIState.error==null){
        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "OOPS NOTHING IS HERE")
            Text(text = "SET THE SEARCH IN PARAMETER")
        }
    }
    if (moviewUIState.networkError!=null){
        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = moviewUIState.networkError)
        }
    }

}