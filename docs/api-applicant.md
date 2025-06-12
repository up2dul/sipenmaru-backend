# API Spec – User (Applicant)

---

## 1. Registration Data

### 1.1. Submit Registration Data
- **POST** `/api/registration/profile`
- **Headers:**
  - `Authorization: Bearer <token>`
- **Request Body:**
```json
{
  "full_name": "string",
  "email": "string",
  "gender": "male|female",
  "birth_date": "YYYY-MM-DD",
  "selected_major": "string",
  "address": "string",
  "diploma_file_url": "string"
}
```
- **Response:**
```json
{
  "data": "OK",
  "success": true,
  "message": "Profile submitted successfully, waiting for admin verification"
}
```

### 1.2. Get Registration Data
- **GET** `/api/registration/profile`
- **Headers:**
  - `Authorization: Bearer <token>`
- **Response:**
```json
{
  "data": {
    "full_name": "string",
    "email": "string",
    "gender": "string",
    "birth_date": "string",
    "selected_major": "string",
    "address": "string",
    "diploma_file_url": "string"
  },
  "success": true,
  "message": "Profile data fetched successfully"
}
```

---

## 2. Registration Status

### 2.1. Get Registration Status
- **GET** `/api/registration/status`
- **Headers:**
  - `Authorization: Bearer <token>`
- **Response:**
```json
{
  "data": {
    "profile_submission": {
      "status": "PENDING|COMPLETED",
      "note": "string"
    },
    "selection": {
      "status": "IN_PROGRESS|PASSED|FAILED",
      "note": "string"
    },
    "payment": {
      "status": "PENDING|COMPLETED|INVALID",
      "note": "string"
    },
    "payment_verification": {
      "status": "PENDING|COMPLETED|INVALID",
      "note": "string"
    }
  },
  "success": true,
  "message": "Registration status fetched successfully"
}
```

---

## 3. Payment

### 3.1. Get Payment Methods & Instructions
- **GET** `/api/registration/payment/methods`
- **Headers:**
  - `Authorization: Bearer <token>`
- **Response:**
```json
{
  "data": {
    "amount": 8700000,
    "methods": [
      {
        "name": "Virtual Account BCA",
        "virtual_account_number": "string",
        "instructions": ["string", "string"]
      },
      {
        "name": "Virtual Account Mandiri",
        "virtual_account_number": "string",
        "instructions": ["string", "string"]
      }
      // ...
    ]
  },
  "success": true,
  "message": "Payment methods fetched successfully"
}
```

### 3.2. Submit Payment Proof
- **POST** `/api/registration/payment/proof`
- **Headers:**
  - `Authorization: Bearer <token>`
- **Request Body:**
```json
{
  "payment_method": "string",
  "payment_proof_file_url": "string"
}
```
- **Response:**
```json
{
  "data": "OK",
  "success": true,
  "message": "Payment proof uploaded successfully, waiting for verification"
}
```

---

> **Notes:**
> - All endpoints that require authentication must send the `Authorization header: Bearer <token>` from the login result.
> - For file uploads (diploma, payment proof), upload to a separate storage endpoint, then use its URL in the JSON body.
> - Response errors follow the standard format:
> ```json
> {
>   "data": null,
>   "success": false,
>   "message": "Error message"
> }
> ``` 