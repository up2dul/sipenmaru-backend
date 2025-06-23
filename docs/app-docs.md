# 📚 Dokumentasi Sipenmaru Backend

## 🏗️ Arsitektur Aplikasi

Aplikasi Sipenmaru menggunakan arsitektur dengan pembagian layer sebagai berikut:

### 📁 Struktur Package

```
src/main/java/group5/sipenmaru/
├── config/           # Konfigurasi aplikasi
├── controller/       # REST API endpoints
├── entity/          # JPA entities dan enums
├── model/           # Request/Response DTOs
├── repository/      # Data access layer
├── security/        # Security & authentication
├── service/         # Business logic layer
├── seeder/          # Database seeding
└── SipenmaruApplication.java
```

## 🔧 Konfigurasi (`config/`)

### `SecurityConfig`
- Konfigurasi Spring Security dengan JWT authentication
- Setup CORS dan session management
- Konfigurasi password encoder menggunakan BCrypt

### `WebMvcConfig`
- Konfigurasi CORS mapping untuk semua endpoints
- Registrasi `AdminOnlyInterceptor`

## 🎮 Controllers (`controller/`)

### `AuthController`
**Base URL:** `/api/auth`
- Menangani autentikasi login dan registrasi
- Endpoint untuk mendapatkan informasi user yang sedang login

### `AdminController`
**Base URL:** `/api/admin`
- **Protected dengan `@AdminOnly`**
- `GET /dashboard` - Dashboard statistik admin
- `GET /applicants` - Daftar semua pendaftar
- `GET /applicants/{id}` - Detail pendaftar
- `PUT /applicants/{id}/status` - Update status pendaftar
- `PUT /applicants/{id}/verify-payment` - Verifikasi pembayaran

### `RegistrationController`
**Base URL:** `/api/registration`
- `POST /profile` - Submit profil pendaftar
- `GET /profile` - Ambil data profil pendaftar
- `GET /status` - Status pendaftaran

### `PaymentController`
**Base URL:** `/api/registration/payment`
- `GET /methods` - Daftar metode pembayaran
- `POST /proof` - Upload bukti pembayaran

### `ErrorController`
- Global exception handler
- Menangani `ResponseStatusException` dan generic exceptions

## 🗃️ Entities & Enums (`entity/`)

### Core Entities

#### `User`
```java
- id: Long
- fullName: String
- email: String (unique)
- password: String
- role: UserRole (ADMIN/APPLICANT)
- token: String
- tokenExpiredAt: Long
```

#### `Applicant`
```java
- id: Long
- user: User (OneToOne)
- registrationCode: String (unique)
- paymentStatus: PaymentStatus
- selectionStatus: SelectionStatus
- createdAt: Date
```

#### `Biodata`
```java
- id: Long
- applicant: Applicant (OneToOne)
- fullName: String
- email: String
- birthDate: Date
- address: String
- gender: Gender
- selectedMajor: String
- diplomaFileUrl: String
```

#### `Payment`
```java
- id: Long
- applicant: Applicant (OneToOne)
- amount: Double
- paymentMethod: String
- paymentProofUrl: String
- status: PaymentStatus
- note: String
- verifiedAt: Date
- verifiedBy: String
```

#### `Selection`
```java
- id: Long
- applicant: Applicant (OneToOne)
- status: SelectionStatus
- note: String
- createdAt: Date
```

### Enums (`entity/enums/`)
- `Gender`: `MALE`, `FEMALE`
- `PaymentStatus`: `PENDING`, `PROOF_SUBMITTED`, `VERIFIED`, `REJECTED`
- `SelectionStatus`: `IN_PROGRESS`, `PASSED`, `FAILED`
- `SubmissionStatus`: `PENDING`, `COMPLETED`
- `UserRole`: `ADMIN`, `APPLICANT`

## 📨 Models (`model/`)

### Request Models (`model/request/`)
- `SubmitProfileRequest` - Data profil pendaftar
- `SubmitPaymentProofRequest` - Upload bukti pembayaran
- `UpdateApplicantStatusRequest` - Update status pendaftar oleh admin
- `VerifyPaymentRequest` - Verifikasi pembayaran oleh admin

### Response Models (`model/response/`)
- `ProfileResponse` - Response data profil
- `RegistrationStatusResponse` - Status pendaftaran
- `PaymentMethodResponse` - Metode pembayaran available
- `ApplicantListResponse` - List pendaftar untuk admin
- `ApplicantDetailResponse` - Detail pendaftar untuk admin
- `DashboardResponse` - Statistik dashboard admin

## 🏪 Repositories (`repository/`)

Semua repository mengextend `JpaRepository` dengan custom query methods:

- `UserRepository` - `findByEmail()`
- `ApplicantRepository` - `findByUser()`, `findByUserEmail()`
- `BiodataRepository` - `findByApplicant()`
- `PaymentRepository` - `findByApplicant()`, `findByApplicantId()`
- `SelectionRepository` - `findByApplicant()`

## 🛡️ Security (`security/`)

### Authentication & Authorization
- `JwtAuthenticationFilter` - Filter untuk validasi JWT token
- `JwtAuthenticationEntryPoint` - Handler untuk unauthorized access
- `CustomUserDetailsService` - Custom user details service

### Admin Protection
- `@AdminOnly` - Annotation untuk endpoint khusus admin
- `AdminOnlyInterceptor` - Interceptor untuk validasi role admin

### Password Encryption
- `BCrypt` - Custom BCrypt implementation

## ⚙️ Services (`service/`)

### `AuthService`
- Login dan registrasi user
- JWT token management
- User authentication

### `RegistrationService`
- Submit dan update profil pendaftar
- Get status pendaftaran
- Manajemen biodata

### `PaymentService`
- Daftar metode pembayaran
- Submit bukti pembayaran
- Fee management (Rp 500.000)

### `AdminService`
- Dashboard statistik
- Management pendaftar
- Update status dan verifikasi pembayaran
- Gender statistics dan recent applicants

## 🌱 Database Seeding (`seeder/`)

### `DatabaseSeeder`
**Automatic seeding saat aplikasi start (jika database kosong)**

**Data yang di-seed:**
- **Admin user:** `admin@sipenmaru.com` / `user123`
- **6 sample applicants** dengan data lengkap
- **Sample biodata** untuk setiap applicant
- **Sample payments** dengan berbagai status

## 🔐 Authentication Flow

1. **Login:** `POST /api/auth/login`
2. **JWT Token** digenerate dan disimpan di database
3. **Setiap request** harus include `Authorization: Bearer <token>`
4. **Token validation** dilakukan oleh `JwtAuthenticationFilter`
5. **Admin endpoints** diproteksi dengan `@AdminOnly`

## 📊 Business Logic Flow

### Pendaftaran Mahasiswa Baru:
1. **Register** → Create User account
2. **Submit Profile** → Create Biodata
3. **Payment** → Upload proof & wait verification
4. **Selection** → Admin reviews and updates status

### Admin Management:
1. **Dashboard** → View statistics
2. **Applicant List** → Manage all applicants
3. **Detail View** → Review individual applications
4. **Status Update** → Update payment/selection status
5. **Payment Verification** → Approve/reject payments

## 🔄 Alur Data Antar Layer

### Request Flow (Bottom-Up)
```
Database Layer (MySQL/PostgreSQL)
    ↑
Repository Layer (JPA Repositories)
    ↑
Service Layer (Business Logic)
    ↑
Controller Layer (REST API)
    ↑
Security Layer (JWT + Admin Protection)
    ↑
Frontend/Client
```

### Response Flow (Top-Down)
```
Frontend/Client
    ↓
Security Layer (Authentication)
    ↓
Controller Layer (Request Handling)
    ↓
Service Layer (Data Processing)
    ↓
Repository Layer (Database Query)
    ↓
Database Layer (Data Storage)
```

### Contoh Alur Lengkap - Submit Profile:
1. **Client** mengirim `POST /api/registration/profile` dengan JWT token
2. **JwtAuthenticationFilter** memvalidasi token dan set user context
3. **RegistrationController** menerima request dan extract user dari SecurityContext
4. **RegistrationService** memproses business logic:
   - Cek apakah user sudah punya applicant record
   - Create/update Applicant dan Biodata entities
   - Generate registration code jika belum ada
5. **Repository layer** melakukan save ke database
6. **Response** dikembalikan melalui controller ke client

---

Dokumentasi ini mencakup semua komponen utama dalam struktur `src/main/java/group5/sipenmaru` dan menjelaskan bagaimana setiap layer berinteraksi dalam sistem Sipenmaru.