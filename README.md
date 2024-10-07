
# demo_wm_onetime_periodic

- Emulate OneTimeWorkRequest and PeriodicWorkRequest

# demo_wm_periodic_custom_delay

- Emulate periodic behavior, with the flexibility to adjust the delay between tasks.
- As per observation this will work as long as app is not killed


# demo_onetime_request_chain_work

- Emulate one time request with chain execution behaviour
- In this scenario work request begins with UploadWorkerA 
- Once it will get execute successfully , UploadWorkerB request begins
- Finally UploadWorkerB request will get execute
- It will execute in sequence which Work Manager will enqueue