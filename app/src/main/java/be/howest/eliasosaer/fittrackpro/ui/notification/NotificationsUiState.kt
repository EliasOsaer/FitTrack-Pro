package be.howest.eliasosaer.fittrackpro.ui.notification

import be.howest.eliasosaer.fittrackpro.domain.model.AppNotification

data class NotificationsUiState(
    val notifications: List<AppNotification> = emptyList()
)