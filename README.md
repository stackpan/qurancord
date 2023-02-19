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
Bot ini menggunakan slash command, sebagai berikut:
- `/random surah`: Meminta surah Al-Quran secara acak
- `/random ayah any`: Meminta ayat Al-Quran secara acak di surah apapun
- `/random ayah from-surah` `[surah:str]`: Meminta ayat Al-Quran secara acak berdasarkan surah
- `/random ayah image`: **(Preview Feature)** Meminta ayat Al-Quran secara acak di surah apapun dengan gambar
- `/search surah` `[surah_search:str]`: Mencari surah Al-Quran
- `/search ayah` `[surah_search:str]` `[ayah_number:int]`: Mencari ayat di Al-Quran

## Installasi

### Manual Local
1. **Persyaratan**: Pastikan kamu telah menginstall JRE/JDK minimal versi 17 di local komputer kamu
2. Membuat Discord Application
    - Pergi ke halaman [discord developer portal](https://discord.com/developers/applications) dan login
    - Klik "New Application"
    - Berikan sebuah nama
    - Buat sebuah bot dengan pergi ke menu "Bot" di pengaturan sebelah kiri lalu klik "Add Bot" dan beri nama
    - Klik "Reset Token" dan copy tokennya, lalu tambahkan ke environment variable di local kamu dengan key `BOT_TOKEN`. Caranya: 
        ```
        export BOT_TOKEN=YOUR_COPIED_TOKEN
        ```
3. Invite bot ke server
    - Di halaman discord developer portal, pergi ke menu OAuth2 - URL Generator
    - Generate URL dengan mencentang: 
      - scopes:
        - bot
      - bot permissions:
        - Send Messages
        - Use Slash Command
    - Copy URL yang digenerate dan buka di browser untuk mengundang bot ke server kamu
4. Download [distribusi file jar kami](https://github.com/stackpan/qurancord/releases) (**Catatan!** pilih versi terbaru)
5. Buka command line, lalu masuk ke lokasi jar yang sudah didownload
    ```
    cd /to/downloadedjar/directory
    ```
6. Jalankan dengan perintah:
    ```
    java -jar quranbot-VERSION-jar-with-dependencies.jar
    ```

### Docker image

Jika kamu kesulitan dengan metode [Manual Local](#manual-local) kamu bisa memanfaatkan [Docker Image kami](https://hub.docker.com/r/ivanzkyanto/qurancord).

1. Pastikan kamu telah menginstall setidaknya docker daemon di mesin kamu, atau bisa sekaligus dengan docker client. Kunjungi [docker.com](https://www.docker.com) untuk menginstallnya
2. Pull image [ivanzkyanto/qurancord](https://hub.docker.com/r/ivanzkyanto/qurancord):
    ```
    docker pull ivanzkyanto/qurancord
    ```
3. Buat dan jalankan sebuah kontainer dari image, **pastikan untuk menambahkan `BOT_TOKEN` di environment variable**:
    ```
    docker run -d -e BOT_TOKEN=<YOUR_BOT_TOKEN_HERE> ivanzkyanto/qurancord
    ```
    Kamu tidak perlu membuka port apapun.
