# HoneyPot
A Discord bot built with Java and JDA to prevent messages that match known scam messages.

## Requirements
- Java 25
- Gradle


## Setup
1. Clone this repository.
2. Open the project in your preferred IDE.
3. Configure your `config.conf` file.
4. Add your Discord bot token and configure the honeypot channel.
5. Run the `Main` class.

## Configuration
The bot uses HOCON (`config.conf`) for its configuration.

| Configuration | Description |
|---|---|
| `discord-bot-token` | The Discord bot token. |
| `discord-bot-status` | The activity displayed by the bot. |
| `honey-pot-channel-id` | The channel monitored by the bot. |
| `honey-pot-pinishment-reason` | The reason logged in the Discord Audit Log. |

## Built With

![Java](https://img.shields.io/badge/Java-25%2B-orange)
[![JDA](https://img.shields.io/badge/JDA-6.7.0-blue)](https://github.com/discord-jda/JDA)
[![Configurate](https://img.shields.io/badge/Configurate-4.2.0-green)](https://github.com/SpongePowered/Configurate)

## License
![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)