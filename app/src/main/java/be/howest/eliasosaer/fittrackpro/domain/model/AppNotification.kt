package be.howest.eliasosaer.fittrackpro.domain.model

data class AppNotification(
    val id: Long,
    val type: NotificationType,
    val title: String,
    val message: String,
    val timestampLabel: String,
    val isRead: Boolean = false
)