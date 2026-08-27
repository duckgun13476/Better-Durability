# Changelog

#### v1.3.10 — Beta

This beta release targets Forge 1.19.2, Forge 1.20.1, and NeoForge 1.21.1.

- Fixed Frost Walker continuing to freeze water while the enchanted boots were broken.
- Broken Frost Walker boots now retain the enchantment but no longer apply its movement effect until repaired.

#### v1.3.9 — Beta

This beta release targets Forge 1.19.2, Forge 1.20.1, and NeoForge 1.21.1.

- Added configurable broken-tool handling for Create Deployers. Wooden, stone, and iron vanilla tools are consumed by default when exhausted; server owners can change the list in the gameplay configuration.
- Broken tools outside the consumption list, including diamond and higher-tier tools, remain inside the Deployer and stop it from performing further actions until the tool is repaired or removed.
- Fixed Create Deployers continuing to operate after a retained tool entered its broken state.
- Fixed a Forge 1.20.1 crash when a disposable tool reached its breaking point inside a Create Deployer.
- Standardized and regression-tested the Create Deployer behavior across all three supported versions.
- Fixed a client HUD crash reported by JIANG_XU when broken armor could reduce the effective armor value below zero.

#### v1.19.2-1.1.0 / v1.20.1-1.0.0 / v1.21.1-1.3.8 — Beta

This beta release brings BetterDurabilityCommunity to Forge 1.19.2, Forge 1.20.1, and NeoForge 1.21.1.

- Broken tools, weapons, and armor keep their item identity but no longer provide their normal attribute bonuses.
- Broken armor remains equipped without providing armor or toughness, so players can repair it without losing the item.
- Broken item names display a localized red `(Broken)` suffix, including the selected-item name above the hotbar.
- Enchantments remain on broken items and their tooltip lines turn red to clearly indicate the item's state.
- The Forge 1.19.2 implementation now matches the tested broken-item handling and presentation of the newer versions.

Please report any compatibility issues with modded tools, armor, or enchantments while these builds are in beta.
