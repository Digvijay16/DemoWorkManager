package com.djmac.demoworkmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class UploadWorker(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {
    override fun doWork(): Result {
        TODO("Not yet implemented")
    }
}