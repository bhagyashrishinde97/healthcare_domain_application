# healthcare_domain_application
#  Health Insurance Application

---

##  Project Overview

This is a Health Insurance Management System built using Spring Boot.

### Modules:

* User Service
* Patient Service
* Doctor Service
* Clinic Service
* Appointment Service
* Encounter Service
* Vitals Service
* Lab Orders Service
* Prescription Service

---

##  BASE URL

```
http://localhost:8081
```

---

## ⚙ How to Run the Project

### 1. Clone Project

```
git clone <your-repo-url>
cd health-insurance
```

### 2. Start Database

```
docker-compose up -d
```

### 3. Run Application

```
mvn spring-boot:run
```

---

#  COMPLETE EXECUTION FLOW 

Follow this  sequence:

1. Create User → get userId (UUID)
2. Create Patient / Doctor / Clinic → using userId
3. Create Appointment
4. Create Encounter
5. Add:

    * Vitals
    * Lab Orders
    * Prescription

---

#  1. USER SERVICE

##  Create User

POST `/api/v1/users`

```json
{
  "userName": "bhagyashri123",
  "email": "bhagyashri@gmail.com",
  "password": "Password@123",
  "contactNumber": 9876543210,
  "bloodGroup": "O+",
  "isActive": true,
  "rolesSet": ["PATIENT"],
  "address": {
    "street": "MG Road",
    "city": "Pune",
    "state": "Maharashtra",
    "country": "India",
    "zipCode": "411001"
  }
}
```

## Get User

GET `/api/v1/users/{userId}`

##  Get All Users

GET `/api/v1/users`

##  Update User

PUT `/api/v1/users/{userId}`

##  Delete User

DELETE `/api/v1/users/{userId}`

---

#  2. PATIENT SERVICE

##  Create Patient

POST `/api/v1/patients`

```json
{
  "userId": "UUID_FROM_USER",
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
```

##  Other APIs

* GET `/api/v1/patients/{id}`
* GET `/api/v1/patients`
* PUT `/api/v1/patients/{id}`
* DELETE `/api/v1/patients/{id}`

---

#  3. DOCTOR SERVICE

## ➤ Create Doctor

POST `/api/v1/doctors`

```json
{
  "userId": "UUID_FROM_USER",
  "firstName": "Vikram",
  "lastName": "Patel",
  "specialization": "Pediatrics",
  "licenseNumber": "E56789",
  "department": "Children",
  "availabilityStatus": true,
  "contactNumber": "9765432101",
  "address": {
    "street": "River Road",
    "city": "Ahmedabad",
    "state": "Gujarat",
    "country": "India",
    "zipCode": "380001"
  }
}
```

##  Other APIs

* GET `/api/v1/doctors/{id}`
* GET `/api/v1/doctors`
* PUT `/api/v1/doctors/{id}`
* DELETE `/api/v1/doctors/{id}`

---

# 4. CLINIC SERVICE

##  Create Clinic

POST `/api/v1/clinic`

```json
{
  "userId": "UUID_FROM_USER",
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
```

##  Other APIs

* GET `/api/v1/clinic/{id}`
* GET `/api/v1/clinic`
* PUT `/api/v1/clinic/{id}`
* DELETE `/api/v1/clinic/{id}`

---

#  5. APPOINTMENT SERVICE

##  Create Appointment

POST `/api/v1/appointments`

```json
{
  "patientId": 1,
  "doctorId": 4,
  "clinicId": 2,
  "appointmentDate": "2026-04-10T11:30:00",
  "status": "CONFIRMED",
  "reason": "Regular health checkup"
}
```

##  Get Appointment

GET `/api/v1/appointments/{appointmentId}`

##  Get All Appointments

GET `/api/v1/appointments`

##  Update Appointment (FIXED)

PUT `/api/v1/appointments/{id}`

##  Delete Appointment

DELETE `/api/v1/appointments/internal/{id}`

---

#  6. ENCOUNTER SERVICE

##  Create Encounter

POST `/api/v1/encounter`

```json
{
  "appointmentId": 4,
  "diagnosis": "Stomach infection",
  "notes": "Recommended antibiotics and light diet"
}
```

---

#  7. VITALS SERVICE

##  Add Vitals

POST `/api/v1/vitals/{encounterId}`

```json
{
  "height": 180.2,
  "weight": 85.5,
  "temperature": 100.2,
  "pulseRate": 88,
  "bloodPressure": "140/90"
}
```

---

#  8. LAB ORDERS SERVICE

##  Add Lab Order

POST `/api/v1/lab_orders/{encounterId}`

```json
{
  "testName": "Blood Test",
  "testDescription": "Complete blood count",
  "labStatus": "ORDERED",
  "result": "Pending"
}
```

---

# 9. PRESCRIPTION SERVICE

## Add Prescription

POST `/api/v1/prescription/{encounterId}`

```json
{
  "medicineName": "Azithromycin",
  "dosage": "500mg",
  "frequency": "Once a day",
  "duration": "3 days",
  "instructions": "Take before food"
}
```

---

# Imp Points

* User → UUID
* Patient/Doctor/Clinic → Long ID
* Appointment → UUID + Long ID
* Blood Pressure → String ("120/80")

---

#  TESTING USING POSTMAN

1. Select HTTP Method (GET/POST/PUT/DELETE)
2. Enter URL
3. Go to Body → raw → JSON
4. Paste Request JSON
5. Click Send