package be.howest.eliasosaer.fittrackpro.ui.notifications

import androidx.lifecycle.ViewModel
import be.howest.eliasosaer.fittrackpro.domain.model.AppNotification
import be.howest.eliasosaer.fittrackpro.domain.model.NotificationType
import be.howest.eliasosaer.fittrackpro.ui.notification.NotificationsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class NotificationsViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(
        NotificationsUiState(
            notifications = listOf(
                AppNotification(
                    id = 1L,
                    type = NotificationType.WORKOUT_REMINDER,
                    title = "Workout starts in 30 min!",
                    message = "Evening Run at 18:30",
                    timestampLabel = "Today 18:00",
                    isRead = false
                ),
                AppNotification(
                    id = 2L,
                    type = NotificationType.ORDER_DELIVERY,
                    title = "Gear order ready for pickup",
                    message = "Yoga mat is ready for pickup",
                    timestampLabel = "Today 17:53",
                    isRead = false
                )
            )
        )
    )
    val uiState: StateFlow<NotificationsUiState> = _uiState.asStateFlow()

    fun markAllAsRead() {
        _uiState.update { currentState ->
            currentState.copy(
                notifications = currentState.notifications.map { it.copy(isRead = true) }
            )
        }
    }
}