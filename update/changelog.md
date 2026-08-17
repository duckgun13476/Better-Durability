# Changelog

#### v1.19.2-1.1.0 / v1.20.1-1.0.0 / v1.21.1-1.3.8

- Added verified Forge 1.19.2, Forge 1.20.1, and NeoForge 1.21.1 releases.
- Broken items now show a localized red `(Broken)` suffix in their display name,
  including the hotbar selection name.
- Broken armor remains equipped but no longer grants armor or toughness; broken
  tools and weapons no longer grant their normal attribute bonuses.
- Enchantments are retained and their tooltip lines turn red while the item is
  broken.
- Forge 1.19.2 now receives the same broken-item presentation, armor handling,
  attribute removal, and durability-threshold protection as the modern builds.

## BDC-combined (unreleased)

1. Rename the player-visible mod name to BetterDurabilityCommunity while retaining the `betterdurability` mod id for configuration and world compatibility.
2. Establish `BDC-combined` as the 1.21.x NeoForge maintenance branch; older-version maintenance is intentionally deferred.
3. Extract shared blacklist, whitelist, and broken-tool usability decisions into `DurabilityPolicy`.
4. Route player interaction and Create Deployer compatibility bridges through the same durability policy, preserving existing broken-tool and non-damageable-armor behavior.
5. Update project documentation to describe the NeoForge 1.21.1 target.
6. Replace the legacy Reforged logo text with Community and register the bundled image as the NeoForge mod-list icon.
7. Reorganize the repository into separate Forge 1.20.1 and NeoForge 1.21.1 version projects with root aggregate build tasks.
8. Add the BDC two-version CurseForge, Modrinth, and soft-failure Discord publication workflow.
9. Merge the upstream 1.20.1 Common and Forge durability implementation into the active Forge source set while retaining the BDC combined build layout.
10. Restore the useful upstream Forge 1.16.5, 1.18.2, and 1.19.2 version sources under the combined project structure.
