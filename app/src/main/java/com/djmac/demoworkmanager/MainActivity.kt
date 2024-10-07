package com.djmac.demoworkmanager

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.djmac.demoworkmanager.databinding.ActivityMainBinding

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
            startOneTimeRequestChainWork()
        }

    }


    private fun startOneTimeRequestChainWork(){

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(true)
            .build()

        val workRequestA = OneTimeWorkRequestBuilder<UploadWorkerA>()
            .setConstraints(constraints)
            .build()

        val workRequestB = OneTimeWorkRequestBuilder<UploadWorkerB>()
            .setConstraints(constraints)
            .build()

        val workRequestC = OneTimeWorkRequestBuilder<UploadWorkerC>()
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(applicationContext)
            .beginWith(workRequestA)
            .then(workRequestB)
            .then(workRequestC)
            .enqueue()

        Log.d(TAG, "One time work started")
        observeWorkRequestStatus(workRequestA)
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