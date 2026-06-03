package be.howest.eliasosaer.fittrackpro.ui.weeklygoal

import androidx.compose.foundation.text.KeyboardOptions
import be.howest.eliasosaer.fittrackpro.domain.model.GoalType
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeeklyGoalScreen(
    uiState: WeeklyGoalUiState,
    onGoalTypeSelected: (GoalType) -> Unit,
    onGoalValueChanged: (String) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("weekly_goal_screen")
            .padding(horizontal = 16.dp)
            .navigationBarsPadding()
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            state = listState,
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                CurrentWeeklyGoalCard(
                    goalType = uiState.currentGoalType,
                    goalValueInput = uiState.currentGoalValueInput,
                    weeklyGoalProgress = uiState.weeklyGoalProgress
                )
            }

            item {
                GoalSettingsCard(
                    selectedGoalType = uiState.currentGoalType,
                    goalValueInput = uiState.currentGoalValueInput,
                    errorMessage = uiState.errorMessage,
                    onGoalTypeSelected = onGoalTypeSelected,
                    onGoalValueChanged = onGoalValueChanged
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onSaveClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .testTag("save_weekly_goal_button"),
            enabled = !uiState.isSaving
        ) {
            Text(
                text = if (uiState.isSaving) "Saving..." else "Save",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun CurrentWeeklyGoalCard(
    goalType: GoalType,
    goalValueInput: String,
    weeklyGoalProgress: Float,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Current weekly goal",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = if (goalValueInput.isBlank()) {
                    "No goal set yet"
                } else {
                    "Goal: $goalValueInput ${goalType.displayUnit}"
                },
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Completion: ${(weeklyGoalProgress * 100).toInt()}%",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GoalSettingsCard(
    selectedGoalType: GoalType,
    goalValueInput: String,
    errorMessage: String?,
    onGoalTypeSelected: (GoalType) -> Unit,
    onGoalValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Track goal by",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            SingleChoiceSegmentedButtonRow(
                modifier = Modifier.fillMaxWidth()
            ) {
                GoalType.entries.forEachIndexed { index, goalType ->
                    SegmentedButton(
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = GoalType.entries.size
                        ),
                        selected = selectedGoalType == goalType,
                        onClick = { onGoalTypeSelected(goalType) },
                        label = { Text(goalType.displayLabel) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = goalValueInput,
                onValueChange = onGoalValueChanged,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Target") },
                placeholder = { Text("Enter ${selectedGoalType.displayLabel.lowercase()} goal") },
                singleLine = true,
                isError = errorMessage != null,
                supportingText = {
                    if (errorMessage != null) {
                        Text(text = errorMessage)
                    } else {
                        Text(text = "Unit: ${selectedGoalType.displayUnit}")
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                )
            )
        }
    }
}

private val GoalType.displayLabel: String
    get() = when (this) {
        GoalType.DISTANCE -> "Distance ran"
        GoalType.ACTIVITY -> "Activity"
        GoalType.CALORIES -> "Calories"
    }

private val GoalType.displayUnit: String
    get() = when (this) {
        GoalType.DISTANCE -> "km"
        GoalType.ACTIVITY -> "minutes"
        GoalType.CALORIES -> "kcal"
    }
