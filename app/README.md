# Study Time Manager

This application was developed as part of the CP3406 Mobile App Development assignment at 
James Cook University. It is an Android application built using Kotlin and Jetpack Compose,
designed to assist users in managing and tracking their study sessions, task progress,
and language learning.

## Project Description

Study Time Manager provides a clean and functional user interface to support self-directed study.
It allows users to log study time, manage personal learning tasks, and monitor weekly progress.
Additionally, it features an English vocabulary learning tool that fetches random words and
definitions through a public API.

## Features

- Study Timer with start/stop functionality and live countdown
- Task Dashboard for managing study tasks
- Weekly progress chart displaying accumulated time
- Vocabulary card with random English word and definition
- Room database for storing study session history
- MVVM architecture with ViewModel and state management
- Navigation using Jetpack Navigation Component
- Manual refresh mechanism for word retrieval

## Technology Stack

- Language: Kotlin
- UI Framework: Jetpack Compose (Material 3)
- Architecture: MVVM
- Database: Room (SQLite)
- API Integration: Retrofit with Gson Converter
- Testing: JUnit 4, kotlinx-coroutines-test, MockK

## Build and Run Instructions

1. Clone the repository from GitHub.
2. Open the project in Android Studio (Giraffe or newer).
3. Ensure Gradle sync completes.
4. Run the application on an emulator or physical device (API 26+).
5. Use the 'Start Study' button to begin a session and observe real-time updates.
6. Navigate between Study, Dashboard, and Progress using the bottom navigation bar.

## Testing

Unit tests are implemented for key ViewModels, focusing on logic correctness and Room database
interaction via mocking. Due to the Compose-based UI, only partial UI tests are included.

## Known Limitations

- UI responsiveness is optimized only for phones, not tablets.
- Network requests for word data are non-cached and manually triggered.
- No user authentication or cloud sync.

## Author

This project was developed by Tianyang Zhang for CP3406 Assignment 2, Term 1, 2025.