package be.howest.eliasosaer.fittrackpro.data.repository

import android.content.Context
import android.hardware.camera2.CameraManager
import be.howest.eliasosaer.fittrackpro.domain.repository.FlashlightRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject

class FlashlightRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : FlashlightRepository {

    private val cameraManager =
        context.getSystemService(Context.CAMERA_SERVICE) as CameraManager

    private val cameraId: String? =
        cameraManager.cameraIdList.firstOrNull()

    override fun setFlashlight(enabled: Boolean) {
        cameraId?.let {
            try {
                cameraManager.setTorchMode(it, enabled)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}