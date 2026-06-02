package be.howest.eliasosaer.fittrackpro.data.repository

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import be.howest.eliasosaer.fittrackpro.domain.repository.MovementSensorRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.sqrt
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull

@Singleton
class MovementSensorRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : MovementSensorRepository {

    private val sensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    private val _movementLabel = MutableStateFlow<String?>(null)
    override val movementLabel: Flow<String> = _movementLabel.filterNotNull()

    private val sensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            if (event.sensor.type != Sensor.TYPE_ACCELEROMETER) return

            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            val magnitude = sqrt((x * x + y * y + z * z).toDouble()).toFloat()

            _movementLabel.value = when {
                magnitude < 10.5f -> "Still"
                magnitude < 13.0f -> "Walking"
                else -> "Running"
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit
    }

    override fun startListening() {
        accelerometer?.let {
            sensorManager.registerListener(
                sensorEventListener,
                it,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }

    override fun stopListening() {
        sensorManager.unregisterListener(sensorEventListener)
    }

    override fun isSensorAvailable(): Boolean = accelerometer != null
}