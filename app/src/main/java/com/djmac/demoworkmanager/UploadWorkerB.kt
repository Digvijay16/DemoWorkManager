package com.djmac.demoworkmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.djmac.demoworkmanager.MainActivity.Companion.TAG

class UploadWorkerB(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {
    override fun doWork(): Result {
        try {
            uploadData()
            return Result.success()
        } catch (e: Exception) {
            return Result.failure()
        }
    }

    private fun uploadData() {
        Log.d(TAG, "Uploading data to server for B...")
        Thread.sleep(20000)
        Log.d(TAG, "Data Uploaded Successfully for B")
    }
}