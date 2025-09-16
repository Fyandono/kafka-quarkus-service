# Proyek Layanan Kafka Sederhana

Proyek ini adalah contoh layanan sederhana menggunakan **Quarkus**, **Apache Kafka**, dan **PostgreSQL**, dengan **Java 17** sebagai versi Java yang digunakan. Aplikasi ini memproses transaksi secara asynchronous:

* Menerima pesan JSON dari topik Kafka.
* Memproses pesan dan menyimpan data transaksi ke database PostgreSQL.
* Mengirim pesan hasil kembali ke topik Kafka yang berbeda.

Panduan ini akan membantu menyiapkan dan menguji aplikasi.

---

## 🚀 Persiapan Lingkungan

Menggunakan **Docker Compose** untuk menyederhanakan setup Kafka dan PostgreSQL. Pastikan **Docker Desktop** sudah terinstal dan sistem menggunakan **Java 17**.

### Mulai Layanan Eksternal

Buka terminal di direktori root proyek dan jalankan:

```bash
docker compose up -d
```

Ini akan menjalankan tiga kontainer:

* **zookeeper**: Layanan koordinasi untuk Kafka.
* **kafka**: Broker Kafka dengan topik `my-input-topic` dan `my-output-topic` yang dibuat otomatis.
* **postgres-db**: Database PostgreSQL untuk menyimpan data transaksi.

---

## 🕵️‍♂️ Pengujian Manual

Langkah-langkah berikut akan membantu menguji aplikasi secara manual:

### 1. Jalankan Aplikasi di Mode Dev

```bash
quarkus dev
```

### 2. Kirim Pesan ke Kafka

Masuk ke kontainer Kafka:

```bash
docker exec -it kafka /bin/bash
```

Jalankan producer dan kirim pesan JSON:

```bash
kafka-console-producer --bootstrap-server localhost:9092 --topic my-input-topic
```

Contoh pesan JSON:

```json
{"userId":"user-001", "amount":1500.0}
```

### 3. Verifikasi Data

* **Log Aplikasi**: Lihat terminal `quarkus dev` untuk memastikan pesan diterima, diproses, dan dikirim keluar.
* **Database**: Masuk ke PostgreSQL dan periksa data transaksi:

```bash
docker exec -it postgres-db psql -U postgres -c "SELECT * FROM transaction;"
```

* **Pesan Keluar**: Pastikan aplikasi mengirim pesan ke topik `my-output-topic`:

```bash
docker exec -it kafka kafka-console-consumer --bootstrap-server localhost:9092 --topic my-output-topic --from-beginning
```

### 4. Contoh Status Transaksi

Aplikasi menetapkan status transaksi berdasarkan jumlah `amount`:

* **HIGH\_VALUE**: Jika `amount` > 1000
* **NORMAL**: Jika `amount` ≤ 1000

Contoh kasus:

| userId   | amount | status      |
| -------- | ------ | ----------- |
| user-001 | 1500.0 | HIGH\_VALUE |
| user-002 | 500.0  | NORMAL      |
| user-003 | 1200.0 | HIGH\_VALUE |

Ini membantu pengguna memahami bagaimana aplikasi menandai transaksi berdasarkan nilai amount.

---

## 🛑 Menghentikan Layanan

```bash
docker compose down
```

Selamat menguji aplikasi!
