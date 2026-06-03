package be.howest.eliasosaer.fittrackpro.ui.scheduleworkout

import android.content.Intent
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import be.howest.eliasosaer.fittrackpro.domain.model.WorkoutType
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleWorkoutScreen(
    uiState: ScheduleWorkoutUiState,
    events: Flow<ScheduleWorkoutEvent>,
    onRequestCameraPermission: () -> Unit,
    onTitleChange: (String) -> Unit,
    onWorkoutTypeMenuExpandedChange: (Boolean) -> Unit,
    onWorkoutTypeSelected: (WorkoutType) -> Unit,
    onDateClick: () -> Unit,
    onDismissDatePicker: () -> Unit,
    onDateSelected: (String) -> Unit,
    onTimeChange: (String) -> Unit,
    onDurationChange: (String) -> Unit,
    onImportQrClick: () -> Unit,
    onSaveClick: () -> Unit,
    onSavedHandled: () -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(uiState.saved) {
        if (uiState.saved) {
            onSavedHandled()
            navigateBack()
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { }

    LaunchedEffect(Unit) {
        onRequestCameraPermission()
        events.collect { event ->
            when (event) {
                ScheduleWorkoutEvent.OpenCamera -> {
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    cameraLauncher.launch(intent)
                }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Schedule Workout",
            style = MaterialTheme.typography.headlineSmall
        )

        OutlinedTextField(
            value = uiState.title,
            onValueChange = onTitleChange,
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.titleError,
            supportingText = {
                if (uiState.titleError) {
                    Text("Title is required")
                }
            }
        )

        ExposedDropdownMenuBox(
            expanded = uiState.showWorkoutTypeMenu,
            onExpandedChange = onWorkoutTypeMenuExpandedChange
        ) {
            OutlinedTextField(
                value = uiState.selectedWorkoutType?.name ?: "",
                onValueChange = {},
                readOnly = true,
                label = { Text("Workout type") },
                placeholder = { Text("Select workout type") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = uiState.showWorkoutTypeMenu
                    )
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                isError = uiState.workoutTypeError,
                supportingText = {
                    if (uiState.workoutTypeError) {
                        Text("Workout type is required")
                    }
                }
            )

            ExposedDropdownMenu(
                expanded = uiState.showWorkoutTypeMenu,
                onDismissRequest = { onWorkoutTypeMenuExpandedChange(false) }
            ) {
                WorkoutType.entries.forEach { type ->
                    DropdownMenuItem(
                        text = { Text(type.name.lowercase().replaceFirstChar { it.uppercase() }) },
                        onClick = { onWorkoutTypeSelected(type) }
                    )
                }
            }
        }

        OutlinedButton(
            onClick = onDateClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = if (uiState.date.isBlank()) "Select date" else "Date: ${uiState.date}"
            )
        }

        if (uiState.dateError) {
            Text(
                text = "Date is required",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        OutlinedTextField(
            value = uiState.time,
            onValueChange = onTimeChange,
            label = { Text("Time") },
            placeholder = { Text("e.g. 18:30") },
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.timeError,
            supportingText = {
                if (uiState.timeError) {
                    Text("Enter a valid time in HH:mm format")
                }
            }
        )

        OutlinedTextField(
            value = uiState.durationMinutes,
            onValueChange = onDurationChange,
            label = { Text("Duration (minutes)") },
            placeholder = { Text("e.g. 45") },
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.durationError,
            supportingText = {
                if (uiState.durationError) {
                    Text("Enter a valid duration greater than 0")
                }
            }
        )

        ImportQrSection(onImportQrClick = onImportQrClick)

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onSaveClick,
            modifier = Modifier.fillMaxWidth(),
            enabled = !uiState.isSaving
        ) {
            Text(if (uiState.isSaving) "Saving..." else "Save")
        }
    }

    if (uiState.showDatePicker) {
        val datePickerState = rememberDatePickerState()

        DatePickerDialog(
            onDismissRequest = onDismissDatePicker,
            confirmButton = {
                TextButton(
                    onClick = {
                        val millis = datePickerState.selectedDateMillis
                        if (millis != null) {
                            val formattedDate = SimpleDateFormat(
                                "dd/MM/yyyy",
                                Locale.getDefault()
                            ).format(Date(millis))
                            onDateSelected(formattedDate)
                        } else {
                            onDismissDatePicker()
                        }
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismissDatePicker) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@Composable
private fun ImportQrSection(
    onImportQrClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "OR",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onImportQrClick() },
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.CameraAlt,
                    contentDescription = "Import with QR scanner"
                )
                Text("Import with QR scanner")
            }
        }
    }
}