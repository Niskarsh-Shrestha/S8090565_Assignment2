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

  "username": "John",
  
  "password": "S1234567"
  
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

