# YuRun
Topik 3 PBW : Aplikasi Track Running - YURUN
**TUBES F - PBW A**
1. 6182101017 - RAYHAN ADJI SANTOSO
2. 6182101021 - CLARENCE FERDINAND
3. ⁠6182201002 - FERNANDO INDRAWAN
4. ⁠6182201017 - TIFFANY TASYA AGATHA 

## Spesifikasi Sistem
- **Gradle** - sistem build pada proyek
- **Java** - bahasa pemrograman utama
- **PostgreSQL** - database untuk menyimpan data 
- **Spring Boot** - framework untuk membangun aplikasi berbasis java

## Persyaratan Sistem 
1. Java versi 17 
2. Gradle versi 8.0 atau terbaru
3. Database PostgreSQL

## Penggunaan  
Clone proyek SiapSidang 
```
git clone https://github.com/tiffany78/YuRun
```
Pindah ke dalam direktori proyek SiapSidang

# Instalasi dan Pengaturan
## 1. Persiapan Database 
1. Masuk ke dalam PostgreSQL menggunakan terminal atau pgAdmin
2. Membuat database dengan nama `YURUN`
3. Run Tabel, View, dan Dummy Data yang sudah tersedia pada folder `database`. Lakukan proses run `Create_Table` terlebih dahulu, kemudian `Dummy_Data` 
## 2. Konfigurasi dengan Database
Buka file `src/main/resources/application.properties` dan sesuaikan dengan pengaturan database Anda.
```
spring.datasource.url=jdbc:postgresql://localhost:5432/YURUN
spring.datasource.username=(username postgresql Anda)
spring.datasource.password=(password postgresql Anda)
```
## 3. Proses Build Aplikasi
Setelah konfigurasi selesai, Anda bisa membangun terlebih dahulu aplikasi menggunakan Gradle. 
Di dalam direktori proyek, buka terminal dan jalankan perintah berikut:
```
gradlew clean build
```
## 4. Proses Running Aplikasi
Setelah proses build berhasil, Anda bisa menjalankan aplikasi di dalam direktori proyek, buka terminal dan jalankan perintah berikut:
```
./gradlew bootRun
```
Aplikasi akan berjalan dan bisa akses melalui browser pada alamat `http://localhost:8080/`. Anda akan langsung diarahakan pada Landing Page YURUN. 
