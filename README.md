Health Care Management System — Complete Setup Guide
Technologies Used
Java 17
Spring Boot
Spring Security OAuth2
Keycloak
PostgreSQL
Docker & Docker Compose
Maven
Microservices Architecture
Project Architecture

Your application contains these services:

Service	Port
Keycloak	8080
Spring Boot Services	8082
PostgreSQL	5432 (inside docker)
PostgreSQL External Access	5433
Step 1 — Install Required Software

Install these softwares first:

1. Install Java 17

Download:

Oracle JDK 17
or OpenJDK 17

Verify:

java -version
2. Install Maven

Verify:

mvn -version
3. Install Docker Desktop

Verify:

docker --version
docker compose version
Step 2 — Project Folder Structure

Example structure:

health-management-system/
│
├── docker-compose.yml
├── .env
│
├── patient-service/
├── doctor-service/
├── clinic-service/
├── appointment-service/
├── encounter-service/
├── prescription-service/
├── vitals-service/
├── lab-orders-service/
Step 3 — Create Environment File

Create .env file in root folder.

.env
POSTGRES_DB_PASSWORD=bhagyashri@123
Step 4 — Docker Compose Setup

Create docker-compose.yml

docker-compose.yml
version: "3.8"

services:

postgres:
image: postgres:15.5
container_name: postgres-db
restart: always

    environment:
      POSTGRES_DB: healthinsurance
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: ${POSTGRES_DB_PASSWORD}

    ports:
      - "5433:5432"

    volumes:
      - postgres_data:/var/lib/postgresql/data

keycloak:
image: quay.io/keycloak/keycloak:24.0.0
container_name: keycloak-server
command: start-dev
restart: always

    environment:
      KEYCLOAK_ADMIN: admin
      KEYCLOAK_ADMIN_PASSWORD: admin
      KC_HTTP_ENABLED: "true"
      KC_HTTP_PORT: 8080
      KC_HOSTNAME: localhost
      KC_HOSTNAME_STRICT: "false"

    ports:
      - "8080:8080"

    depends_on:
      - postgres

volumes:
postgres_data:
Step 5 — Start Docker Containers

Open terminal in project root folder.

Run:

docker compose up -d
Step 6 — Verify Containers

Check running containers:

docker ps

Expected:

postgres-db
keycloak-server
Step 7 — Access PostgreSQL

Database details:

Property	Value
Host	localhost
Port	5433
Database	healthinsurance
Username	postgres
Password	bhagyashri@123

You can connect using:

pgAdmin
DBeaver
IntelliJ Database Tool
Step 8 — Access Keycloak

Open browser:

http://localhost:8080

Login credentials:

Username	Password
admin	admin
Step 9 — Create Realm in Keycloak

After login:

Create Realm
Click dropdown (top left)
Click "Create Realm"
Realm name:
healthcare-realm
Click Create
Step 10 — Create Client

Inside realm:

Create Client
Clients
Create Client
Client Details
Field	Value
Client ID	healthcare-client
Client Type	OpenID Connect
Authentication Flow	Enabled

Click Next

Client Settings
Setting	Value
Client Authentication	ON
Authorization	OFF
Standard Flow	ON
Direct Access Grants	ON

Save client.

Step 11 — Get Client Secret

After client creation:

Open client
Credentials tab
Copy Client Secret

Example:

5ZYhjRbvlFOyf6AK6gRlXNfOsGiIEyb0
Step 12 — Create Roles

Go to:

Realm Roles

Create these roles:

PATIENT
DOCTOR
ADMIN
Step 13 — Create Users

Go to:

Users → Create User

Example:

Field	Value
Username	patient1
Email	patient1@gmail.com
Enabled	true

Save.

Step 14 — Set Password

Inside user:

Credentials
Set Password

Example:

Password@123

Turn OFF:

Temporary Password

Save.

Step 15 — Assign Role

Inside user:

Role Mapping
Assign Role

Assign:

PATIENT
DOCTOR
ADMIN
Step 16 — Spring Boot Configuration
application.yml
server:
port: 8082

spring:
application:
name: patientservice

datasource:
url: jdbc:postgresql://localhost:5433/healthinsurance
username: postgres
password: bhagyashri@123
driver-class-name: org.postgresql.Driver

jpa:
hibernate:
ddl-auto: update

    show-sql: true

    properties:
      hibernate:
        format_sql: true
        dialect: org.hibernate.dialect.PostgreSQLDialect

security:
oauth2:
resourceserver:
jwt:
issuer-uri: http://localhost:8080/realms/healthcare-realm
Important Fix

You used:

jdbc:postgresql://localhost:5432/healthinsurance

But Docker exposes PostgreSQL on:

5433

Correct URL:

jdbc:postgresql://localhost:5433/healthinsurance
Step 17 — Maven Dependencies
pom.xml

Add:

<dependencies>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
    </dependency>

    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>

</dependencies>
Step 18 — Start Spring Boot Application

Run:

mvn clean install

Then:

mvn spring-boot:run

Application runs on:

http://localhost:8082
Step 19 — Generate Access Token from Keycloak

Use Postman.

API
POST http://localhost:8080/realms/healthcare-realm/protocol/openid-connect/token
Body → x-www-form-urlencoded
Key	Value
client_id	healthcare-client
client_secret	your-client-secret
username	patient1
password	Password@123
grant_type	password
Step 20 — Copy Access Token

Response:

{
"access_token": "eyJhbGc..."
}

Copy token.

Step 21 — Call APIs

Example:

GET Current User
GET http://localhost:8082/api/v1/users/me

Headers:

Authorization: Bearer your_access_token
Step 22 — Security Configuration
SecurityConfig.java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth

                .requestMatchers("/api/v1/clinic/**").permitAll()

                .requestMatchers("/api/v1/patients/**").hasRole("PATIENT")

                .requestMatchers("/api/v1/doctors/**").hasAnyRole("DOCTOR", "ADMIN")

                .requestMatchers("/api/v1/users/**").authenticated()

                .anyRequest().authenticated()
            )

            .oauth2ResourceServer(oauth2 ->
                oauth2.jwt(Customizer.withDefaults()));

        return http.build();
    }
}
Step 23 — Common Docker Commands
Start Containers
docker compose up -d
Stop Containers
docker compose down
Restart Containers
docker compose restart
View Logs
docker logs keycloak-server
docker logs postgres-db
Step 24 — Verify Setup
Check PostgreSQL
docker exec -it postgres-db psql -U postgres

Then:

\l

You should see:

healthinsurance
Step 25 — Common Errors & Solutions
Error 1 — Connection Refused

Cause:

PostgreSQL container not running

Solution:

docker compose up -d
Error 2 — Invalid JWT Issuer

Cause:

Wrong issuer-uri

Correct:

issuer-uri: http://localhost:8080/realms/healthcare-realm
Error 3 — 401 Unauthorized

Cause:

Token missing
Invalid token
Role missing

Solution:

Generate new token
Add Bearer token
Assign correct role in Keycloak
Error 4 — Database Authentication Failed

Cause:

Wrong password

Check:

POSTGRES_DB_PASSWORD
Complete Flow Summary
1. Start Docker
2. PostgreSQL starts
3. Keycloak starts
4. Create Realm
5. Create Client
6. Create Roles
7. Create Users
8. Generate Token
9. Start Spring Boot App
10. Call APIs
11. 
Port — 8082
Auth — Keycloak on port 8080, realm: healthcare-realm
Database — PostgreSQL on port 5432, database: healthinsurance
Containerized using Docker Compose
=============================================================================================================
Roles — PATIENT, DOCTOR, ADMIN

Complete Flow
Step 1 → User Registers on Keycloak
Step 2 → Patient creates Profile
Step 3 → Doctor creates Profile
Step 4 → Admin creates Clinic
Step 5 → Patient books Appointment
Step 6 → Doctor confirms Appointment
Step 7 → Doctor creates Encounter
Step 8 → Doctor records Vitals
Step 9 → Doctor writes Prescriptions
Step 10 → Doctor orders Lab Tests


MODULE 1 — USER SERVICE
Base URL — /api/v1/users

GET /api/v1/users/me
Auth — PATIENT, DOCTOR, ADMIN
Response —
json{
"success": true,
"message": "User fetched successfully",
"data": {
"id": "uuid-here",
"userName": "bhagyashri123",
"email": "bhagyashri@gmail.com",
"firstName": "Bhagyashri",
"lastName": "Shinde",
"contactNumber": "9172826757",
"bloodGroup": "O+",
"isActive": true,
"address": {
"street": "MG Road",
"city": "Pune",
"state": "Maharashtra",
"country": "India",
"zipCode": "411001"
},
"createdAt": "2025-01-01T10:00:00",
"updatedAt": "2025-01-01T10:00:00"
}
}

PUT /api/v1/users/me
Auth — PATIENT, DOCTOR, ADMIN
Request —
json{
"userName": "bhagyashri_updated",
"contactNumber": "9172826757",
"bloodGroup": "O+",
"address": {
"street": "FC Road",
"city": "Pune",
"state": "Maharashtra",
"country": "India",
"zipCode": "411004"
}
}
Response —
json{
"success": true,
"message": "User updated successfully",
"data": {
"id": "uuid-here",
"userName": "bhagyashri_updated",
"email": "bhagyashri@gmail.com",
"contactNumber": "9172826757",
"bloodGroup": "O+",
"isActive": true,
"address": {
"street": "FC Road",
"city": "Pune",
"state": "Maharashtra",
"country": "India",
"zipCode": "411004"
},
"updatedAt": "2025-01-02T10:00:00"
}
}

GET /api/v1/users
Auth — ADMIN only
Response —
json{
"success": true,
"message": "Users fetched successfully",
"data": [
{
"id": "uuid-1",
"userName": "bhagyashri123",
"email": "bhagyashri@gmail.com",
"firstName": "Bhagyashri",
"lastName": "Shinde",
"contactNumber": "9172826757",
"isActive": true,
"createdAt": "2025-01-01T10:00:00"
}
]
}

GET /api/v1/users/{id}
Auth — ADMIN only
Response —
json{
"success": true,
"message": "User fetched successfully",
"data": {
"id": "uuid-here",
"userName": "bhagyashri123",
"email": "bhagyashri@gmail.com",
"firstName": "Bhagyashri",
"lastName": "Shinde",
"contactNumber": "9172826757",
"isActive": true,
"address": {
"street": "MG Road",
"city": "Pune",
"state": "Maharashtra",
"country": "India",
"zipCode": "411001"
}
}
}

PATCH /api/v1/users/{id}/activate
Auth — ADMIN only
Response —
json{
"success": true,
"message": "User activated successfully",
"data": null
}

PATCH /api/v1/users/{id}/deactivate
Auth — ADMIN only
Response —
json{
"success": true,
"message": "User deactivated successfully",
"data": null
}


MODULE 2 — PATIENT SERVICE
Base URL — /api/v1/patients

POST /api/v1/patients/me
Auth — PATIENT only
(Creates or updates own profile — upsert)
Request —
json{
"userId": "uuid-from-keycloak",
"firstName": "Amit",
"lastName": "Patil",
"email": "amit.patil@gmail.com",
"dob": "1992-05-20",
"gender": "MALE",
"bloodGroup": "B+",
"contactNumber": "9123456780",
"address": {
"street": "FC Road",
"city": "Pune",
"state": "Maharashtra",
"country": "India",
"zipCode": "411004"
}
}
Response —
json{
"success": true,
"message": "Patient profile saved successfully",
"data": {
"id": 1,
"userId": "uuid-from-keycloak",
"firstName": "Amit",
"lastName": "Patil",
"email": "amit.patil@gmail.com",
"dob": "1992-05-20",
"gender": "MALE",
"bloodGroup": "B+",
"contactNumber": "9123456780",
"address": {
"street": "FC Road",
"city": "Pune",
"state": "Maharashtra",
"country": "India",
"zipCode": "411004"
},
"createdAt": "2025-01-01T10:00:00",
"updatedAt": "2025-01-01T10:00:00"
}
}

GET /api/v1/patients/me
Auth — PATIENT only
Response —
json{
"success": true,
"message": "Patient fetched successfully",
"data": {
"id": 1,
"userId": "uuid-here",
"firstName": "Amit",
"lastName": "Patil",
"email": "amit.patil@gmail.com",
"dob": "1992-05-20",
"gender": "MALE",
"bloodGroup": "B+",
"contactNumber": "9123456780",
"address": { "...": "..." },
"createdAt": "2025-01-01T10:00:00",
"updatedAt": "2025-01-01T10:00:00"
}
}

GET /api/v1/patients/{id}
Auth — DOCTOR, ADMIN
Response —
json{
"success": true,
"message": "Patient fetched successfully",
"data": {
"id": 1,
"firstName": "Amit",
"lastName": "Patil",
"email": "amit.patil@gmail.com",
"dob": "1992-05-20",
"gender": "MALE",
"bloodGroup": "B+",
"contactNumber": "9123456780",
"address": { "...": "..." }
}
}

GET /api/v1/patients
Auth — DOCTOR, ADMIN
Response —
json{
"success": true,
"message": "Patients fetched successfully",
"data": [
{
"id": 1,
"firstName": "Amit",
"lastName": "Patil",
"email": "amit.patil@gmail.com",
"gender": "MALE",
"bloodGroup": "B+"
}
]
}

GET /api/v1/patients/search?firstName=Amit&lastName=Patil
Auth — DOCTOR, ADMIN
Response —
json{
"success": true,
"message": "Patients fetched successfully",
"data": [
{
"id": 1,
"firstName": "Amit",
"lastName": "Patil",
"email": "amit.patil@gmail.com",
"gender": "MALE",
"bloodGroup": "B+"
}
]
}


MODULE 3 — DOCTOR SERVICE
Base URL — /api/v1/doctors

POST /api/v1/doctors
Auth — DOCTOR only
Request —
json{
"userId": "uuid-from-keycloak",
"firstName": "Vikram",
"lastName": "Patel",
"specialization": "Pediatrics",
"licenseNumber": "E56789",
"department": "Children",
"availabilityStatus": true,
"contactNumber": "9765432101",
"address": {
"street": "River Road",
"city": "Pune",
"state": "Maharashtra",
"country": "India",
"zipCode": "411001"
}
}
⚠️ licenseNumber — 1 uppercase letter + 5 digits. Example — E56789
Response —
json{
"success": true,
"message": "Doctor created successfully",
"data": {
"id": 4,
"userId": "uuid-from-keycloak",
"firstName": "Vikram",
"lastName": "Patel",
"specialization": "Pediatrics",
"licenseNumber": "E56789",
"department": "Children",
"availabilityStatus": true,
"contactNumber": "9765432101",
"address": { "...": "..." },
"createdAt": "2025-01-01T10:00:00",
"updatedAt": "2025-01-01T10:00:00"
}
}

GET /api/v1/doctors/{id}
Auth — DOCTOR, ADMIN
Response —
json{
"success": true,
"message": "Doctor fetched successfully",
"data": {
"id": 4,
"firstName": "Vikram",
"lastName": "Patel",
"specialization": "Pediatrics",
"licenseNumber": "E56789",
"availabilityStatus": true
}
}

GET /api/v1/doctors
Auth — ADMIN only
Response —
json{
"success": true,
"message": "Doctors fetched successfully",
"data": [ { "...": "..." } ]
}

GET /api/v1/doctors/available
Auth — ADMIN only
Response —
json{
"success": true,
"message": "Available doctors fetched successfully",
"data": [
{
"id": 4,
"firstName": "Vikram",
"availabilityStatus": true,
"specialization": "Pediatrics"
}
]
}

GET /api/v1/doctors/specialization/{specialization}
Auth — ADMIN only
Example — GET /api/v1/doctors/specialization/Pediatrics
Response —
json{
"success": true,
"message": "Doctors fetched successfully",
"data": [
{
"id": 4,
"firstName": "Vikram",
"specialization": "Pediatrics"
}
]
}

PUT /api/v1/doctors/{id}
Auth — DOCTOR, ADMIN
Request —
json{
"firstName": "Vikram",
"lastName": "Patel",
"specialization": "Cardiology",
"licenseNumber": "E56789",
"department": "Heart",
"availabilityStatus": true,
"contactNumber": "9765432101",
"address": { "...": "..." }
}
Response —
json{
"success": true,
"message": "Doctor updated successfully",
"data": {
"id": 4,
"specialization": "Cardiology",
"department": "Heart"
}
}

PATCH /api/v1/doctors/{id}/availability?available=false
Auth — DOCTOR, ADMIN
Response —
json{
"success": true,
"message": "Doctor availability updated successfully",
"data": {
"id": 4,
"availabilityStatus": false
}
}

DELETE /api/v1/doctors/{id}
Auth — ADMIN only
Response —
json{
"success": true,
"message": "Doctor deleted successfully",
"data": null
}


MODULE 4 — CLINIC SERVICE
Base URL — /api/v1/clinic

POST /api/v1/clinic
Auth — ADMIN only
Request —
json{
"clinicName": "Family Health Clinic",
"location": "Karve Nagar",
"contactEmail": "familyhealth@gmail.com",
"isActive": true,
"address": {
"street": "Karve Road 12",
"city": "Pune",
"state": "Maharashtra",
"country": "India",
"zipCode": "411052"
}
}
Response —
json{
"success": true,
"message": "Clinic created successfully",
"data": {
"id": 2,
"clinicName": "Family Health Clinic",
"location": "Karve Nagar",
"contactEmail": "familyhealth@gmail.com",
"isActive": true,
"address": { "...": "..." },
"createdAt": "2025-01-01T10:00:00",
"updatedAt": "2025-01-01T10:00:00"
}
}

GET /api/v1/clinic
Auth — Public (no login needed)
Response —
json{
"success": true,
"message": "Clinics fetched successfully",
"data": [ { "...": "..." } ]
}

GET /api/v1/clinic/{id}
Auth — Public
Response —
json{
"success": true,
"message": "Clinic fetched successfully",
"data": {
"id": 2,
"clinicName": "Family Health Clinic",
"location": "Karve Nagar",
"isActive": true
}
}

GET /api/v1/clinic/active
Auth — Public
Response —
json{
"success": true,
"message": "Active clinics fetched successfully",
"data": [
{
"id": 2,
"clinicName": "Family Health Clinic",
"isActive": true
}
]
}

PUT /api/v1/clinic/{id}
Auth — ADMIN only
Request —
json{
"clinicName": "Updated Clinic Name",
"location": "Kothrud",
"contactEmail": "updated@gmail.com",
"isActive": true,
"address": { "...": "..." }
}
Response —
json{
"success": true,
"message": "Clinic updated successfully",
"data": {
"id": 2,
"clinicName": "Updated Clinic Name",
"location": "Kothrud"
}
}

PATCH /api/v1/clinic/{id}/activate
Auth — ADMIN only
Response —
json{
"success": true,
"message": "Clinic activated successfully",
"data": {
"id": 2,
"isActive": true
}
}

PATCH /api/v1/clinic/{id}/deactivate
Auth — ADMIN only
Response —
json{
"success": true,
"message": "Clinic deactivated successfully",
"data": {
"id": 2,
"isActive": false
}
}

DELETE /api/v1/clinic/{id}
Auth — ADMIN only
Response —
json{
"success": true,
"message": "Clinic deleted successfully",
"data": null
}


MODULE 5 — APPOINTMENT SERVICE
Base URL — /api/v1/appointments

POST /api/v1/appointments
Auth — PATIENT only
Request —
json{
"patientId": 1,
"doctorId": 4,
"clinicId": 2,
"appointmentDate": "2026-04-10T11:30:00",
"status": "PENDING",
"reason": "Regular health checkup"
}
Response —
json{
"success": true,
"message": "Appointment created successfully",
"data": {
"id": 1,
"appointmentId": "uuid-auto-generated",
"patientId": 1,
"doctorId": 4,
"clinicId": 2,
"appointmentDate": "2026-04-10T11:30:00",
"status": "PENDING",
"reason": "Regular health checkup",
"createdAt": "2025-01-01T10:00:00",
"updatedAt": "2025-01-01T10:00:00"
}
}

GET /api/v1/appointments/{id}
Auth — PATIENT, DOCTOR, ADMIN
Response —
json{
"success": true,
"message": "Appointment fetched successfully",
"data": {
"id": 1,
"appointmentId": "uuid-here",
"patientId": 1,
"doctorId": 4,
"clinicId": 2,
"appointmentDate": "2026-04-10T11:30:00",
"status": "PENDING",
"reason": "Regular health checkup"
}
}

GET /api/v1/appointments
Auth — DOCTOR, ADMIN
Response —
json{
"success": true,
"message": "Appointments fetched successfully",
"data": [ { "...": "..." } ]
}

PUT /api/v1/appointments/{id}
Auth — DOCTOR, ADMIN
Request —
json{
"patientId": 1,
"doctorId": 4,
"clinicId": 2,
"appointmentDate": "2026-04-10T11:30:00",
"status": "CONFIRMED",
"reason": "Regular health checkup"
}
Response —
json{
"success": true,
"message": "Appointment updated successfully",
"data": {
"id": 1,
"appointmentId": "uuid-here",
"status": "CONFIRMED",
"updatedAt": "2025-01-02T10:00:00"
}
}

DELETE /api/v1/appointments/internal/{id}
Auth — Internal service only
Response —
json{
"success": true,
"message": "Appointment deleted successfully",
"data": null
}


MODULE 6 — ENCOUNTER SERVICE
Base URL — /api/v1/encounter

POST /api/v1/encounter
Auth — DOCTOR only
Request —
json{
"appointmentId": 1,
"diagnosis": "Viral fever and throat infection",
"notes": "Patient has been experiencing fever for 3 days"
}
Response —
json{
"success": true,
"message": "Encounter created successfully",
"data": {
"id": 1,
"encounterId": "uuid-auto-generated",
"appointmentId": 1,
"diagnosis": "Viral fever and throat infection",
"notes": "Patient has been experiencing fever for 3 days",
"encounterDate": "2026-04-10T11:30:00",
"createdAt": "2026-04-10T11:30:00",
"updatedAt": "2026-04-10T11:30:00"
}
}

GET /api/v1/encounter/{encounterId}
Auth — DOCTOR, PATIENT, ADMIN
Response —
json{
"success": true,
"message": "Encounter fetched successfully",
"data": {
"id": 1,
"encounterId": "uuid-here",
"appointmentId": 1,
"diagnosis": "Viral fever and throat infection",
"notes": "Patient has been experiencing fever for 3 days",
"encounterDate": "2026-04-10T11:30:00"
}
}

GET /api/v1/encounter/appointment/{appointmentId}
Auth — DOCTOR, PATIENT, ADMIN
Response —
json{
"success": true,
"message": "Encounter fetched successfully",
"data": {
"id": 1,
"encounterId": "uuid-here",
"appointmentId": 1,
"diagnosis": "Viral fever and throat infection",
"notes": "Patient has been experiencing fever for 3 days",
"encounterDate": "2026-04-10T11:30:00"
}
}

GET /api/v1/encounter
Auth — DOCTOR, ADMIN
Response —
json{
"success": true,
"message": "Encounters fetched successfully",
"data": [ { "...": "..." } ]
}

GET /api/v1/encounter/patient/{patientId}
Auth — DOCTOR, PATIENT, ADMIN
Response —
json{
"success": true,
"message": "Patient encounters fetched successfully",
"data": [
{
"id": 1,
"encounterId": "uuid-here",
"appointmentId": 1,
"diagnosis": "Viral fever",
"encounterDate": "2026-04-10T11:30:00"
}
]
}

PUT /api/v1/encounter/{id}
Auth — DOCTOR only
Request —
json{
"diagnosis": "Updated diagnosis — bacterial infection",
"notes": "Updated notes after lab results"
}
Response —
json{
"success": true,
"message": "Encounter updated successfully",
"data": {
"id": 1,
"diagnosis": "Updated diagnosis — bacterial infection",
"notes": "Updated notes after lab results",
"updatedAt": "2026-04-10T12:00:00"
}
}


MODULE 7 — PRESCRIPTION SERVICE
Base URL — /api/v1/prescription

POST /api/v1/prescription/{encounterId}
Auth — DOCTOR only
Request —
json{
"medicineName": "Paracetamol",
"dosage": "500mg",
"frequency": "Twice a day",
"duration": "5 days",
"instructions": "Take after food"
}
Response —
json{
"success": true,
"message": "Prescription created successfully",
"data": {
"id": 1,
"encounterId": 1,
"medicineName": "Paracetamol",
"dosage": "500mg",
"frequency": "Twice a day",
"duration": "5 days",
"instructions": "Take after food"
}
}

GET /api/v1/prescription/{id}
Auth — DOCTOR, PATIENT, ADMIN
Response —
json{
"success": true,
"message": "Prescription fetched successfully",
"data": {
"id": 1,
"encounterId": 1,
"medicineName": "Paracetamol",
"dosage": "500mg",
"frequency": "Twice a day",
"duration": "5 days",
"instructions": "Take after food"
}
}

GET /api/v1/prescription/encounter/{encounterId}
Auth — DOCTOR, PATIENT, ADMIN
Response —
json{
"success": true,
"message": "Prescriptions fetched successfully",
"data": [
{
"id": 1,
"encounterId": 1,
"medicineName": "Paracetamol",
"dosage": "500mg",
"frequency": "Twice a day",
"duration": "5 days",
"instructions": "Take after food"
},
{
"id": 2,
"encounterId": 1,
"medicineName": "Azithromycin",
"dosage": "250mg",
"frequency": "Once a day",
"duration": "3 days",
"instructions": "Take with water"
}
]
}

GET /api/v1/prescription
Auth — DOCTOR, ADMIN
Response —
json{
"success": true,
"message": "Prescriptions fetched successfully",
"data": [ { "...": "..." } ]
}

PUT /api/v1/prescription/{id}
Auth — DOCTOR only
Request —
json{
"medicineName": "Paracetamol",
"dosage": "650mg",
"frequency": "Three times a day",
"duration": "7 days",
"instructions": "Take after food with warm water"
}
Response —
json{
"success": true,
"message": "Prescription updated successfully",
"data": {
"id": 1,
"medicineName": "Paracetamol",
"dosage": "650mg",
"frequency": "Three times a day",
"duration": "7 days",
"instructions": "Take after food with warm water"
}
}

DELETE /api/v1/prescription/{id}
Auth — DOCTOR only
Response —
json{
"success": true,
"message": "Prescription deleted successfully",
"data": null
}


MODULE 8 — LAB ORDERS SERVICE
Base URL — /api/v1/lab_orders

POST /api/v1/lab_orders/{encounterId}
Auth — DOCTOR only
Request —
json{
"testName": "Complete Blood Count",
"testDescription": "Full blood count to check for infection and anaemia",
"labStatus": "ORDERED",
"result": ""
}
Response —
json{
"success": true,
"message": "Lab order created successfully",
"data": {
"id": 1,
"encounterId": 1,
"testName": "Complete Blood Count",
"testDescription": "Full blood count to check for infection and anaemia",
"labStatus": "ORDERED",
"result": ""
}
}

GET /api/v1/lab_orders/{id}
Auth — DOCTOR, PATIENT, ADMIN
Response —
json{
"success": true,
"message": "Lab order fetched successfully",
"data": {
"id": 1,
"encounterId": 1,
"testName": "Complete Blood Count",
"testDescription": "Full blood count",
"labStatus": "ORDERED",
"result": ""
}
}

GET /api/v1/lab_orders/encounter/{encounterId}
Auth — DOCTOR, PATIENT, ADMIN
Response —
json{
"success": true,
"message": "Lab orders fetched successfully",
"data": [
{
"id": 1,
"encounterId": 1,
"testName": "Complete Blood Count",
"labStatus": "ORDERED"
},
{
"id": 2,
"encounterId": 1,
"testName": "Chest X-Ray",
"labStatus": "IN_PROGRESS"
}
]
}

GET /api/v1/lab_orders
Auth — DOCTOR, ADMIN
Response —
json{
"success": true,
"message": "Lab orders fetched successfully",
"data": [ { "...": "..." } ]
}

PATCH /api/v1/lab_orders/{id}/status?status=IN_PROGRESS
Auth — DOCTOR, ADMIN
Response —
json{
"success": true,
"message": "Lab order status updated successfully",
"data": {
"id": 1,
"testName": "Complete Blood Count",
"labStatus": "IN_PROGRESS"
}
}

PATCH /api/v1/lab_orders/{id}/result?result=Haemoglobin normal, WBC elevated
Auth — DOCTOR, ADMIN
Response —
json{
"success": true,
"message": "Lab order result updated successfully",
"data": {
"id": 1,
"testName": "Complete Blood Count",
"labStatus": "COMPLETED",
"result": "Haemoglobin normal, WBC elevated"
}
}


MODULE 9 — VITALS SERVICE
Base URL — /api/v1/vitals

POST /api/v1/vitals/{encounterId}
Auth — DOCTOR only
Request —
json{
"height": 165.5,
"weight": 68.0,
"temperature": 101.2,
"pulseRate": 88,
"bloodPressure": "130/85"
}
Response —
json{
"success": true,
"message": "Vitals created successfully",
"data": {
"id": 1,
"encounterId": 1,
"height": 165.5,
"weight": 68.0,
"temperature": 101.2,
"pulseRate": 88,
"bloodPressure": "130/85"
}
}

GET /api/v1/vitals/{id}
Auth — DOCTOR, PATIENT, ADMIN
Response —
json{
"success": true,
"message": "Vitals fetched successfully",
"data": {
"id": 1,
"encounterId": 1,
"height": 165.5,
"weight": 68.0,
"temperature": 101.2,
"pulseRate": 88,
"bloodPressure": "130/85"
}
}

GET /api/v1/vitals/encounter/{encounterId}
Auth — DOCTOR, PATIENT, ADMIN
Response —
json{
"success": true,
"message": "Vitals fetched successfully",
"data": [
{
"id": 1,
"encounterId": 1,
"height": 165.5,
"weight": 68.0,
"temperature": 101.2,
"pulseRate": 88,
"bloodPressure": "130/85"
}
]
}

GET /api/v1/vitals
Auth — DOCTOR, ADMIN
Response —
json{
"success": true,
"message": "All vitals fetched successfully",
"data": [ { "...": "..." } ]
}

PUT /api/v1/vitals/{id}
Auth — DOCTOR only
Request —
json{
"height": 165.5,
"weight": 69.0,
"temperature": 99.0,
"pulseRate": 80,
"bloodPressure": "120/80"
}
Response —
json{
"success": true,
"message": "Vitals updated successfully",
"data": {
"id": 1,
"encounterId": 1,
"height": 165.5,
"weight": 69.0,
"temperature": 99.0,
"pulseRate": 80,
"bloodPressure": "120/80"
}
}


Key Rules Summary
EntityID TypeNoteUserUUIDAuto from KeycloakPatientLongReferences userId UUIDDoctorLongReferences userId UUIDClinicLongReferences userId UUIDAppointmentUUID + LongUUID auto-generated on persistEncounterUUID + LongUUID auto-generated on persistPrescriptionLongLinked to EncounterLab OrderLongLinked to EncounterVitalsLongLinked to Encounter
FieldFormatlicenseNumberE56789 — 1 uppercase + 5 digitscontactNumber10 digitsbloodPressure120/80Appointment StatusPENDING, CONFIRMED, COMPLETED, CANCELLEDLab StatusORDERED, IN_PROGRESS, COMPLETED