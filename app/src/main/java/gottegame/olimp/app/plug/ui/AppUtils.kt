package gottegame.olimp.app.plug.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gottegame.olimp.app.R

@Composable
fun AppText(
    modifier: Modifier? = Modifier,
    text: String,
    size: TextUnit? = null,
    color: Color? = null,
    textDecoration: TextDecoration? = null
) {
    Text(
        modifier = modifier ?: Modifier,
        color = color ?: Color.White,
        text = text,
        fontFamily = FontFamily(Font(R.font.railway)),
        fontSize = size ?: 16.sp,
        textDecoration = textDecoration,
        overflow = TextOverflow.Ellipsis,
        maxLines = 7,
        textAlign = TextAlign.Center
    )
}

@Composable
fun ProgressBar() {
    val infiniteTransition = rememberInfiniteTransition()
    val color = if (isSystemInDarkTheme()) Color.White else Color.Black
    val angle by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(1500, easing = FastOutSlowInEasing)
        )
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier.rotate(angle),
            progress = 0.5f,
            color = color,
            strokeWidth = 2.dp,
        )
        AppText(text = stringResource(id = R.string.loading))
    }
}

@ExperimentalAnimationApi
@Composable
fun AppButton(
    modifier: Modifier? = Modifier,
    text: String = "",
    textColor: Color? = null,
    enabled: Boolean = true,
    onClick: (() -> Unit)
) {
    Button(
        contentPadding = PaddingValues(),
        onClick = { onClick.invoke() },
        enabled = enabled
    )
    {
        Box(
            modifier = modifier ?: Modifier,
            contentAlignment = Alignment.Center
        ) {
            AppText(text = text, color = textColor)
        }
    }
}

@Composable
fun appGradient() = if (isSystemInDarkTheme()) {
    Brush.horizontalGradient(
        listOf(
            Color.DarkGray,
            Color.Black
        )
    )
} else Brush.horizontalGradient(
    listOf(
        Color.Gray,
        Color.DarkGray
    )
)

