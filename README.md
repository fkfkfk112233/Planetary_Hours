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

### Core Features

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

Current supported locations include:

- Taipei
- New Taipei
- Taoyuan
- Taichung
- Tainan
- Kaohsiung

More locations can be added in future versions.

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
