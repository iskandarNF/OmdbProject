package tj.iskandarbek.omdb.presentation

import android.view.ViewTreeObserver
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import tj.iskandarbek.omdb.ui.theme.BottomSheetColor
import tj.iskandarbek.omdb.ui.theme.DarkBlue
import tj.iskandarbek.omdb.ui.theme.FocusedEdittext


@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun SearchBottomSheet(
    onClick: () -> Unit,
    sheetState: ModalBottomSheetState,
    searchConditions: (String, String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var title by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    val view = LocalView.current
    val scope = rememberCoroutineScope()
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    DisposableEffect(view) {
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            scope.launch { bringIntoViewRequester.bringIntoView() }
        }
        view.viewTreeObserver.addOnGlobalLayoutListener(listener)
        onDispose { view.viewTreeObserver.removeOnGlobalLayoutListener(listener) }
    }
    Box(
        modifier = Modifier
            .height(360.dp)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
            .background(Color.White)

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(.4f)
                    .height(3.dp)
                    .background(BottomSheetColor),
//                elevation = 0.dp,
                shape = RoundedCornerShape(10.dp)
            ) {

            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Search",
                fontSize = 22.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 32.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextFieldBackground(FocusedEdittext) {
                OutlinedTextField(
                    value = title,
//                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    onValueChange = { title = it },
                    placeholder = {
                        Text(
                            text = "Title",
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth(.9f)
                        .bringIntoViewRequester(bringIntoViewRequester),
                    colors = TextFieldDefaults.textFieldColors(textColor = DarkBlue),
                    shape = RoundedCornerShape(12.dp),
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextFieldBackground(FocusedEdittext) {
                OutlinedTextField(
                    value = quantity,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    onValueChange = { quantity = it },
                    placeholder = {
                        Text(
                            text = "Quantity",
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth(.9f)
                        .bringIntoViewRequester(bringIntoViewRequester),
                    colors = TextFieldDefaults.textFieldColors(textColor = DarkBlue),
                    shape = RoundedCornerShape(12.dp),
                )
            }
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            searchConditions(title, quantity)
                            sheetState.hide()
                        }
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
                        text = "Apply",
                        fontSize = 18.sp,
                        letterSpacing = .2.sp,
                        color = White,
                        fontWeight = FontWeight.Normal
                    )

                }
            Spacer(modifier = Modifier.height(16.dp))

            }


        }
    }

}


@Composable
fun OutlinedTextFieldBackground(
    color: Color,
    content: @Composable () -> Unit
) {
    Box {
        // This box works as background
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    color,
                    shape = RoundedCornerShape(12.dp)
                )
        )
        content()
    }
}