package tj.iskandarbek.omdb.presentation

import android.content.Intent
import android.net.Uri
import android.view.ViewTreeObserver
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import kotlinx.coroutines.launch
import tj.iskandarbek.omdb.R
import tj.iskandarbek.omdb.ui.theme.*
import tj.iskandarbek.omdb.viewmodel.DetailViewModel


@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun DetailScreen(navController: NavController, viewModel: DetailViewModel = hiltViewModel()) {
    val moviewUIState = viewModel.details

    val defaultCount = remember {
        mutableStateOf(0)
    }
    val isLiked = remember {
        mutableStateOf(1)
    }
    var title by remember { mutableStateOf("") }
    val interactionSource = remember { MutableInteractionSource() }
    val view = LocalView.current
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    DisposableEffect(view) {
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            scope.launch { bringIntoViewRequester.bringIntoView() }
        }
        view.viewTreeObserver.addOnGlobalLayoutListener(listener)
        onDispose { view.viewTreeObserver.removeOnGlobalLayoutListener(listener) }
    }
    if (moviewUIState.data != null) {
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
                                    navController.navigateUp()
                                },
                                enabled = true,
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.arrowone),
                                    contentDescription = "Back",
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
                                    text = "Movie Info"
                                )
                            }
                        }
                    }


                }
            }
        }
    ) {



            Column {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    elevation = 1.dp,
                    backgroundColor = CardBackground,

                    ) {
                    Row(
                        Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        SubcomposeAsyncImage(
                            model = moviewUIState.data.poster,
                            contentDescription = "",
                            modifier = Modifier
                                .heightIn(max = 200.dp)
                                .background(color = CardBackground)
                                .clip(RoundedCornerShape(5.dp))
                        ) {
                            val state = painter.state
                            if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                                CircularProgressIndicator(
                                    color = GreenColor
                                )
                            } else {
                                SubcomposeAsyncImageContent()
                            }
                        }
                        Column(
                            Modifier
                                .fillMaxSize()
                                .padding(start = 16.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column() {
                                Text(
                                    text = moviewUIState.data.title, fontSize = 16.sp,
                                    color = DarkBlue,
                                    fontWeight = FontWeight.Normal,
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Text(
                                    text = moviewUIState.data.year,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal,
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = moviewUIState.data.type,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal
                                )

                            }
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {

                                Row() {
                                    Card(
                                        modifier = Modifier
                                            .wrapContentWidth()
                                            .height(40.dp)
                                            .clickable {
                                                val browserIntent = Intent(
                                                    Intent.ACTION_VIEW,
                                                    Uri.parse("https://www.imdb.com/title/"+moviewUIState.data.imdbID)
                                                )
                                                context.startActivity(browserIntent)
                                            },
                                        elevation = 1.dp,
                                        backgroundColor = Greybackground
                                    ) {
                                        Row(
                                            Modifier.padding(horizontal = 6.dp),
                                            horizontalArrangement = Arrangement.Center,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.comment),
                                                contentDescription = "",
                                                modifier = Modifier.size(40.dp)
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(text = "IMDb", textAlign = TextAlign.Center)
                                        }
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Image(painter = if (isLiked.value % 2 == 0) {
                                        painterResource(id = R.drawable.liked)
                                    } else {
                                        painterResource(id = R.drawable.like)
                                    }, contentDescription = "", modifier = Modifier
                                        .size(40.dp)
                                        .clickable(
                                            interactionSource = interactionSource,
                                            indication = null
                                        ) {
                                            isLiked.value += 1
                                        })
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "COMMENTS", Modifier.padding(horizontal = 16.dp), color = Color(0xFF9796A9), fontSize = 16.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    elevation = 1.dp,
                    backgroundColor = CardBackground,

                    ) {

                        Column() {
                            OutlinedTextField(
                                value = title,
//                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                                onValueChange = { title = it },
                                placeholder = {
                                    Text(
                                        text = "Write your comment",
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .padding(horizontal = 16.dp)
                                    .bringIntoViewRequester(bringIntoViewRequester),
                                colors = TextFieldDefaults.textFieldColors(textColor = DarkBlue),
                                shape = RoundedCornerShape(12.dp),
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = {

                                },
                                modifier = Modifier
                                    .height(50.dp)
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                contentPadding = PaddingValues(10.dp),
                                border = BorderStroke(1.dp, DarkBlue),
                                shape = RoundedCornerShape(15),
                                colors = ButtonDefaults.buttonColors(backgroundColor = DarkBlue)
                            ) {

                                Text(
                                    text = "Leave comment",
                                    fontSize = 18.sp,
                                    letterSpacing = .2.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Normal
                                )

                            }
                            Spacer(modifier = Modifier.height(16.dp))

                        }
                }
            }

        }
    }
    if (moviewUIState.isLoading) {
        LoadingDialog()
    }
}
