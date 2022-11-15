package tj.iskandarbek.omdb.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import tj.iskandarbek.omdb.data.model.Search
import tj.iskandarbek.omdb.ui.theme.CardBackground
import tj.iskandarbek.omdb.ui.theme.DarkBlue
import tj.iskandarbek.omdb.ui.theme.GreenColor

@Composable
fun ItemMovie(search: Search, onClick:(String)->Unit) {
    val isLiked = remember {
        mutableStateOf(1)
    }
    val interactionSource = remember { MutableInteractionSource() }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable {
                onClick(search.imdbID)
            },
        elevation = 1.dp,
        backgroundColor = CardBackground,

    ) {
        Row(Modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 8.dp)) {
            SubcomposeAsyncImage(
                model = search.poster,
                contentDescription = "",
                modifier = Modifier
                    .heightIn(max = 100.dp)
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
            Column(Modifier.fillMaxSize().padding(start = 16.dp), verticalArrangement = Arrangement.SpaceBetween) {
                Column() {
                    Text(
                        text = search.title, fontSize = 16.sp,
                        color = DarkBlue,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = search.year,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = search.type,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Row() {
                        Image(
                            painter = painterResource(id = tj.iskandarbek.omdb.R.drawable.comment),
                            contentDescription = "",
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Image(painter = if (isLiked.value % 2 == 0) {
                            painterResource(id = tj.iskandarbek.omdb.R.drawable.liked)
                        } else {
                            painterResource(id = tj.iskandarbek.omdb.R.drawable.like)
                        }, contentDescription = "", modifier = Modifier
                            .size(40.dp)
                            .clickable (
                                interactionSource = interactionSource,
                                indication = null
                            ) {
                                isLiked.value +=1
                            })
                    }
                }
            }
        }
    }
}