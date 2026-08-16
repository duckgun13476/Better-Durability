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

---

## 简体中文

BetterDurabilityCommunity 让适用的工具、武器、护甲、盾牌及其他耐久物品在
耐久耗尽时不会直接消失，而是进入“已损坏”状态，等待修复。

损坏装备不会再提供正常的挖掘、战斗、防御、格挡及相关附魔效果。这样既能
保留物品和附魔，也不会让它们在损坏后继续像完整装备一样工作。

### 功能

- 防止适用的耐久物品在耐久归零时被直接销毁。
- 对工具、武器、护甲和盾牌统一应用损坏状态限制。
- 支持按物品或类别配置黑名单与白名单。
- 处理荆棘、灵魂疾行和绑定装备等与耐久相关的附魔行为。
- 兼容 Create 部署器与传送带部署器，使自动化与玩家操作遵循相同的损坏工具规则。

### 兼容性与配置

当前社区维护版本面向 **NeoForge 1.21.1**。为保持现有配置和存档兼容，模组 ID
仍为 `betterdurability`。

游戏规则为服务端配置；可在生成的 `betterdurability-gameplay.toml` 中设置物品及
类别的黑名单或白名单。
