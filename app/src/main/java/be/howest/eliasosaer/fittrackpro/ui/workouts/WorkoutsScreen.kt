package be.howest.eliasosaer.fittrackpro.ui.workouts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import be.howest.eliasosaer.fittrackpro.domain.model.Workout
import be.howest.eliasosaer.fittrackpro.domain.model.WorkoutDialog
import be.howest.eliasosaer.fittrackpro.domain.model.WorkoutFilter

@Composable
fun WorkoutsScreen(
    uiState: WorkoutsUiState,
    onFilterSelected: (WorkoutFilter) -> Unit,
    onWorkoutClick: (Workout) -> Unit,
    onReminderClick: (Workout) -> Unit,
    onQrClick: (Workout) -> Unit,
    onDismissDialog: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("workouts_screen")
    ) {

        FilterSection(
            selectedFilter = uiState.selectedFilter,
            onFilterSelected = onFilterSelected
        )

        Spacer(modifier = Modifier.height(16.dp))

        WorkoutList(
            modifier = Modifier.weight(1f),
            workouts = uiState.workouts,
            onWorkoutClick = onWorkoutClick,
            onReminderClick = onReminderClick,
            onQrClick = onQrClick
        )
    }

    WorkoutsDialogs(
        dialog = uiState.dialog,
        workouts = uiState.workouts,
        onDismiss = onDismissDialog
    )
}

@Composable
fun WorkoutList(
    workouts: List<Workout>,
    onWorkoutClick: (Workout) -> Unit,
    onReminderClick: (Workout) -> Unit,
    onQrClick: (Workout) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = workouts,
            key = { it.id }
        ) { workout ->
            WorkoutCard(
                workout = workout,
                onClick = {
                    onWorkoutClick(workout)
                },
                onReminderClick = {
                    onReminderClick(workout)
                },
                onQrClick = {
                    onQrClick(workout)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FilterSection(
    selectedFilter: WorkoutFilter,
    onFilterSelected: (WorkoutFilter) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Your workouts",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        SingleChoiceSegmentedButtonRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            FILTER_OPTIONS.forEachIndexed { index, option ->
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = FILTER_OPTIONS.size
                    ),
                    onClick = { onFilterSelected(option.first) },
                    selected = selectedFilter == option.first,
                    label = {
                        Text(text = option.second)
                    }
                )
            }
        }
    }
}

@Composable
private fun WorkoutCard(
    workout: Workout,
    onClick: () -> Unit,
    onReminderClick: () -> Unit,
    onQrClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Schedule,
                        contentDescription = null
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = workout.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = workout.formattedDateTime,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = formatDuration(workout.durationMinutes),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(
                    onClick = onReminderClick,
                    modifier = Modifier.testTag("reminder_button_${workout.id}")
                ) {
                    Icon(
                        imageVector = Icons.Filled.Notifications,
                        contentDescription = "Reminder"
                    )
                }

                IconButton(
                    onClick = onQrClick,
                    modifier = Modifier.testTag("qr_button_${workout.id}")
                ) {
                    Icon(
                        imageVector = Icons.Filled.QrCode2,
                        contentDescription = "QR code"
                    )
                }
            }
        }
    }
}

@Composable
fun WorkoutsDialogs(
    dialog: WorkoutDialog,
    workouts: List<Workout>,
    onDismiss: () -> Unit
) {
    when (dialog) {

        WorkoutDialog.None -> Unit

        is WorkoutDialog.Reminder -> {
            ReminderDialog(
                onDismiss = onDismiss
            )
        }

        is WorkoutDialog.Qr -> {

            val workout = workouts.firstOrNull {
                it.id == dialog.workoutId
            }

            QrOverlayDialog(
                workout = workout,
                onDismiss = onDismiss
            )
        }
    }
}

@Composable
private fun ReminderDialog(
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Workout reminder")
        },
        text = {
            Text("Temporary UI placeholder for scheduling a workout reminder.")
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("OK")
            }
        }
    )
}

@Composable
private fun QrOverlayDialog(
    workout: Workout?,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Workout QR")
        },
        text = {
            Text(
                text = workout?.let { "Temporary QR placeholder for ${it.title}." }
                    ?: "Temporary QR placeholder."
            )
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}

private fun formatDuration(minutes: Int): String = "$minutes min"

private val FILTER_OPTIONS = listOf(
    WorkoutFilter.UPCOMING to "Upcoming",
    WorkoutFilter.HISTORY to "History"
)