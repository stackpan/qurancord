# qurancord
Qurancord is a discord bot to search for and obtain surahs and/or verses of Quran from the internet. Built using Spring Framework and JDA

## Preview
This project is built on top of JVM Technology which will require Java 17 and using Gradle as dependency management. Quran's sources are obtained from https://alquran.cloud/api.

These are the dependencies used:
- [Spring Framework](https://spring.io/)
- [JUnit Jupiter (JUnit 5)](https://junit.org/junit5)
- [JDA (Java Discord API)](https://github.com/DV8FromTheWorld/JDA)
- [freya022/BotCommands](https://github.com/freya022/BotCommands)

### Usage

This bot monstly uses the Discord Slash Command. The following is a list of commands:

| Command          | Options                                   | Description                                              |
|------------------|-------------------------------------------|----------------------------------------------------------|
| `/surah find`    | `number: int`                             | Find surah information based on specified number         |
| `/surah search`  | `keyword: str`                            | Search surah information based on specified name keyword |
| `/surah random`  | -                                         | Get randomized surah information                         |
| `/ayah find`     | `surah: int` `number: int` `image?: bool` | Find an ayah from specified options                      |             |
| `/ayah search`   | `keyword: str` `image?: bool`             | Search ayah translations based on specified keyword      |                      |
| `/ayah random`   | `surah?: int` `image?: bool`              | Get randomized ayah information                          |

## Installation

### Manual
Requirement:
- JDK 17 or later

Procedure:
1. Clone this repo:
   ```
    git clone https://github.com/stackpan/qurancord.git
    cd /path/to/qurancord
   ```
2. Copy the app config template which are located on `src/main/resources/application.properties.example`
   ```shell
   cp src/main/resources/application.properties.example src/main/resources/application.properties
   ```
3. Apply your token into `discord.bot-token` key inside app config:
    ```properties
    discord.bot-token=YOUR_COPIED_TOKEN
    ```
4. Run it:
   ```shell
   ./gradlew bootRun
   ```