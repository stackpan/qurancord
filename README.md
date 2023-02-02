# qurancord
Qurancord adalah aplikasi berupa bot discord untuk mencari dan mendapatkan surah dan/atau ayat Al-Quran dengan memanfaatkan API dari internet. Ditulis menggunakan Java.

## Pratinjau
Project ini dibuat menggunakan bahasa Java (Java 17) dan memanfaatkan Apache Maven sebagai manajemen dependensi. Untuk sumber data Al-Quran, didapatkan dari https://equran.id/apidev.

Berikut dependensi yang digunakan:
- [JDA (Java Discord API)](https://github.com/DV8FromTheWorld/JDA)
- [JUnit Jupiter (JUnit 5)](https://junit.org/junit5)
- [Mockito](https://site.mockito.org)
- [Google GSON](https://github.com/google/gson)
- [SLF4J logback-classic](https://logback.qos.ch)

### Penggunaan
Bot ini menggunakan slash command, sebagai berikut:
- `/random surah`: Meminta surah Al-Quran secara acak
- `/random ayah any`: Meminta ayat Al-Quran secara acak di surah apapun
- `/random ayah with-surah-name` `[surah_name:str]`: Meminta ayat Al-Quran secara acak berdasarkan surah
- `/random ayah with-surah-number` `[surah_number:int]`: Meminta ayat Al-Quran secara acak berdasarkan nomor surah
- `/search surah name` `[search_name:str]`: Mencari surah Al-Quran dengan nama
- `/search surah number` `[search_number:int]`: Mencari surah Al-Quran dengan nomor urut surah
- `/search ayah with-surah-name` `[surah_name:str]` `[ayah_number:int]`: Mencari ayat di Al-Quran dengan nama surah
- `/search ayat with-surah-number` `[surah_number:int]` `[ayah_number:int]`: Mencari ayat di Al-Quran dengan nomor urut surah

## Installasi
1. **Persyaratan**: Pastikan kamu telah menginstall JRE/JDK minimal versi 17 di local komputer kamu
2. Membuat Discord Application
    - Pergi ke halaman [discord developer portal](https://discord.com/developers/applications) dan login
    - Klik "New Application"
    - Berikan sebuah nama
    - Buat sebuah bot dengan pergi ke menu "Bot" di pengaturan sebelah kiri lalu klik "Add Bot" dan beri nama
    - Klik "Reset Token" dan copy tokennya, lalu tambahkan ke environment variable di local kamu dengan key `BOT_TOKEN`. Caranya: `export BOT_TOKEN=YOUR_COPIED_TOKEN`
3. Invite bot ke server
    - Di halaman discord developer portal, pergi ke menu OAuth2 - URL Generator
    - Generate URL dengan mencentang: 
      - scopes:
        - bot
      - bot permissions:
        - Send Messages
        - Use Slash Command
    - Copy URL yang digenerate dan buka di browser untuk mengundang bot ke server kamu
4. Download [distribusi file jar kami](https://github.com/stackpan/qurancord/tags) (**Catatan!** pilih versi terbaru)
5. Buka command line, lalu `cd /to/downloadedjar/directory`
6. Jalankan dengan perintah `java -jar quranbot-VERSION-jar-with-dependencies.jar`
