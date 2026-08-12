# Planetary Hours

A Spring Boot web application for calculating and displaying the 24 Planetary Hours based on sunrise and sunset times.

> Version: **v1.0.0**
>
> Status: **Completed**

---

## 📖 Introduction

Planetary Hours is a traditional time system that divides the period between sunrise and sunset into 12 daytime planetary hours, and the period between sunset and the following sunrise into 12 nighttime planetary hours.

Unlike ordinary clock hours, a Planetary Hour does not necessarily last exactly 60 minutes.

The duration of each Planetary Hour is calculated by:

- Daytime duration ÷ 12
- Nighttime duration ÷ 12

This project calculates the 24 Planetary Hours for a selected date and location.

---

## ✨ Features

- Calculate 24 Planetary Hours
- Calculate sunrise and sunset times
- Separate Day Hours and Night Hours
- Determine the ruling planet of each hour
- Display the current Planetary Hour
- Support different locations in Taiwan
- Handle dates and times using Taiwan timezone (UTC+8)
- REST API
- Swagger / OpenAPI documentation
- Global exception handling
- Responsive frontend

### Location Support

- Taipei
- New Taipei
- Taoyuan
- Taichung
- Tainan
- Kaohsiung

---

## 🛠️ Tech Stack

### Backend

- Java
- Spring Boot
- Maven
- Spring Web
- REST API
- OpenAPI / Swagger

### Frontend

- HTML
- CSS
- JavaScript
- Fetch API

### Development Tools

- Eclipse
- Git
- GitHub
- Maven

---

## 🏗️ Project Architecture

The project follows a layered architecture:

```text
Frontend
    │
    │ fetch()
    ▼
Controller
    │
    ▼
Service
    │
    ├───────────────┐
    ▼               ▼
Provider        Calculator
    │               │
    ▼               ▼
Sun Data       Planetary Hours
    │               │
    └───────┬───────┘
            ▼
          Service
            │
            ▼
           DTO
            │
            ▼
        Controller
            │
          JSON
            │
            ▼
         Frontend
```

### Controller

Responsible for handling HTTP requests and responses.

It does not contain the main calculation logic.

### Service

Responsible for coordinating the application logic.

For example:

```text
Get sunrise
    ↓
Get sunset
    ↓
Get next day's sunrise
    ↓
Call PlanetaryHourCalculator
    ↓
Build response DTO
```

### Calculator

Responsible for the actual Planetary Hours calculation.

The calculator handles:

- Day ruler
- Chaldean planetary sequence
- Daytime Planetary Hours
- Nighttime Planetary Hours
- Hour start/end times

### Provider

`SunDataProvider` is an abstraction for obtaining sunrise and sunset data.

Current implementation:

```text
SunDataProvider
       ▲
       │
SunDataService
```

This allows the data source to be replaced in the future without changing the main service logic.

### DTO

DTOs define the data structure transferred between the backend and frontend.

### Global Exception Handler

The project uses `@RestControllerAdvice` to handle API exceptions centrally.

Errors are returned in a consistent format.

Example:

```json
{
  "status": 400,
  "message": "Invalid location"
}
```

---

## 🪐 Planetary Hour Calculation

The project uses the traditional Chaldean planetary sequence:

```text
Saturn
Jupiter
Mars
Sun
Venus
Mercury
Moon
```

The sequence repeats continuously.

The first Planetary Hour is determined by the ruling planet of the day.

Example:

```text
Day Ruler: Saturn

Hour 1 → Saturn
Hour 2 → Jupiter
Hour 3 → Mars
Hour 4 → Sun
Hour 5 → Venus
Hour 6 → Mercury
Hour 7 → Moon
Hour 8 → Saturn
...
```

---

## ☀️ Day and Night Calculation

The 24 hours are divided into:

```text
12 Day Hours
+
12 Night Hours
=
24 Planetary Hours
```

### Day

```text
Sunrise → Sunset
```

The duration is divided by 12.

### Night

```text
Sunset → Next Sunrise
```

The duration is divided by 12.

Therefore, a Planetary Hour may be longer or shorter than a normal 60-minute clock hour.

---

## 🌏 Timezone

The project currently uses:

```text
Asia/Taipei
UTC+8
```

This ensures sunrise, sunset, and the current Planetary Hour are evaluated using local Taiwan time.

---

## 🌐 REST API

### Calculate Planetary Hours

```http
GET /api/planetary-hours
```

### Parameters

| Parameter | Type | Required | Description |
|---|---|---|---|
| `date` | LocalDate | Yes | Date to calculate |
| `location` | Location | Yes | Calculation location |

### Example

```http
GET /api/planetary-hours?date=2026-08-13&location=TAIPEI
```

### Successful Response

```http
200 OK
```

Example:

```json
{
  "date": "2026-08-13",
  "location": "Taipei",
  "sunrise": "05:30:00",
  "sunset": "18:40:00",
  "hours": [
    {
      "hour": 1,
      "planet": "SATURN",
      "type": "DAY",
      "start": "05:30:00",
      "end": "06:35:50"
    }
  ]
}
```

---

## 📚 Swagger / OpenAPI

Swagger UI is available at:

```text
http://localhost:8080/swagger-ui/index.html
```

Swagger provides:

- API documentation
- Request parameter information
- Response structure
- Interactive API testing

---

## 🖥️ Frontend

The frontend is implemented using:

```text
HTML
CSS
JavaScript
Fetch API
```

The user can:

1. Select a date
2. Select a location
3. Click Calculate
4. Send a request to the Spring Boot API
5. Display sunrise and sunset
6. Display Day Hours
7. Display Night Hours
8. Highlight the current Planetary Hour

---

## 📂 Project Structure

```text
src
└── main
    ├── java
    │   └── com.example.Planetary_hours
    │       │
    │       ├── PlanetaryHoursApplication.java
    │       │
    │       ├── calculator
    │       │   ├── PlanetaryHourCalculator.java
    │       │   └── SunCalculator.java
    │       │
    │       ├── controller
    │       │   └── PlanetaryHourController.java
    │       │
    │       ├── dto
    │       │   ├── ErrorResponse.java
    │       │   ├── PlanetaryHourResponse.java
    │       │   ├── PlanetaryHoursResponse.java
    │       │   └── SunData.java
    │       │
    │       ├── exception
    │       │   └── GlobalExceptionHandler.java
    │       │
    │       ├── model
    │       │   ├── Location.java
    │       │   └── Planet.java
    │       │
    │       └── service
    │           ├── PlanetaryHourService.java
    │           ├── SunDataProvider.java
    │           └── SunDataService.java
    │
    └── resources
        ├── static
        │   ├── index.html
        │   ├── css
        │   │   └── style.css
        │   └── js
        │       └── app.js
        │
        └── application.properties
```

---

## 🚀 Getting Started

### Requirements

- JDK 17+
- Maven
- Git

### Clone the project

```bash
git clone <your-repository-url>
cd Planetary_Hours
```

### Run the application

```bash
mvn spring-boot:run
```

Or run `PlanetaryHoursApplication.java` from Eclipse.

### Open the application

```text
http://localhost:8080/
```

### Open Swagger UI

```text
http://localhost:8080/swagger-ui/index.html
```

---

## 🧪 Testing

Run:

```bash
mvn test
```

Expected result:

```text
Tests run: 1
Failures: 0
Errors: 0
Skipped: 0

BUILD SUCCESS
```

---

## 📌 Current Version

### v1.0.0

Completed features:

- [x] Spring Boot application
- [x] REST API
- [x] Sunrise calculation
- [x] Sunset calculation
- [x] Planetary Hour calculation
- [x] 24 Planetary Hours
- [x] Day / Night separation
- [x] Taiwan locations
- [x] Location selection
- [x] Current Planetary Hour
- [x] Taiwan timezone handling
- [x] Exception handling
- [x] Swagger / OpenAPI
- [x] Responsive frontend
- [x] Maven test

---

## 🔮 Future Improvements

### v2

- [ ] Add more locations
- [ ] Improve astronomy calculations
- [ ] Integrate an external astronomy API
- [ ] Add Planetary Hour details
- [ ] Add planet descriptions
- [ ] Add historical query records

### Future

- [ ] User accounts
- [ ] Save favorite locations
- [ ] Database integration
- [ ] Calendar view
- [ ] Mobile-friendly improvements
- [ ] Docker deployment
- [ ] Cloud deployment

---

## 📄 License

This project is currently intended as a personal learning and portfolio project.
