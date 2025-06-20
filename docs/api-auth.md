# API Spec – Authentication (General)

---

## 1. Register (Applicant Only)
- **POST** `/api/auth/register`
- **Request Body:**
```json
{
  "full_name": "string",
  "email": "string",
  "password": "string"
}
```
- **Response:**
```json
{
  "data": "OK",
  "success": true,
  "message": "Registration successful"
}
```

---

## 2. Login (Applicant & Admin)
- **POST** `/api/auth/login`
- **Request Body:**
```json
{
  "email": "string",
  "password": "string"
}
```
- **Response:**
```json
{
  "data": {
    "token": "jwt-token",
    "expired_at": 1718102400000
  },
  "success": true,
  "message": "Login successful"
}
```

---

## 3. Logout (Applicant & Admin)

- **POST** `/api/auth/logout`
- **Headers:**
  - `Authorization: Bearer <token>`
- **Response:**
```json
{
  "data": "OK",
  "success": true,
  "message": "Logout successful"
}
```

---

## 4. Misc

### 4.1. Get Profile
- **GET** `/api/auth/me`
- **Headers:**
  - `Authorization: Bearer <token>`
- **Response:**
```json
{
  "data": {
    "id": 123,
    "full_name": "string",
    "email": "string",
    "role": "APPLICANT|ADMIN"
  },
  "success": true,
  "message": "Profile fetched successfully"
}
```

---

> **Notes:**
> - Response errors follow the standard format:
>   ```json
>   {
>     "data": {}|"OK",
>     "success": true,
>     "message": "Success message"
>   }
>   ```
> - For errors:
>   ```json
>   {
>     "data": null,
>     "success": false,
>     "message": "Error message"
>   }
>   ```
