package gottegame.olimp.app.plug.ui.menu

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import gottegame.olimp.app.R
import gottegame.olimp.app.plug.ui.AppButton
import gottegame.olimp.app.plug.ui.AppText
import gottegame.olimp.app.plug.ui.appGradient
import gottegame.olimp.app.plug.ui.navigation.Screen
import gottegame.olimp.app.plug.ui.viewmodel.MainViewModel

@Composable
fun MenuScreen(
    navController: NavHostController,
    viewModel: MainViewModel
) {
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
            text = stringResource(id = R.string.app_name),
            size = 36.sp,
        )
        AppText(
            modifier = Modifier.padding(top = 24.dp),
            text = "Choose category:"
        )
        CategoryList(
            navController,
            viewModel
        )
    }
}

@Composable
fun CategoryList(
    navController: NavHostController,
    mainViewModel: MainViewModel
) {
    val models = listOf(
        "Football",
        "Basketball",
        "Hockey"
    )
    LazyColumn(
        content = {
            items(models.size) { item ->
                Block(models[item]) {
                    mainViewModel.getCardInfo(it)
                    navController.navigate(
                        Screen.QuizScreen.route,
                    )
                }
            }
        },
        modifier = Modifier
            .padding(top = 32.dp)
            .fillMaxWidth()
            .height(200.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(vertical = 8.dp)
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Block(
    name: String,
    onClick: (String) -> Unit
) {
    AppButton(
        modifier = Modifier
            .width(140.dp)
            .height(40.dp)
            .background(appGradient()),
        text = name,
    ) {
        onClick.invoke(name)
    }
}
