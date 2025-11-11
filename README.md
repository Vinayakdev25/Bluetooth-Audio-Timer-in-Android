Aravind's Timer - Bluetooth headset usage tracker (Enhanced)
------------------------------------------------------------

This is a minimal Android Studio / Gradle Kotlin project that:
- Tracks Bluetooth headset connect/disconnect events
- Logs session durations into Room database
- Provides a foreground service to keep listening in background
- Sends notifications when a session ends

How to build (using Appcircle / Codemagic / local Android Studio):
1. Upload this project ZIP to Appcircle or Codemagic, or open in Android Studio.
2. Build -> Generate Signed / Debug APK.
3. Install the APK on your device (allow install from unknown sources).

Notes:
- The Bluetooth permissions (BLUETOOTH_CONNECT) require runtime grant on Android 12+.
- You should start the foreground service from the app once to register the receiver in background.
- This project is intentionally minimal; adjust UI/UX and notifications as desired.
