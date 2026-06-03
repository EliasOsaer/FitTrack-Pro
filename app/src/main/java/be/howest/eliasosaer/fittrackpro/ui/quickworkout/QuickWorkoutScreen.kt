package be.howest.eliasosaer.fittrackpro.ui.quickworkout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.howest.eliasosaer.fittrackpro.domain.model.WorkoutType
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuickWorkoutScreen(
    uiState: QuickWorkoutUiState,
    onWorkoutStarted: () -> Unit,
    onTitleChange: (String) -> Unit,
    onWorkoutTypeSelected: (WorkoutType) -> Unit,
    onEndWorkoutClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var elapsedSeconds by remember(uiState.startTimeMillis) { mutableLongStateOf(0L) }

    LaunchedEffect(Unit) {
        onWorkoutStarted()
    }

    LaunchedEffect(uiState.startTimeMillis) {
        while (true) {
            elapsedSeconds = ((System.currentTimeMillis() - uiState.startTimeMillis) / 1000)
                .coerceAtLeast(0L)
            delay(1000)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = uiState.title,
                onValueChange = onTitleChange,
                label = { Text("Title") },
                placeholder = { Text("Quick Workout") },
                modifier = Modifier.fillMaxWidth()
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = if (uiState.selectedWorkoutType == WorkoutType.STRENGTH) {
                        "Strength"
                    } else {
                        "Cardio"
                    },
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Workout type") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Strength") },
                        onClick = {
                            onWorkoutTypeSelected(WorkoutType.STRENGTH)
                            expanded = false
                        }
                    )

                    DropdownMenuItem(
                        text = { Text("Cardio") },
                        onClick = {
                            onWorkoutTypeSelected(WorkoutType.CARDIO)
                            expanded = false
                        }
                    )
                }
            }
        }

        Text(
            text = formatChronometer(elapsedSeconds),
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.displayMedium
        )

        Button(
            onClick = { onEndWorkoutClick(elapsedSeconds) },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            )
        ) {
            Text("End Workout")
        }
    }
}

private fun formatChronometer(totalSeconds: Long): String {
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}
