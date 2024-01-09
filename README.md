# qurancord
Qurancord adalah aplikasi berupa bot discord untuk mencari dan mendapatkan surah dan/atau ayat Al-Quran dengan memanfaatkan API dari internet. Ditulis menggunakan Java.

## Pratinjau
Project ini dibuat menggunakan bahasa Java (Java 17) dan memanfaatkan Apache Maven sebagai manajemen dependensi. Untuk sumber data Al-Quran, didapatkan dari https://equran.id/apidev.

Berikut dependensi yang digunakan:
- [JDA (Java Discord API)](https://github.com/DV8FromTheWorld/JDA)
- [JUnit Jupiter (JUnit 5)](https://junit.org/junit5)
- [Google GSON](https://github.com/google/gson)
- [SLF4J logback-classic](https://logback.qos.ch)
- [freya022/BotCommands](https://github.com/freya022/BotCommands)

### Penggunaan

Bot ini menggunakan slash command, berikut ini adalah perintahnya:

| Command                   | Options                                                   | Description                                         |
|---------------------------|-----------------------------------------------------------|-----------------------------------------------------|
| `/random surah`           |                                                           | Meminta surah Al-Quran secara acak                  |
| `/random ayah any`        | `without_image:bool`                                      | Meminta ayat Al-Quran secara acak di surah apapun   |
| `/random ayah from-surah` | `surah:str` `without_image:bool`                          | Meminta ayat Al-Quran secara acak berdasarkan surah |
| `/search surah`           | `surah_search:str`                                        | Mencari surah Al-Quran                              |
| `/search ayah`            | `surah_search:str` `ayah_search:int` `without_image:bool` | Mencari ayat di Al-Quran                            |

## Installasi

### Persiapan Discord Application
1. Pergi ke halaman [discord developer portal](https://discord.com/developers/applications) dan login
2. Klik "New Application"
3. Beri nama
4. Buat sebuah bot dengan pergi ke menu "Bot" di pengaturan sebelah kiri lalu klik "Add Bot" dan beri nama
5. Klik "Reset Token", **salin dan simpan tokennya**
6. Di halaman discord developer portal, pergi ke menu OAuth2 - URL Generator
7. Generate URL dengan mencentang item berikut:
   - scopes:
     - bot
   - bot permissions:
     - Send Messages
     - Use Slash Command
8. Salin URL yang telah di-_generate_ dan buka di browser untuk mengundang bot tersebut ke server kamu
9. Ikuti salah satu metode deployment berikut: [Manual Local](#manual-local) atau [Docker Compose](#docker-compose)

### Deployment

#### Manual Local
Persyaratan:
- JDK versi 17 ke atas

Langkah-langkah:
1. Clone dan buka repository ini:
   ```
    git clone https://github.com/stackpan/qurancord.git
    cd /path/to/qurancord
   ```
2. Tambahkan environment variable `BOT_TOKEN` di local kamu:
    ```
    export BOT_TOKEN=YOUR_COPIED_TOKEN
    ```
3. Build file jar:
   ```
   ./mvnw clean compile assembly:single
   ```
4. Jalankan dengan perintah:
    ```
    java -jar ./bot/target/qurancord-bot-{VERSION}-jar-with-dependencies.jar
    ```

#### Docker compose

Persyaratan:
- Docker dengan Docker Compose. Kunjungi [docker.com](https://www.docker.com) untuk menginstallnya

Langkah-langkah:
1. Clone repository ini
2. Isi environment `BOT_TOKEN` di dalam file `docker-compose.yaml`
3. Jalankan file compose:
    ```
    docker compose up
    ```
