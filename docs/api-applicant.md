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
  "diploma_file_url": "string" // URL hasil upload file terpisah
}
```
- **Response:**
```json
{
  "data": null,
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
      "status": "completed|pending",
      "note": "string"
    },
    "selection": {
      "status": "in_progress|passed|failed",
      "note": "string"
    },
    "payment": {
      "status": "pending|completed",
      "note": "string"
    },
    "payment_verification": {
      "status": "in_progress|valid|invalid",
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
  "payment_proof_file_url": "string" // URL hasil upload file terpisah
}
```
- **Response:**
```json
{
  "data": null,
  "success": true,
  "message": "Payment proof uploaded successfully, waiting for verification"
}
```

---

> **Notes:**
> - Semua endpoint yang membutuhkan autentikasi harus mengirimkan header `Authorization: Bearer <token>` hasil login.
> - Untuk upload file (ijazah, payment proof), lakukan upload ke endpoint storage terpisah, lalu gunakan URL-nya pada body JSON.
> - Response error mengikuti format standar:
> ```json
> {
>   "data": null,
>   "success": false,
>   "message": "Error message"
> }
> ``` 