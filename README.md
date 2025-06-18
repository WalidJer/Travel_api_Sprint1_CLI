# ✈ Travel Info CLI

## Overview

**Travel Info CLI** is a Java-based command-line application that interacts with a RESTful API to provide users with real-time travel information. The system handles four key entities: **Cities**, **Passengers**, **Airports**, and **Aircraft**. This client application allows users to query and explore how these entities are related via a simple, interactive menu-driven interface.

---

## Features

- Query **airports by city**
- Retrieve **aircraft flown by a passenger**
- Show **airports used by a specific aircraft**
- List **airports visited by a passenger**


---

## Requirements

- Java 11 or higher
- Maven
- A running backend API (e.g., Spring Boot application) hosted at `http://localhost:8080`

---

## Backend API Requirements

Your RESTful API must support the following endpoints:

- `GET /cities/{id}/airports`
- `GET /passengers/{id}/aircrafts`
- `GET /aircrafts/{id}/airports`
- `GET /passengers/{id}/airports`
- `GET /cities/{id}`
- `GET /passengers/{id}`

---

## Usage

Once launched, the CLI presents a user-friendly interface with 5 options:

```
╔════════════════════════════════════╗
║        Travel Info CLI             ║
╚════════════════════════════════════╝

1. List airports in a city
2. List aircraft flown by a passenger
3. List airports used by an aircraft
4. List airports used by a passenger
5. Exit
```

You will be prompted for an ID depending on the option selected. The application supports repeated queries (e.g., check multiple passengers or cities) before returning to the main menu.

---

## Example Interaction

```
Choose an option (1-5): 2
Enter Passenger ID: 2

Aircraft flown by Emily Johnson:
-------------------------------------
• Boeing 777 Updated  (Airline: Air Canada Updated)
• Airbus A320  (Airline: WestJet)

Would you like to check another passenger? (y/n): y
Enter Passenger ID: 3

```

---



## Project Structure

```
TravelInfoCLI/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/keyin/
│       │       ├── domain/         # Domain models (City, Airport, etc.)
│       │       ├── cli/   # Main entry point (HTTPRestCLIApplication)
│       │       └── client/ # API interaction logic (RESTClient)
├── pom.xml
└── README.md
```

##  Testing

Manual testing is performed via the interactive CLI, and the API endpoints are assumed to be already tested separately via tools like Postman or unit tests in the backend project.

---

