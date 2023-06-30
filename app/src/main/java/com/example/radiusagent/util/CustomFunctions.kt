package com.example.radiusagent.util

import androidx.lifecycle.Observer
import androidx.work.WorkInfo
import androidx.work.WorkManager
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.UUID
import kotlin.coroutines.resume


suspend fun workInfoResult(workManager: WorkManager, id: UUID): Boolean =
    suspendCancellableCoroutine { continuation ->
        var counter = 0
        val observer = Observer<WorkInfo> { workInfo ->

            if (workInfo.state == WorkInfo.State.ENQUEUED) {
                counter++
            }

            if (counter == 2) {
                continuation.resume(true)
            }

            if (workInfo.state == WorkInfo.State.SUCCEEDED) {
                continuation.resume(true)
            }

            if (workInfo.state == WorkInfo.State.FAILED)
                continuation.resume(false)
        }

        val liveData = workManager.getWorkInfoByIdLiveData(id)
        liveData.observeForever(observer)

        continuation.invokeOnCancellation {
            liveData.removeObserver(observer)
        }
    }
