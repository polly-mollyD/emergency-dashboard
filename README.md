# Emergency Dashboard

## Overview
The **Emergency Dashboard** is a Spring Boot-based application for managing emergency incidents. This README provides installation, setup, and usage instructions.

## Prerequisites
Ensure you have the following installed on your system:
- Java 11 or later
- Maven
- cURL (for API testing) or PowerShell (for Windows users)

## Installation and Setup

### 1. Build the Project
To build the project, run the following command:

```sh
mvn clean install

### 2. Start the Server
Run the following command to start the Spring Boot application:
```sh
mvn spring-boot:run

## API Endpoints
### 1. Get All Incidents
Retrieve a list of all reported incidents:
- URL: http://localhost:8080/api/incidents
- Command (cURL):
```sh
curl.exe -X GET "http://localhost:8080/api/incidents"

### 2. Post a New Incident
Submit a new emergency incident:
- URL: http://localhost:8080/api/incidents
- Command (Power Shell):
```sh
Invoke-RestMethod -Uri "http://localhost:8080/api/incidents" `
    -Method Post `
    -Headers @{"Content-Type"="application/json"} `
    -Body '{"incidentType":"Fire","severityLevel":"high","location":{"longitude":-122.4194,"latitude":37.7749}}'

## Web Interface
Access the application through the following URLs:
- Main Website: http://localhost:8080
- Incident List Page: http://localhost:8080/api/incidents











