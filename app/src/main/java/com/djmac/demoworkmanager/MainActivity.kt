package com.djmac.demoworkmanager

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.djmac.demoworkmanager.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        val TAG = "DemoWorkManager"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnOneTime.setOnClickListener {
            startOneTimeWork()
        }

        binding.btnPeriodic.setOnClickListener {

            startPeriodicTimeRequest()
        }
    }

    private fun startPeriodicTimeRequest() {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        /*
        The minimum interval for periodic work is 15 minutes.
        If you specify a shorter interval,
        WorkManager will automatically adjust it to 15 minutes.
        */
        val uploadWorkRequest = PeriodicWorkRequestBuilder<UploadWorker>(
            15,
            TimeUnit.MINUTES
        ).setConstraints(constraints)
            .build()

        WorkManager.getInstance(applicationContext).enqueue(uploadWorkRequest)
        Log.d(TAG, "Periodic work started")
        observeWorkRequestStatus(uploadWorkRequest)
    }

    private fun startOneTimeWork(){

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(true)
            .build()

        val uploadWorkerRequest = OneTimeWorkRequestBuilder<UploadWorker>()
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(applicationContext).enqueue(uploadWorkerRequest)
        Log.d(TAG, "One time work started")
        observeWorkRequestStatus(uploadWorkerRequest)
    }

    private fun observeWorkRequestStatus(workRequest: WorkRequest) {

        WorkManager.getInstance(applicationContext).getWorkInfoByIdLiveData(workRequest.id)
            .observe(this) { workInfo ->
                if (workInfo != null) {
                    when (workInfo.state) {
                        WorkInfo.State.SUCCEEDED -> Log.d(TAG, "Work succeeded")
                        WorkInfo.State.FAILED -> Log.d(TAG, "Work failed")
                        WorkInfo.State.RUNNING -> Log.d(TAG, "Work is running")
                        WorkInfo.State.ENQUEUED -> Log.d(TAG, "Work is enqueued")
                        WorkInfo.State.CANCELLED -> Log.d(TAG, "Work is cancelled")
                        WorkInfo.State.BLOCKED -> Log.d(TAG, "Work is cancelled")
                    }
                }
            }

    }
}