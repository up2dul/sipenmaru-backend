# API Spec – Authentication (General)

---

## 1. Register (User Only)
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

## 2. Login (User & Admin)
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
    "access_token": "jwt-token",
    "refresh_token": "jwt-token"
  },
  "success": true,
  "message": "Login successful"
}
```

---

## 3. Logout (User & Admin)

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

## 4. Refresh Token (User & Admin)


- **POST** `/api/auth/refresh`
- **Headers:**
  - `Authorization: Bearer <token>`
- **Response:**
```json
{
  "data": {
    "access_token": "jwt-token",
    "refresh_token": "jwt-token"
  },
  "success": true,
  "message": "Token refreshed successfully"
}
```

---

## 5. Misc

### 4.1. Get Profile
- **GET** `/api/auth/me`
- **Headers:**
  - `Authorization: Bearer <token>`
- **Response:**
```json
{
  "data": {
    "id": "uuid",
    "full_name": "string",
    "email": "string",
    "role": "user|admin"
  },
  "success": true,
  "message": "Profile fetched successfully"
}
```

---

> **Notes:**
> - Semua endpoint autentikasi menggunakan format response:
>   ```json
>   {
>     "data": {}|"OK",
>     "success": true,
>     "message": "Success message"
>   }
>   ```
> - Untuk error, gunakan:
>   ```json
>   {
>     "data": null,
>     "success": false,
>     "message": "Error message"
>   }
>   ```
