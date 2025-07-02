package com.example.takecare

import android.content.Context
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object ReminderScheduler {
    fun schedule(context: Context, medication: Medication) {
        val data = Data.Builder()
            .putString("name", medication.name)
            .build()
        val request = PeriodicWorkRequestBuilder<ReminderWorker>(24, TimeUnit.HOURS)
            .setInputData(data)
            .build()
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "med_${medication.id}",
            ExistingPeriodicWorkPolicy.UPDATE,
            request
        )
    }

    fun cancel(context: Context, medication: Medication) {
        WorkManager.getInstance(context).cancelUniqueWork("med_${medication.id}")
    }
}
