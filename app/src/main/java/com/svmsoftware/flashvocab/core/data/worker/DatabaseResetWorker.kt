package com.svmsoftware.flashvocab.core.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.svmsoftware.flashvocab.core.data.local.DailyResetDatabase
import com.svmsoftware.flashvocab.core.domain.repository.DailyResetRepository
import com.svmsoftware.flashvocab.feature_notification.NotificationManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltWorker
class DatabaseResetWorker @AssistedInject constructor(
    private val repo: DailyResetRepository,
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters
) :
    Worker(appContext, params) {

    override fun doWork(): Result {
        println("doWork")
        repo.resetDatabase()
        return Result.success()
    }
}

fun scheduleDatabaseReset(context: Context) {
    val workRequest = PeriodicWorkRequestBuilder<DatabaseResetWorker>(24, TimeUnit.HOURS)
        .build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "clear daily notification limit",
        ExistingPeriodicWorkPolicy.KEEP,
        workRequest
    );
}