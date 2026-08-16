# BetterDurabilityCommunity

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

The current community-maintained release targets **NeoForge 1.21.1**.

The mod ID remains `betterdurability` for configuration and world
compatibility with existing installations.

## Configuration

Gameplay settings are server-side. Use the generated
`betterdurability-gameplay.toml` configuration file to define item and category
blacklists or whitelists.
