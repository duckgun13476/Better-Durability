# Changelog

## BDC-combined (unreleased)

1. Rename the player-visible mod name to BetterDurabilityCommunity while retaining the `betterdurability` mod id for configuration and world compatibility.
2. Establish `BDC-combined` as the 1.21.x NeoForge maintenance branch; older-version maintenance is intentionally deferred.
3. Extract shared blacklist, whitelist, and broken-tool usability decisions into `DurabilityPolicy`.
4. Route player interaction and Create Deployer compatibility bridges through the same durability policy, preserving existing broken-tool and non-damageable-armor behavior.
5. Update project documentation to describe the NeoForge 1.21.1 target.
