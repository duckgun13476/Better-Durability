# BetterDurabilityCommunity

BetterDurabilityCommunity preserves supported tools and equipment when their
durability is exhausted. Instead of disappearing, an item enters a disabled
**Broken** state and can be used again after it is repaired.

## Supported versions

- **Forge 1.16.5**
- **Forge 1.18.2**
- **Forge 1.19.2**
- **Forge 1.20.1**
- **NeoForge 1.21.1**

Install the mod on both the client and server. The server enforces gameplay
behavior, while the client provides broken-item names, tooltips, and visual
feedback. Compatibility features vary where older Minecraft APIs do not expose
the same hooks as modern versions.

## Features

- Preserves supported tools, weapons, armor, shields, and other damageable
  equipment at their broken durability threshold.
- Marks broken items with a red localized **(Broken)** suffix.
- Removes the normal combat, mining, armor, toughness, and shielding benefits
  of broken equipment.
- Keeps enchantments on the item and renders their tooltip entries in red while
  broken, while suppressing effects that must not remain active, including
  Frost Walker on broken boots.
- Provides server-side item and category blacklists/whitelists.
- Does not attach extra tag data to mark an item as broken.

## Create automation

Supported versions integrate with Create Deployers without making Create a
required dependency.

- Configured consumable tools disappear normally when a Deployer exhausts
  them, allowing automated production lines to continue.
- Unlisted tools remain broken in the Deployer and stop the machine, preserving
  valuable equipment for repair.
- The default consumable list contains wooden, stone, and iron tools plus all
  five Applied Energistics 2 Fluix tools.
- Enchanted tools are protected by default even when they are in the consumable
  list. Disable `protectEnchantedItems` to let Deployers consume them normally.
- Existing installations that still use the previous default list receive the
  new Fluix defaults automatically; customized lists remain unchanged.
- When Create is installed, tools that can actually be consumed display a red
  warning explaining that they will disappear if exhausted by a Deployer.

The configurable Deployer policy is available on Forge 1.18.2, Forge 1.19.2,
Forge 1.20.1, and NeoForge 1.21.1.

## Configuration

Gameplay settings are server-side. Use the generated configuration file for
your version to control protected items and the Create Deployer consumable
list.

## Project origin

BetterDurabilityCommunity is a community-maintained fork of
[Better Durability](https://github.com/Darkorg69/Better-Durability) by
Darkorg69. It preserves and extends the original MIT-licensed code and assets,
with continued attribution in [LICENCE](LICENCE).

![BetterDurabilityCommunity](https://i.ibb.co/7YgFSf4/better-durability.png)
