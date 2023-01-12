package gottegame.olimp.app.plug.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gottegame.olimp.app.plug.domain.usecase.GetQuizListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: GetQuizListUseCase
): ViewModel() {

    private val _uiState: MutableStateFlow<Event> =
        MutableStateFlow(Event.LoadingEvent)
    val uiState: StateFlow<Event> = _uiState

    var counter = 0

    fun getCardInfo(category: String) {
        _uiState.value = Event.LoadingEvent
        viewModelScope.launch {
            val result = useCase(category)
            if (result.second?.isNotEmpty() == true) {
                _uiState.value = Event.SuccessEvent(
                    result.first,
                    result.second
                )
            }
        }
    }

    fun resetData() {
        _uiState.value = Event.LoadingEvent
        counter = 0
    }

    fun tryAgain() {
        counter = 0
    }

    fun submitRightAnswer(score: Int) {
        counter = score
    }


}
