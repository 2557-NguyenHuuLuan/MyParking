# My Parking App 🚗📱

**My Parking App** is a smart parking management system that allows users to conveniently book parking spots through a mobile app. It also features an automatic check-in/out system using license plate recognition (YOLOv8) and parking card verification.

## 🌟 Features

- User-friendly mobile app for booking and managing parking slots
- Real-time parking status and availability
- Automated check-in/out using:
  - License plate recognition (YOLOv8)
  - Parking card verification
- Admin panel to manage users, vehicles, and parking zones
- Secure authentication and role-based access

---

## 🛠 Tech Stack

| Layer            | Technology             |
|------------------|------------------------|
| Backend API      | Java Spring Boot       |
| Mobile App       | Flutter / Dart         |
| Database         | Oracle Database        |
| AI Recognition   | Python + YOLOv8        |
| Communication    | RESTful APIs           |

---

## 🧱 System Architecture

```mermaid
graph TD
  subgraph Mobile App
    A[Flutter App]
  end

  subgraph Backend
    B[Spring Boot API]
    C[Oracle Database]
  end

  subgraph AI Service
    D[Python Service - YOLOv8]
  end

  A -->|REST API| B
  B -->|SQL| C
  D -->|POST license plate| B
  B -->|GET parking status| A
