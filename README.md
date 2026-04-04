# 📌 GearX BackEnd App

## 🚀 Giới thiệu

GearX-BackEnd-App là hệ thống backend phục vụ cho ứng dụng **GearX** – nền tảng thương mại điện tử được 2 thằng nhóc múa lửa.
Dự án được xây dựng bằng **Spring Boot** kết hợp **PostgreSQL** và **Redis** để hỗ trợ:

* ✅ Quản lý người dùng & phân quyền (Admin, User)
* ✅ Xác thực & lưu session/token với Redis
* ✅ Quản lý sản phẩm, dịch vụ, đơn hàng
* ✅ API RESTful cho frontend (Vue/Shadcn-UI)
* 🥷🏿 Cloudinary để lưu trữ hình ảnh

---

## 🛠️ Công nghệ sử dụng

* **Java 21**
* **Spring Boot 3.3+**
* **Spring Security + JWT**
* **PostgreSQL 15** (database chính)
* **Redis 7.2** (lưu token đăng nhập, cache)
* **MyBatis** (ORM + custom mapper XML)
* **Docker Compose** (quản lý PostgreSQL + Redis)

---


## ⚙️ Cấu hình

### 1. Environment variables

Trong file `application.yml` tự vô coi, có `.env exmaple đó` tự coi luôn đi

---

### 2. Docker Compose

```yaml
version: "3.9"

services:
  postgres:
    image: postgres:15
    container_name: gearx_postgres
    restart: always
    environment:
      POSTGRES_USER: tự nhập đi ba
      POSTGRES_PASSWORD: Cái này giấu kĩ
      POSTGRES_DB: gearx_db
    ports:
      - "5432:5432"
    volumes:
      - pgdata:/var/lib/postgresql/data

  redis:
    image: redis:7.2
    container_name: gearx_redis
    restart: always
    ports:
      - "6379:6379"
    command: >
      redis-server --requirepass khongCoDauDungCoTim --save 60 1 --appendonly yes --loglevel warning
    volumes:
      - redis_data:/data

volumes:
  pgdata:
  redis_data:
```

---

## ▶️ Chạy ứng dụng

### 1. Khởi động DB + Redis

```sh
docker compose up -d
```

### 2. Chạy backend

chạy bằng intellij đi ba

### 3. API chạy tại:

```
http://localhost:8080/api/v1
```

## 📜 License

Dự án thuộc sở hữu **yeumedevs**. Hiện tại chưa có gì, xài gì xài đi

---

README này tôi kêu chatgpt viết, nếu bạn đọc tới đây thì có lẽ bạn cũng rảnh phết
