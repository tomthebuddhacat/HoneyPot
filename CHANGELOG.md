# Changelog

All changes to this project will be documented here.

## [1.0.2.1] - 2026/11/05

### Resolved 
- A comment appearing in the wrong section.
- Rephrased a configuration key and it's value.
- Fixed a NumberFormatException caused by a null embedColor value by defining a default value.
- Fixed an issue where the bot attempted to interact with members whose highest role was above the bot's role.
- Improved code consistency throughout the project.
- Refactored Config and Data into ConfigManager and DataManager, respectively.

## [1.0.2] - 2026/11/05

### Implemented
- Added support for Discord Components V2.
- Refactored ReadyListener class to use the centralized MessageService class.
- Improved overall code readability
- Improved the structure of `CHANGELOG.md`.
- Updated `README.md` to include the new configuration keys.

## [1.0.1] - 2026/11/03

### Implemented
- Updated the data storage system to save data in separate configuration files.
- Improved overall code readability.

## [1.0.0] - 2026/11/03

### Implemented
- Added configurable HoneyPot warning messages.
- Added support for text and embed warning messages.
- Added detection of existing HoneyPot warning messages.
- Updated `README.md` to include the new configuration keys.

### Resolved
- Corrected grammatical errors in `README.md`.