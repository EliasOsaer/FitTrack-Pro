# 🎬 Project Status — FitTrack Pro App
## Author
- Elias Osaer
# Project Summary
This app is made to help users track their daily activity and encourage them to keep sporting. Additionally the app offers a small shop where you can purchase items for at home fitness.
# Daily Status
## Monday, June 1st, 2026
- started from a project that was already half made (mainly UI)
- Added the full shopping cart functionality
- did the minimum requirement for the mapbox
- added an intent to share your purchase with friends
- made the app publish a message to the messagebroker when a user purchased something
## Tuesday, June 2nd, 2026
- made it possible for the app to consume a message (OrderDelivery)
- made the app send a notification (through backgroundworker) telling the user that his/her order is on the way (as a response to the incoming message from the messagebroker)
## Wednesday, June 3rd, 2026
- Added the weekly goal settings functionality
- Added a notifications screen (hardcoded data sadly)
- fixed some bugs and did a general code cleanup
## Thursday, June 4th, 2026
- more bug fixing
- tests
## Friday, June 5th, 2026
- Update the camera2 implementation to a cameraX implemenation
- added an automated flow based on sensor data
# Status Overview Technical Requirements
## Status Legend
| Status | Meaning            |     |
| ------ | ------------------ | --- |
| ✅      | Implemented        | ✅   |
| 💪     | Work in progress   | 💪  |
| ❌      | Not implemented    | ❌   |
| 🧪     | Testing            | 🧪  |
| ⚠️     | Problems / blocked | ⚠️  |
### Table

| Requirement                       | Covered? | Where                                              |
| --------------------------------- | -------- | -------------------------------------------------- |
| **Must Have (12/20)**             |          |                                                    |
| Jetpack Compose                   | ✅        | All screens                                        |
| Multi screen application (4+)     | ✅        | 14 screens                                         |
| Menu based navigation             | ✅        | Bottom nav                                         |
| Material Design + custom theme    | ✅        | Custom icons/theme implied                         |
| Android app architecture          | ✅        | Room/ViewModel implied                             |
| Room database                     | ✅        | Workouts, ShoppingCart                             |
| Retrofit                          | ✅        | Gear shop API                                      |
| WorkManager                       | ✅        | Order notification                                 |
| 2 intents                         | ✅        | 1 in Share with friends, 1 for notifications       |
| MessageBroker                     | ✅        | OrderCreated (publish) and OrderDelivery (consume) |
| GPS data + Mapbox                 | ✅        | Map screen                                         |
| Sensors (2)                       | ✅        | Light + accelerometer                              |
| Notifications                     | ✅        | Orders                                             |
| Testing                           | ✅        | /                                                  |
|                                   |          |                                                    |
| **Intermediate (14/20)**          |          |                                                    |
| Multiple notifications + channels | 💪       | Workout/order/zone                                 |
| MessageBroker publish/consume     | ✅        | OrderCreated (publish) and OrderDelivery (consume) |
| Geofencing                        | 💪       | Map Screen                                         |
| Auto actions on sensors           | ✅        | Flashlight auto mode                               |
| Camera                            | ✅        | Import a workout from a friend                     |
| Unit + instrumented tests         | ✅        | /                                                  |
|                                   |          |                                                    |
| **Experienced (16/20)**           |          |                                                    |
| Key vault                         | ❌        | Mapbox api token, any other tokens                 |
| MessageBroker filtering           | ❌        | Wherever possible                                  |
| GPS navigation                    | ❌        | Zone click → navigation                            |

# App Overview
Describe the implementation of the following topics.
## Screenshots
![](ReadmeImages/Screens/Home.png)
![](ReadmeImages/Screens/Notifications.png)
![](ReadmeImages/Screens/WeeklyGoal.png)
![](ReadmeImages/Screens/WorkoutDetail.png)
![](ReadmeImages/Screens/WorkoutEdit.png)
![](ReadmeImages/Screens/QuickWorkout.png)
![](ReadmeImages/Screens/WorkoutsUpcoming.png)
![](ReadmeImages/Screens/WorkoutsHistory.png)
![](ReadmeImages/Screens/WorkoutsQR.png)
![](ReadmeImages/Screens/Shop.png)
![](ReadmeImages/Screens/Cart.png)
![](ReadmeImages/Screens/Share.png)
![](ReadmeImages/Screens/ThankYou.png)
![](ReadmeImages/Screens/Map.png)
![](ReadmeImages/Screens/Sensors.png)
## Room Database
![](ReadmeImages/Database/CartItemsDB.png)
![](ReadmeImages/Database/WeeklyGoalDB.png)
![](ReadmeImages/Database/WorkoutsDB.png)
Describe:
- Cart table used to persist items even after the app is closed
- weekly goal table is used to check what goal the user has set for himself/herself (this is later checked against what he/she has already done that week)
- Workouts table (biggest table) is used to store all workouts past and future
## API Requests
![](ReadmeImages/API/Endpoint.png)
![](ReadmeImages/API/Data.png)
Describe:
- API is exclusively used by the shop screen to render the available items for purchase (this simulates a real api sending real items)
## MessageBroker
![](ReadmeImages/MessageBroker.png)
Describe:
- 1 publish (customer clicks on checkout)
- 1 consume (screenshot) when order is being delivered (to trigger notification)
## Intents
![](ReadmeImages/Intents.png)
Describe:
- 2 intents:
	- 1 share with friends (Implicit intent)
	- 1 intent used for notifications (Explicit Intent)
## WorkManager
Describe:
- used to handle notifications
- workmanager scheduled upon message received
## Notifications
Describe:
- Used to tell the user that his/her delivery is on its way
- activated by an incoming message from the broker
## Map
Describe:
- uses the mapbox api
- asks the user for access to coarse/precise location and sets the maps centerpoint to the userlocation (if no permission is given centerpoint is hardcoded)
## Sensor Data
Describe:
- Sensors used: light and movement
- if light level is less then 50 lux and the person is "running" the flashlight automatically gets activated
## CameraX
Describe:
- QR scanning implementation
- popup to open the camera (permission asked upon clicking the button)
- on an upcoming workout the qr icon reveals a qrcode that is supposed to share the details of that workout so others can import it
# Repositories
## Code Repository
- [Repo]([https://test.com](https://github.com/Howest-TI-Project-BnD/st-client-mobile-EliasOsaer))
## APK
- [Link to APK]