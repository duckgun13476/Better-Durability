# Changelog

#### v1.3.9 — Beta

- Added configurable Create Deployer tool consumption. Wooden, stone, and iron vanilla tools are consumed by default when exhausted; the list can be changed in the server configuration.
- Tools outside that list, including diamond tools, retain their normal broken state in a Create Deployer, causing the Deployer to stop instead of silently restoring usable durability.
- Applied the same Create Deployer behavior to Forge 1.19.2, Forge 1.20.1, and NeoForge 1.21.1.

#### v1.19.2-1.1.0 / v1.20.1-1.0.0 / v1.21.1-1.3.8 — Beta

This beta release brings BetterDurabilityCommunity to Forge 1.19.2, Forge 1.20.1, and NeoForge 1.21.1.

- Broken tools, weapons, and armor keep their item identity but no longer provide their normal attribute bonuses.
- Broken armor remains equipped without providing armor or toughness, so players can repair it without losing the item.
- Broken item names display a localized red `(Broken)` suffix, including the selected-item name above the hotbar.
- Enchantments remain on broken items and their tooltip lines turn red to clearly indicate the item's state.
- The Forge 1.19.2 implementation now matches the tested broken-item handling and presentation of the newer versions.

Please report any compatibility issues with modded tools, armor, or enchantments while these builds are in beta.
