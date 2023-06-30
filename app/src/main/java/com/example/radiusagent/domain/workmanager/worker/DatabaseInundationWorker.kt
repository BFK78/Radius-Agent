package com.example.radiusagent.domain.workmanager.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.radiusagent.domain.repository.FacilitiesRepository
import com.example.radiusagent.domain.workmanager.util.WorkUtil.WORKER_FAILURE
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class DatabaseInundationWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val facilitiesRepository: FacilitiesRepository
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        return try {
            facilitiesRepository.fetchFacilitiesAndUpdateDatabase()
            Result.success()
        } catch (exception: Exception) {
            Log.e("DatabaseWorker", "An exception occurred inside doWork().Reason: $exception")
            Result.failure(
                workDataOf(
                    WORKER_FAILURE to "An exception occurred, $exception"
                )
            )
        }
    }
}