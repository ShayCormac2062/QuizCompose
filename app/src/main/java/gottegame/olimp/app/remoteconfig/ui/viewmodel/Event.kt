package gottegame.olimp.app.remoteconfig.ui.viewmodel

sealed class Event {

    data class SuccessEvent(
        val url: String
    ) : Event()

    object ErrorEvent : Event()

}
