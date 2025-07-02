# Take Care

A simple offline medication reminder app built with Android Jetpack technologies.

## Features
- Add and edit medications with description, reason, daily dose and starting quantity.
- Capture a photo of the medication box using the device camera.
- Local storage using Room database; no internet access is required.
- Schedules notifications to remind you to take medications even if the phone is locked.
- Supports English and Hebrew languages.

## Build Instructions
1. Open the project in Android Studio Flamingo or newer.
2. Ensure you have the Android SDK installed (compileSdk 34).
3. Build and run the `app` module on your device or emulator.

Notifications are scheduled using WorkManager, so they will work even when the app is not in the foreground.
