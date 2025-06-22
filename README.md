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
Travel_api_Sprint1_CLI/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/keyin/
│   │           ├── domain/         # Domain models (City, Airport, Passenger, Aircraft)
│   │           └── http/
│   │               ├── cli/        # CLI logic (HTTPRestCLIApplication)
│   │               └── client/     # REST API client logic (RESTClient)
│   └── test/
│       └── java/
│           └── com/keyin/
│               ├── cli/           # Unit tests for CLI logic (HTTPRestCLIApplicationTest)
│               └── client/        # Unit tests for REST client (RESTClientTest)
├── pom.xml
└── README.md
```

---

##  Testing

The project includes unit tests for both the CLI and the REST client logic.

### HTTPRestCLIApplicationTest

Tests the core user-facing logic by mocking the RESTClient:

- Successful report generation for all options.
- Empty result edge cases.
- Error handling (e.g., not found, server errors).
- Assertions include: `assertTrue`, `assertFalse`, `assertEquals`, `assertNotNull`, and `assertThrows`.

### RESTClientTest

Tests raw HTTP response handling using mocked responses:

- Validates JSON deserialization.
- Simulates different HTTP codes.
- Covers edge cases like 404 errors.

---

