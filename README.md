<div align="center">
  <img src="https://raw.githubusercontent.com/mehritik/WeatherApp/master/app/src/main/res/mipmap-xxxhdpi/ic_launcher_round.webp" alt="App Icon" width="150" height="150">
  <h1>WeatherApp</h1>
</div>

## Overview

The Weather Forecast Android App provides real-time weather updates based on the user's current location or a manually entered city name. The app fetches data from the WeatherAPI and displays essential weather information like temperature, humidity, weather conditions, wind speed, and UV index. The app is designed with a focus on simplicity, user-friendliness, and responsiveness.

## Features

- Fetches and displays weather data using the WeatherAPI.
- Automatic location detection using the device's GPS.
- Manual city entry for location-based weather information.
- Displays temperature, humidity, weather conditions, wind speed, and UV index.
- User-friendly interface with a refresh button for real-time updates.

## Screenshots

<table>
  <tr>
    <th>Welcome Screen</th>
    <th>Weather Data</th>
    <th>Manual City Entry</th>
  </tr>
  <tr>
    <td><img src="https://github.com/mehritik/WeatherApp/blob/master/app/src/main/Screenshot1.jpg" alt="Welcome Screen" width="200"/></td>
    <td><img src="https://github.com/mehritik/WeatherApp/blob/master/app/src/main/Screenshot2.jpg" alt="Weather Data" width="200"/></td>
    <td><img src="https://github.com/mehritik/WeatherApp/blob/master/app/src/main/Screenshot3.jpg" alt="Manual City Entry" width="200"/></td>
  </tr>
</table>


## Download

You can download the latest version of the Weather Forecast Android App from the link below:

[![Download APK](https://img.shields.io/badge/Download-APK-blue)](https://github.com/mehritik/WeatherApp/raw/master/app/app-debug.apk)



## Architecture

The app follows the Model-View-ViewModel (MVVM) architecture, ensuring a clear separation of concerns and easy testability.

### Components

1. **Model**: 
   - `WeatherResponse.java`: The model class that holds the weather data fetched from the API.
   - Retrofit and Gson are used to convert JSON responses to Java objects.

2. **View**: 
   - Activities and XML layouts that handle the UI presentation.
   - The main activity shows the current weather, a manual entry option, and a refresh button.

3. **ViewModel**: 
   - The ViewModel interacts with the Weather API using Retrofit, processes the data, and provides it to the view.

### Key Technologies

- **Retrofit**: For making HTTP requests to the WeatherAPI.
- **Gson**: For parsing JSON responses.
- **FusedLocationProviderClient**: For obtaining the user's current location.
- **MVVM Architecture**: For separation of concerns and better code management.

## Setup Instructions

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/mehritik/WeatherApp.git
   cd WeatherApp

2. **Open Android Studio**:

- Open Android Studio.
- Select "Open an existing project."
- Navigate to the cloned repository and select it.

3. **Build and Run**:
- Sync the project with Gradle files.
- Build the project by selecting "Build" -> "Make Project."
- Run the project on an emulator or a physical device.

## Assumptions & Limitations

**Assumptions**:

- The user has a stable internet connection to fetch the weather data.
- The user's device has location services enabled.

**Limitations**:

- The app only supports current weather data and does not provide forecasts.
- In the event of denied location permissions, the app defaults to manual city entry.
- API key usage is limited by the free tier provided by WeatherAPI. Exceeding the limit may result in errors.

## Future Improvements

- Add support for weather forecasts (e.g., hourly, daily).
- Implement dark mode for the UI.
- Add caching to minimize API calls and support offline mode.
- Enhance error handling and provide more descriptive messages to the user.

## Contributing
Contributions are welcome! Please fork the repository and create a pull request with your changes.

## License
This project is licensed under the MIT License. See the LICENSE file for details.
