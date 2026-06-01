package be.howest.eliasosaer.fittrackpro.ui.editworkout

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DirectionsRun
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import be.howest.eliasosaer.fittrackpro.domain.model.WorkoutType

@Composable
fun EditWorkoutScreen(
    uiState: EditWorkoutUiState,
    onTitleChange: (String) -> Unit,
    onDistanceChange: (String) -> Unit,
    onCaloriesChange: (String) -> Unit,
    onSetsOrRepsChange: (String) -> Unit,
    onMuscleGroupsChange: (String) -> Unit,
    onRatingChange: (String) -> Unit,
    onNotesChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(uiState.saved) {
        if (uiState.saved) navigateBack()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = uiState.title,
                    onValueChange = onTitleChange,
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = uiState.titleError
                )

                Text(
                    text = uiState.formattedDateTime,
                    style = MaterialTheme.typography.bodyMedium
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    MetricInfoCard(
                        icon = { Icon(Icons.Outlined.Schedule, contentDescription = null) },
                        label = "Duration",
                        value = "${uiState.durationMinutes} min",
                        modifier = Modifier.weight(1f)
                    )

                    when (uiState.workoutType) {
                        WorkoutType.CARDIO -> {
                            MetricInputField(
                                label = "Distance (km)",
                                value = uiState.distanceKm,
                                onValueChange = onDistanceChange,
                                isError = uiState.distanceError,
                                icon = { Icon(Icons.Outlined.DirectionsRun, contentDescription = null) },
                                modifier = Modifier.weight(1f)
                            )
                        }

                        WorkoutType.STRENGTH -> {
                            MetricInfoCard(
                                icon = { Icon(Icons.Outlined.FitnessCenter, contentDescription = null) },
                                label = "Sets / reps",
                                value = uiState.setsOrReps,
                                modifier = Modifier.weight(1f)
                            )
                        }

                        null -> Unit
                    }

                    MetricInputField(
                        label = "Calories",
                        value = uiState.calories,
                        onValueChange = onCaloriesChange,
                        isError = uiState.caloriesError,
                        icon = { Icon(Icons.Outlined.LocalFireDepartment, contentDescription = null) },
                        modifier = Modifier.weight(1f)
                    )
                }

                Text(
                    text = "Details",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                when (uiState.workoutType) {
                    WorkoutType.CARDIO -> Unit

                    WorkoutType.STRENGTH -> {
                        OutlinedTextField(
                            value = uiState.muscleGroups,
                            onValueChange = onMuscleGroupsChange,
                            label = { Text("Muscle groups") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    null -> Unit
                }

                if (uiState.workoutType == WorkoutType.STRENGTH) {
                    OutlinedTextField(
                        value = uiState.setsOrReps,
                        onValueChange = onSetsOrRepsChange,
                        label = { Text("Sets / reps") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                OutlinedTextField(
                    value = uiState.rating,
                    onValueChange = onRatingChange,
                    label = { Text("Rating (0-5)") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = uiState.ratingError
                )

                OutlinedTextField(
                    value = uiState.notes,
                    onValueChange = onNotesChange,
                    label = { Text("Notes") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = onSaveClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save changes")
                }
            }
        }
    }
}

@Composable
private fun MetricInfoCard(
    label: String,
    value: String,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            icon()
            Text(text = label, style = MaterialTheme.typography.labelMedium)
            Text(text = value, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun MetricInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    icon: @Composable () -> Unit
) {
    Card(
        modifier = modifier,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            icon()
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                label = { Text(label) },
                modifier = Modifier.fillMaxWidth(),
                isError = isError,
                singleLine = true
            )
        }
    }
}