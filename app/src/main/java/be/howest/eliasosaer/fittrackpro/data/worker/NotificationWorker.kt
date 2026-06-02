package be.howest.eliasosaer.fittrackpro.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class NotificationWorker (
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val orderId = inputData.getString("ORDER_ID") ?: return Result.failure()
        NotificationHelper.showOrderDeliveryNotification(applicationContext, orderId)
        return Result.success()
    }
}