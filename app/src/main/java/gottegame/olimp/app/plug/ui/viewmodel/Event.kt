package gottegame.olimp.app.plug.ui.viewmodel

import gottegame.olimp.app.plug.domain.model.QuizModel

sealed class Event {

    object LoadingEvent : Event()

    data class SuccessEvent(
        val resId: Int,
        val quiz: List<QuizModel>?
    ) : Event()

}
