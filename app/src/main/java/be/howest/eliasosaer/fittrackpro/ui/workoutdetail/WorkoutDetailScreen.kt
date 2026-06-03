package be.howest.eliasosaer.fittrackpro.ui.workoutdetail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DirectionsRun
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import be.howest.eliasosaer.fittrackpro.domain.model.CardioDetails
import be.howest.eliasosaer.fittrackpro.domain.model.StrengthDetails
import be.howest.eliasosaer.fittrackpro.domain.model.Workout
import be.howest.eliasosaer.fittrackpro.domain.model.WorkoutType

@Composable
fun WorkoutDetailScreen(
    uiState: WorkoutDetailUiState,
    modifier: Modifier = Modifier
) {
    val workout = uiState.workout ?: return

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        WorkoutDetailCard(workout = workout)
    }
}

@Composable
private fun WorkoutDetailCard(
    workout: Workout,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = workout.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = workout.formattedDateTime,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                DetailMetric(
                    icon = { Icon(Icons.Outlined.Schedule, contentDescription = null) },
                    value = "${workout.durationMinutes} min"
                )

                MetricDivider()

                when (workout.type) {
                    WorkoutType.CARDIO -> {
                        val details = workout.details as CardioDetails
                        DetailMetric(
                            icon = { Icon(Icons.Outlined.DirectionsRun, contentDescription = null) },
                            value = "${details.distanceKm ?: 0.0} km"
                        )
                    }

                    WorkoutType.STRENGTH -> {
                        val details = workout.details as StrengthDetails
                        DetailMetric(
                            icon = { Icon(Icons.Outlined.FitnessCenter, contentDescription = null) },
                            value = details.setsOrReps ?: "-"
                        )
                    }
                }

                MetricDivider()

                val calories = when (val details = workout.details) {
                    is CardioDetails -> details.calories
                    is StrengthDetails -> details.calories
                }

                DetailMetric(
                    icon = { Icon(Icons.Outlined.LocalFireDepartment, contentDescription = null) },
                    value = "${calories ?: 0} kcal"
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Details",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            when (workout.type) {
                WorkoutType.CARDIO -> {
                    Text(
                        text = "Pace: ${formatPace(workout.durationMinutes, (workout.details as CardioDetails).distanceKm)}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                WorkoutType.STRENGTH -> {
                    val details = workout.details as StrengthDetails
                    Text(
                        text = "Muscle Groups: ${details.muscleGroups ?: "-"}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Rating: ${buildRatingStars(workout.rating ?: 0)}",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Notes: ${workout.notes ?: ""}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun DetailMetric(
    icon: @Composable () -> Unit,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        icon()
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun MetricDivider() {
    Box(
        modifier = Modifier
            .width(1.dp)
            .height(36.dp)
    ) {
        HorizontalDivider(
            modifier = Modifier.fillMaxSize()
        )
    }
}

private fun formatPace(durationMinutes: Int, distanceKm: Double?): String {
    val distance = distanceKm ?: return "-"
    if (distance <= 0.0) return "-"

    val secondsPerKm = (durationMinutes * 60.0) / distance
    val minutesPart = (secondsPerKm / 60).toInt()
    val secondsPart = (secondsPerKm % 60).toInt()

    return String.format("%d:%02d/km", minutesPart, secondsPart)
}

private fun buildRatingStars(rating: Int): String {
    val safeRating = rating.coerceIn(0, 5)
    return buildString {
        repeat(safeRating) { append("★") }
        repeat(5 - safeRating) { append("☆") }
    }
}
