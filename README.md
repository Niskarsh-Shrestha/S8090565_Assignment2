# FoodApp

A simple and interactive app that helps users to discover details about various food items such as Dish name, origin, main ingredients, meal type, and description of the dish. It has various food items listed in dashbaord page which can be accessed by clicking on the Dish name and it will show all the details in Details page. This app is for the food lovers who are interested in trying variety of food from different countries. 

---

## Table of Contents
- [Features](#features)
- [Screenshots](#screenshots)
- [Installation](#installation)
- [Technologies](#technologies)
- [Project Structure](#project-structure)
- [API Reference](#api-reference)
- [Usage](#usage)
  

---

##  Features
- Displays detailed information about various international dishes.
- Includes dish name, country of origin, main ingredient, and meal type.
- Shows a description of each dish, including preparation and cultural notes.
- Easily extendable to add more dishes or food categories.
- Displays a total count of dishes available.
- Helps users discover food based on country or meal type.

---

## Screenshots

-Login page
![image](https://github.com/user-attachments/assets/ce1c4fd0-7d6e-4d82-9cf9-29ef9844bacc)

-Dashboard Page
![image](https://github.com/user-attachments/assets/3a7b0a66-bda1-4434-a128-8e38afa09f13)

-Details Page
![image](https://github.com/user-attachments/assets/798067ea-5c6c-450a-8b8c-c3ea4c339cd4)


---

## ⚙️ Installation

### Cloning the project in Android Studio

1. Open **Android Studio**.

2. On the welcome screen, click **Get from Version Control**.

3. Paste this URL in the **URL** field:  
https://github.com/Niskarsh-Shrestha/S8090565_Assignment2.git

markdown
Copy
Edit

4. Choose the directory where you want to save the project locally.

5. Click **Clone**.

6. After cloning, Android Studio will open the project and sync Gradle automatically.

7. Once syncing is complete, run the app on a connected device or emulator.

## My Login Credentials

To access the dashboard and data, use the following credentials on the login screen:

- **Username (First Name):** Niskarsh
 
- **Password (Student ID):** s8090565

## Technologies Used

- Kotlin
- XML (for layouts)
- Android Studio
- Gradle
- Hilt (for Dependency Injection)

## Project Structure

S8090565Assignment2/
|- build.gradle.kts
|- gradle.properties
|- gradlew
|- gradlew.bat
|- local.properties
|- settings.gradle.kts
|- app/
| |- build.gradle.kts
| |- proguard-rules.pro
| |- src/
| |- main/
| |- AndroidManifest.xml
| |- java/
| | |- com/
| | |- example/
| | |- s8090565assignment2/
| | |- DashboardActivity.kt
| | |- DetailActivity.kt
| | |- Dish.kt
| | |- DishAdapter.kt
| | |- item_dish.kt
| | |- MainActivity.kt
| | |- MyApp.kt
| |- res/
| |- drawable/
| | |- ic_launcher_background.xml
| | |- ic_launcher_foreground.xml
| |- layout/
| | |- activity_dashboard.xml
| | |- activity_detail.xml
| | |- activity_item_dish.xml
| | |- activity_main.xml
| |- values/
| |- colors.xml
| |- strings.xml
| |- themes.xml

## API Reference
-Authentication API
Endpoint:
POST https://nit3213api.onrender.com/sydney/auth

Used In:
MainActivity.kt

Description:
Authenticates the user using their first name and student ID.

Request Header:
Content-Type: application/json


Request Body:

{

  "username": "Niskarsh",
  
  "password": "s8090565"
  
}


Successful Response:

{

  "keypass": "food"
  
}

Notes:

-If keypass is returned, user is navigated to the Dashboard.

-If keypass is missing or response is invalid, an error message is shown.

-Dashboard Data API

Endpoint:

GET https://nit3213api.onrender.com/dashboard/{topic}?firstName={firstName}&studentID={studentID}

Example:

https://nit3213api.onrender.com/dashboard/food?firstName=John&studentID=S1234567

Used In:
DashboardActivity.kt

Description:
Retrieves a list of entities (dishes or items) assigned to the user’s topic.

Successful Response:
{
    "entities": [
        {
            "dishName": "Sushi",
            "origin": "Japan",
            "mainIngredient": "Rice",
            "mealType": "Lunch/Dinner",
            "description": "A Japanese dish of prepared vinegared rice accompanied by various ingredients such as seafood and vegetables."
        },
        {
            "dishName": "Pizza",
            "origin": "Italy",
            "mainIngredient": "Dough",
            "mealType": "Lunch/Dinner",
            "description": "A savory dish consisting of a round, flattened base of leavened wheat-based dough topped with various ingredients."
        },
        {
            "dishName": "Tacos",
            "origin": "Mexico",
            "mainIngredient": "Tortilla",
            "mealType": "Lunch/Dinner",
            "description": "A traditional Mexican dish consisting of a folded or rolled tortilla filled with various mixtures, such as seasoned meat, beans, or vegetables."
        },
        {
            "dishName": "Croissant",
            "origin": "France",
            "mainIngredient": "Butter",
            "mealType": "Breakfast",
            "description": "A buttery, flaky, viennoiserie pastry named for its historical crescent shape."
        },
        {
            "dishName": "Pad Thai",
            "origin": "Thailand",
            "mainIngredient": "Rice noodles",
            "mealType": "Lunch/Dinner",
            "description": "A stir-fried rice noodle dish commonly served as a street food and at casual local eateries in Thailand."
        },
        {
            "dishName": "Hamburger",
            "origin": "United States",
            "mainIngredient": "Ground beef",
            "mealType": "Lunch/Dinner",
            "description": "A sandwich consisting of one or more cooked patties of ground meat, usually beef, placed inside a sliced bread roll or bun."
        },
        {
            "dishName": "Curry",
            "origin": "India",
            "mainIngredient": "Spices",
            "mealType": "Lunch/Dinner",
            "description": "A variety of dishes originating in the Indian subcontinent that use a complex combination of spices or herbs, usually including ground turmeric, cumin, coriander, ginger, and fresh or dried chilies."
        }
    ],
    "entityTotal": 7
}

## Usage
Login

-Launch the app.

-Enter your First Name (Niskarsh) and Student ID (s8090565) in the text fields.

-Tap the Login button to authenticate via the Students API.

Dashboard

-After successful login, you'll be navigated to the Dashboard screen.

-This screen displays a list of items (dishes) fetched from the API, based on your assigned topic.

View Details

-Tap on any item in the list to open its Detail page.

-The detail view shows all available information about the selected item.

Navigation

-Use the Back button to return to the Dashboard from the Detail screen.

-Tap the Logout button on the Dashboard to return to the Login screen.

## Requirements / Prerequisites
Before running or installing the app, ensure you have the following:

-Software
Android Studio (Recommended: Latest version)

JDK 11 or higher (used via JavaVersion.VERSION_11)

Gradle (Handled automatically by Android Studio)

-Android SDK
Compile SDK Version: 35

Minimum SDK Version: 28

Target SDK Version: 35

-Build Tools & Dependencies
Kotlin JVM Target: 11

Dagger Hilt: 2.48

OkHttp: 4.12.0

org.json: 20231013

AndroidX Libraries: Core KTX, AppCompat, Activity, ConstraintLayout

Material Components

## Configuration
No additional configuration is required to run this app.
The app uses a predefined API to fetch student data and does not require any environment variables or API keys.

Note: Ensure your device/emulator has internet access to fetch data from the API successfully.

## Known Issues / Limitations
-Network connectivity is required to fetch data from the API — there is no offline mode.

-Error handling for API failures (e.g., no internet, server errors) is minimal.

-No input validation is implemented for the login fields.

-The UI is optimized for basic screen sizes and may not scale well on tablets or foldables.

-No unit or UI testing has been added yet.

## Contributing

Contributions are welcome! If you’d like to improve this project, follow these steps:

-Fork the repository.

-Create a new branch:
git checkout -b feature/Niskarsh-Shrestha  

Make your changes and commit:
git commit -m "Add your message here"  

Push to your fork:
git push origin feature/Niskarsh-Shrestha

Submit a pull request.

Please make sure your code follows clean coding practices and includes clear comments when necessary.

## Contact

If you have any questions, suggestions, or issues, feel free to reach out:

Student name: Niskarsh Shrestha

Student ID: s8090565

📧 Email: niskarshshrestha@gmail.com/
niskarsh.shrestha@live.vu.edu.au/
s8090565@live.vu.edu.au

🐛 Report Issues: GitHub Issues

I'm happy to help or hear your feedback!
