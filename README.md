# FitTrack Pro

**FitTrack Pro** is a native Android application designed to help users track their workouts, monitor their activity and stay motivated through weekly fitness goals.

In addition to its fitness tracking functionality, the application features an integrated fitness shop where users can purchase equipment for home workouts. The application also explores several native Android capabilities, including background processing, messaging, location services, camera integration and sensor-based automation.

## Features

### Workout & Activity Tracking

- Create, manage and track workouts.
- Store past and upcoming workouts locally.
- Set weekly activity goals and monitor progress.
- Share workouts with friends through QR codes.

### Fitness Shop

- Browse available fitness equipment retrieved from an external API.
- Add products to a persistent shopping cart.
- Keep cart contents available across application restarts.
- Share purchases with friends using Android's sharing functionality.

### Order Processing & Notifications

- Publish order events to a message broker when a purchase is completed.
- Consume delivery events when an order is being processed for delivery.
- Schedule background work to notify users when their order is on its way.

### Location & Maps

- Display an interactive map using Mapbox.
- Request coarse and precise location permissions.
- Automatically center the map around the user's current location when permission is available.

### Sensor Integration

- Utilize the device's light and accelerometer sensors.
- Automatically activate the flashlight when the user is running in a poorly lit environment (below 50 lux).

### Camera & QR Codes

- Scan QR codes using CameraX.
- Generate QR codes containing workout information.
- Allow users to import workouts shared by friends.

## Technology Stack

| Technology      | Purpose                                       |
| --------------- | --------------------------------------------- |
| Kotlin          | Primary programming language                  |
| Jetpack Compose | Declarative UI development                    |
| Room            | Local database persistence                    |
| ViewModel       | UI-related state management                   |
| Retrofit        | HTTP requests to the fitness shop API         |
| WorkManager     | Background task scheduling                    |
| Mapbox          | Maps and location visualization               |
| CameraX         | Camera and QR-code functionality              |
| Android Sensors | Light and accelerometer data                  |
| Message Broker  | Asynchronous order and delivery communication |

## Architecture & Implementation

### Local Database

The application uses Room to persist important application data locally.

Three main data entities are used:

- **Workouts:** Stores past and upcoming workouts.
- **Shopping Cart:** Persists cart items, allowing users to retain their selections after closing the application.
- **Weekly Goals:** Stores the user's activity goals, which are used to monitor weekly progress.

### API Integration

Retrofit is used to communicate with an external API that provides the products displayed in the fitness shop.

This simulates a real-world shop where available products are retrieved from a remote service rather than being hardcoded into the application.

### Message Broker

The application implements asynchronous communication through a message broker.

Two main message flows are supported:

1. **OrderCreated:** Published when a user completes a purchase.
2. **OrderDelivery:** Consumed when a delivery event is received.

The incoming delivery event triggers background processing, allowing the application to notify the user that their order is on its way.

### Background Processing

WorkManager handles background tasks, particularly order-related notifications.

When a delivery message is received, background work is scheduled to process the notification.

### Android Intents

The application uses both implicit and explicit intents:

- **Implicit intent:** Allows users to share their purchases through other applications.
- **Explicit intent:** Used for application-specific notification interactions.

### Maps & Location

Mapbox provides the map interface and location visualization.

The application requests location permissions and uses the device's current location as the map's center point. A predefined fallback location is used when location permissions are unavailable.

### Sensor-Based Automation

The application combines light sensor and accelerometer readings to automate certain actions.

When the detected light level falls below 50 lux and the user is identified as running, the flashlight is automatically activated.

### Camera & QR Code Integration

CameraX provides the camera functionality used for QR-code scanning. (not functional)

## Project Structure

The application follows an Android application architecture with separate responsibilities for UI, application logic and data persistence.

Key technologies include Jetpack Compose for the UI, ViewModels for UI-related state management and Room for local persistence.

## Testing

The project includes both unit tests and instrumented tests to verify application functionality.

---

*Developed by Elias Osaer as an Android application development project.*
