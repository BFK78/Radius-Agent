package com.example.radiusagent.domain.workmanager

import android.content.Context
import android.util.Log
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.radiusagent.domain.workmanager.util.WorkUtil.DATABASE_WORKER
import com.example.radiusagent.domain.workmanager.worker.DatabaseInundationWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WorkMangerHandler @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val workManager = WorkManager.getInstance(context)

    private val databaseInundationWorker = PeriodicWorkRequestBuilder<DatabaseInundationWorker>(
        repeatInterval = 1,
        repeatIntervalTimeUnit = TimeUnit.DAYS
    )
        .build()

    suspend fun startDatabaseWorker(): PeriodicWorkRequest? = withContext(Dispatchers.IO) {
        try {

            workManager.enqueueUniquePeriodicWork(
                DATABASE_WORKER,
                ExistingPeriodicWorkPolicy.KEEP,
                databaseInundationWorker
            )

            databaseInundationWorker

        } catch (exception: Exception) {
            Log.e(
                "WorkMangerHandler",
                "An exception occurred inside startDatabaseWorker().Reason: $exception"
            )
            null
        }
    }
}