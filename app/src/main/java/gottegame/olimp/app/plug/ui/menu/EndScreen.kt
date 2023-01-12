package gottegame.olimp.app.plug.ui.menu

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import gottegame.olimp.app.plug.ui.viewmodel.MainViewModel
import gottegame.olimp.app.R
import gottegame.olimp.app.plug.ui.AppButton
import gottegame.olimp.app.plug.ui.AppText
import gottegame.olimp.app.plug.ui.appGradient
import gottegame.olimp.app.plug.ui.navigation.Screen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun EndScreen(
    navHostController: NavHostController,
    viewModel: MainViewModel
) {
    val counter = viewModel.counter
    Image(
        painter = painterResource(id = R.drawable.sports),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppText(
            modifier = Modifier.padding(horizontal = 12.dp),
            text = "Congratulations!\nYour points are $counter",
            size = 36.sp,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 42.dp)
                .padding(vertical = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AppButton(
                modifier = Modifier
                    .width(100.dp)
                    .height(40.dp)
                    .background(appGradient()),
                text = stringResource(id = R.string.try_again),
            ) {
                viewModel.tryAgain()
                navHostController.navigate(Screen.QuizScreen.route)
            }
            AppButton(
                modifier = Modifier
                    .width(150.dp)
                    .height(40.dp)
                    .background(appGradient()),
                text = stringResource(id = R.string.go_back),
            ) {
                viewModel.resetData()
                navHostController.navigate(Screen.MenuScreen.route)
            }
        }
    }
}

