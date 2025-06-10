# API Spec – Admin SIPENMARU

---

## 1. Dashboard

### 1.1. Get Dashboard Statistics
- **GET** `/api/admin/dashboard`
- **Headers:**
  - `Authorization: Bearer <token>`
- **Response:**
```json
{
  "data": {
    "total_applicants": 580,
    "total_applicants_this_month": 58,
    "recent_applicants": [
      { "full_name": "string" },
      { "full_name": "string" },
      { "full_name": "string" }
    ],
    "gender_stats": {
      "male": 380,
      "female": 200
    }
  },
  "success": true,
  "message": "Dashboard statistics fetched successfully"
}
```

---

## 2. Applicants Management

### 2.1. Get Applicants List
- **GET** `/api/admin/applicants`
- **Headers:**
  - `Authorization: Bearer <token>`
- **Query Params (optional):**
  - `year`: string (e.g. "2024/2025")
- **Response:**
```json
{
  "data": [
    {
      "id": "uuid",
      "full_name": "string",
      "email": "string",
      "payment_status": "paid|unpaid",
      "selection_status": "passed|failed|in_progress"
    }
    // ...
  ],
  "success": true,
  "message": "Applicants list fetched successfully"
}
```

### 2.2. Get Applicant Detail
- **GET** `/api/admin/applicants/{id}`
- **Headers:**
  - `Authorization: Bearer <token>`
- **Response:**
```json
{
  "data": {
    "id": "uuid",
    "registration_code": "string",
    "full_name": "string",
    "email": "string",
    "gender": "male|female",
    "birth_date": "YYYY-MM-DD",
    "selected_major": "string",
    "address": "string",
    "registration_file_url": "string",
    "payment_proof_url": "string|null",
    "payment_status": "paid|unpaid",
    "selection_status": "passed|failed|in_progress",
    "note": "string|null"
  },
  "success": true,
  "message": "Applicant detail fetched successfully"
}
```

### 2.3. Update Applicant Status (Selection/Payment/Note)
- **PUT** `/api/admin/applicants/{id}/status`
- **Headers:**
  - `Authorization: Bearer <token>`
- **Request Body:**
```json
{
  "selection_status": "passed|failed|in_progress",
  "payment_status": "paid|unpaid",
  "note": "string" // optional
}
```
- **Response:**
```json
{
  "data": null,
  "success": true,
  "message": "Applicant status updated successfully"
}
```

---

> **Notes:**
> - Semua endpoint yang membutuhkan autentikasi harus mengirimkan header `Authorization: Bearer <token>` hasil login admin.
> - Response error mengikuti format standar:
> ```json
> {
>   "data": null,
>   "success": false,
>   "message": "Error message"
> }
> ```
