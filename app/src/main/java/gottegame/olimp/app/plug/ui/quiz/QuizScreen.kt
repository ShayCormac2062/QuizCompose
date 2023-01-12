package gottegame.olimp.app.plug.ui.quiz

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import gottegame.olimp.app.plug.domain.model.QuizModel
import gottegame.olimp.app.plug.ui.AppButton
import gottegame.olimp.app.plug.ui.AppText
import gottegame.olimp.app.plug.ui.ProgressBar
import gottegame.olimp.app.plug.ui.navigation.Screen
import gottegame.olimp.app.plug.ui.viewmodel.Event
import gottegame.olimp.app.plug.ui.viewmodel.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

var score = 0

@Composable
fun QuizScreen(
    navController: NavHostController,
    viewModel: MainViewModel,
) {
    val quiz by viewModel.uiState.collectAsState()
    when (quiz) {
        is Event.LoadingEvent -> ProgressBar()
        else -> {
            Image(
                painter = painterResource(
                    id = (quiz as Event.SuccessEvent).resId
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Quiz(
                (quiz as Event.SuccessEvent).quiz,
                navController,
                viewModel
            )
        }
    }
}

@Composable
fun Quiz(
    quiz: List<QuizModel>?,
    navController: NavHostController,
    viewModel: MainViewModel
) {
    val counter = remember { mutableStateOf(0) }
    if (counter.value == quiz?.size) {
        viewModel.submitRightAnswer(score)
        score = 0
        counter.value = -1
        navController.navigate(Screen.EndScreen.route)
    } else if (counter.value >= 0) {
        val question = quiz?.get(counter.value)
        Question(question, counter)
    }
}

@Composable
fun Question(
    question: QuizModel?,
    counter: MutableState<Int>
) {
    var isButtonsEnabled by remember { mutableStateOf(true) }
    val isAnswerRight = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val onClick: (String) -> Unit = { answer ->
        isButtonsEnabled = false
        scope.launch {
            if (answer == question?.answerRight.toString()) {
                isAnswerRight.value = "Right"
                score += 1
            } else isAnswerRight.value = "Wrong"
            delay(1000)
            isButtonsEnabled = true
            isAnswerRight.value = ""
            counter.value++
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppText(
            modifier = Modifier.padding(bottom = 12.dp),
            text = question?.question.toString(),
            size = 36.sp,
        )
        Answer(question?.answerOne.toString(), onClick, isButtonsEnabled)
        Answer(question?.answerTwo.toString(), onClick, isButtonsEnabled)
        Answer(question?.answerThree.toString(), onClick, isButtonsEnabled)
        AppText(
            modifier = Modifier.padding(top = 12.dp),
            text = isAnswerRight.value,
            color = if (isAnswerRight.value == "Right") Color.Green else Color.Red,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Answer(
    answer: String,
    onClick: (String) -> Unit,
    enabled: Boolean
) {
    AppButton(
        modifier = Modifier
            .width(180.dp)
            .height(50.dp)
            .background(Color.Black),
        text = answer,
        enabled = enabled
    ) {
        onClick.invoke(answer)
    }
}
