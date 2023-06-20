# Changelog

## [2.0.0] - ???

### Changed

- Now supports multiple Minecraft versions with the same dependency.

## [1.2.4] - 2022-06-05

### Fixed

- Sound category volume not being saved

## [1.2.3] - 2022-06-05

### Fixed

- Sound categories being null when mods are being initialized
- Default float not being applied if settings are not yet saved

## [1.2.2] - 2022-06-01

### Changed

- Made default level mixin optional - this makes Optifine run with SoundCategories, however the default levels for all
  sounds will always be 100%.

## [1.2.1] - 2022-01-02

### Changed

- Added Optifine/Optifabric as an incompatible mod - causes crash on startup
- Added Fabric API as a required dependency

## [1.2.0] - 2021-12-28

### Added

- Category grouping via `master` attribute
- Configurable default volume levels

### Fixed

- Done button offset

## [1.1.0] - 2021-12-15

### Changed

- Replaced registration function with annotations

### Fixed

- Sound categories not being registered after init

## [1.0.0] - 2021-12-15

Initial release
