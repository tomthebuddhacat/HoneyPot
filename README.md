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
| `punishment-logging-channel-id` | The channel where successful and failed punishment attempts are logged. |
| `honey-pot-punishment-reason` | The reason logged in the Discord Audit Log. |
| `honey-pot-message-format` | The format of the warning message. Available options: `text` or `embed`. |
| `honey-pot-message` | The configurable text displayed when the warning message format is set to `text`. |
| `honey-pot-embed.title` | The title of the warning embed. |
| `honey-pot-embed.description` | The description displayed inside the warning embed. |
| `honey-pot-embed.color` | The color of the warning embed. |
| `honey-pot-embed.footer` | The footer displayed at the bottom of the warning embed. |
| `use-components-embed-format` | Whether to use Components V2 for embed warning messages. |
| `use-punishment-logging` | Whether to log punishment attempts or not. |


## Data
The bot uses HOCON (`data.conf`) to store bot related data.

| Key | Description |
|---|---|
| `honey-pot-warning-message-id` | The ID of the published warning message. |


## Built With

![Java](https://img.shields.io/badge/Java-25%2B-orange)
[![JDA](https://img.shields.io/badge/JDA-6.7.0-blue)](https://github.com/discord-jda/JDA)
[![Configurate](https://img.shields.io/badge/Configurate-4.2.0-green)](https://github.com/SpongePowered/Configurate)

## License
![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)