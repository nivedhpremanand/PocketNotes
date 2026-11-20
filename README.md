<div align="center">

# Pocket Notes

### *Your notes. Simple. Secure. Always accessible.*

A lightweight native Android notes application built using **Java** and **SQLite**. Create, save, and manage personal notes through a clean, minimal interface. Notes are stored locally on your device and remain available even after closing and reopening the application.

</div>

---

## ✨ Features

- 📝 Create and save personal notes
- 💾 Local data storage using SQLite
- 🔄 Notes remain saved after restarting the app
- 🕒 Automatic timestamps for notes
- 🗑️ Delete notes with a long press
- 📱 Native Android application
- ⚡ Lightweight and simple interface
- 🔒 Offline functionality with no backend dependency

---

# 🛠️ Tech Stack

- Java
- Android SDK
- SQLite Database
- Gradle
- Android Studio-compatible project structure

---

# 🚀 Running Locally

## Prerequisites

Make sure the following are installed:

- Java JDK 17
- Android SDK
- Git
- Android device or emulator

---

## 1. Clone the Repository

```bash
git clone https://github.com/nivedhpremanand/PocketNotes.git
cd PocketNotes
```

---

## 2. Configure Android SDK

Make sure your Android SDK is installed and configured.

Create a `local.properties` file in the project root and specify your Android SDK location:

```properties
sdk.dir=C:/Users/USERNAME/AppData/Local/Android/Sdk
```

Replace the path with your actual Android SDK location.

---

## 3. Build the Application

On Windows, run:

```powershell
.\gradlew.bat assembleDebug
```

---

## 4. Install the APK

The generated APK will be available at:

```text
app/build/outputs/apk/debug/app-debug.apk
```

Install it on a connected Android device using:

```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

---

# 📦 Build Configuration

| Configuration | Value |
|---|---|
| Language | Java |
| Build System | Gradle |
| Android Gradle Plugin | 8.7.3 |
| Gradle Version | 8.9 |
| Compile SDK | 35 |
| Minimum SDK | 23 |
| Target SDK | 35 |
| Database | SQLite |
| Application ID | com.example.pocketnotes |

---

# 📂 Project Structure

```text
PocketNotes/
│
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/example/pocketnotes/
│   │       │       └── MainActivity.java
│   │       └── AndroidManifest.xml
│   │
│   └── build.gradle
│
├── gradle/
│   └── wrapper/
│
├── build.gradle
├── gradle.properties
├── gradlew.bat
├── settings.gradle
└── .gitignore
```

---

# 🔐 Data Storage

- Notes are stored locally using SQLite.
- No external database or cloud server is required.
- The application works without an internet connection.
- Notes persist across application restarts.

---

# 🤝 Contributing

Contributions, bug reports, feature requests, and improvements are welcome.

If you'd like to contribute:

1. Fork the repository.
2. Create a new feature branch.
3. Commit your changes.
4. Push the branch.
5. Open a Pull Request.

---

# 📄 License

This project is intended for educational and personal use.

---

<div align="center">

### Built with simplicity in mind.

</div>
