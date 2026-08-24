# BetterDurabilityCommunity

* A mod to prevent your precious tools from breaking!

## Notice

* This project currently provides builds for **Forge 1.19.2**, **Forge 1.20.1**, and **NeoForge 1.21.1**.
* Install the mod on both the client and server. Server-side code enforces broken-item behavior, while client-side code provides its names, tooltips, and presentation.

## Project origin

BetterDurabilityCommunity is a community-maintained fork of [Better Durability](https://github.com/Darkorg69/Better-Durability) by Darkorg69. It preserves and extends the original MIT-licensed code and assets, with continued attribution in [LICENCE](LICENCE).

## What does this mod do?

* When the tool you are using reaches **critical durability** (break on next use), it will render as **Broken** with
  its functionality disabled.
  
  - For **Swords, Pickaxes, Shovels, Axes, Hoes, Bows, Crossbows, Tridents, Fishing Rods, Shears and Flint**: you can
    try to use them, but nothing will happen.
  - For **Armors**: their toughness and defense will be treated as zero, as if you never put them on, but enchantment
    will still work (except for **Thorns** and **Soul Speed**, for they cost durability).
  - For **Shields**: you can use them to block, but arrow (and other projectiles) will still hurt you as if you never
    blocked.
* This mod does not attach tag data to items to mark "Broken".
* Currently, this mod only injects on NeoForge events and vanilla code, so there is possibility that a certain mod can
  still cause tools to break.

![](https://i.ibb.co/7YgFSf4/better-durability.png)

## To-do list

* Add some more obvious effect to indicate that tool is broken
