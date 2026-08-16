# Changelog

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
