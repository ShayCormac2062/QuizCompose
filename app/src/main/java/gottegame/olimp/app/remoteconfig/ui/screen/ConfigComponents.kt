package gottegame.olimp.app.remoteconfig.ui.screen

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gottegame.olimp.app.R
import gottegame.olimp.app.plug.ui.AppButton
import gottegame.olimp.app.plug.ui.AppText
import gottegame.olimp.app.plug.ui.appGradient

@Preview(showBackground = true)
@Composable
fun ConfigProgressBar() {
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(1500, easing = FastOutSlowInEasing)
        )
    )
    Image(
        painter = painterResource(id = R.drawable.sports),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.rotate(angle)) {
            Image(
                painter = painterResource(id = R.drawable.sports_icon),
                contentDescription = null,
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
            )
        }
        AppText(
            modifier = Modifier.padding(top = 12.dp),
            text = stringResource(id = R.string.loading),
            size = 20.sp
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ErrorMessage(
    onClick: () -> Unit,
) {
    Image(
        painter = painterResource(id = R.drawable.sports),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberVectorPainter(
                image = ImageVector.vectorResource(
                    id = R.drawable.error
                )
            ),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
        AppText(
            modifier = Modifier.padding(
                vertical = 8.dp,
                horizontal = 56.dp
            ),
            text = stringResource(R.string.no_internet_connection),
            size = 24.sp
        )
        AppButton(
            modifier = Modifier
                .width(140.dp)
                .height(40.dp)
                .background(appGradient()),
            text = stringResource(id = R.string.try_again),
            onClick = {
                onClick.invoke()
            }
        )
    }
}
