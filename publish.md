# BetterDurabilityCommunity

## Multi-Version Release

BetterDurabilityCommunity is now available for:

- **Forge 1.16.5**
- **Forge 1.18.2**
- **Forge 1.19.2**
- **Forge 1.20.1**
- **NeoForge 1.21.1**

This release restores and verifies the broken-item system across all three
published targets. Supported equipment is preserved at its broken durability
threshold instead of disappearing.

### Changes

- Broken items are clearly marked with a red localized **(Broken)** suffix.
- Broken armor remains equipped but no longer provides armor or toughness.
- Broken tools and weapons lose their normal attribute bonuses.
- Enchantments are retained; their tooltip entries turn red while the item is
  broken.
- The Forge 1.19.2 build now includes the same broken-item presentation,
  armor handling, attribute removal, and durability protection as the modern
  releases.

BetterDurabilityCommunity prevents supported tools, weapons, armor, shields,
and other damageable equipment from disappearing when their durability is
exhausted. Instead, the item remains in a **broken** state until it is
repaired.

Broken equipment no longer provides its normal mining, combat, defense,
shielding, or relevant enchantment effects. This preserves the item and its
enchantments while preventing it from being used as if it were intact.

## Features

- Keeps supported damageable items from being destroyed at zero durability.
- Applies broken-item restrictions consistently to tools, weapons, armor, and
  shields.
- Supports item and category blacklists/whitelists for server-side control.
- Handles durability-related enchantment behavior, including Thorns, Soul
  Speed, and binding equipment.
- Includes compatibility for Create Deployers and belt deployers, so automation
  follows the same broken-tool rules as normal player interaction.

## Compatibility

The current community-maintained release targets **Forge 1.16.5**, **Forge
1.18.2**, **Forge 1.19.2**, **Forge 1.20.1**, and **NeoForge 1.21.1**.

The mod ID remains `betterdurability` for configuration and world
compatibility with existing installations.

The project is designed for installation on both the client and server. The
server enforces broken-item gameplay behavior, while the client provides the
corresponding names, tooltips, and presentation.

## Project Origin

BetterDurabilityCommunity is a community-maintained fork of
[Better Durability](https://github.com/Darkorg69/Better-Durability) by
Darkorg69. It preserves and extends the original MIT-licensed code and assets,
with continued attribution provided in the included license.

## Configuration

Gameplay settings are server-side. Use the generated
`betterdurability-gameplay.toml` configuration file to define item and category
blacklists or whitelists.
