package PinkCats.betterdurability.durability;

import PinkCats.betterdurability.event.ItemDurabilityEvent.ItemUsage;
import PinkCats.betterdurability.setup.ConfigurationHandler;
import PinkCats.betterdurability.util.VanillaDamageableType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/**
 * Shared durability decisions used by player-event and automation bridges.
 *
 * <p>Keep policy here so compatibility mixins cannot drift from normal player
 * interaction behavior.</p>
 */
public final class DurabilityPolicy {
    private DurabilityPolicy() {
    }

    public static boolean isBlacklisted(Item item, VanillaDamageableType type) {
        return ConfigurationHandler.DISABLED_CATEGORIES.contains(type.category)
                || ConfigurationHandler.DISABLED_TYPES.contains(type)
                || ConfigurationHandler.BLACKLISTED_ITEMS.contains(item);
    }

    public static boolean isWhitelisted(Item item) {
        return ConfigurationHandler.WHITELISTED_ITEMS.contains(item)
                && !ConfigurationHandler.BLACKLISTED_ITEMS.contains(item);
    }

    /**
     * Applies the normal item-usage event and the whitelist broken-item rule to
     * automation entry points that bypass NeoForge player interaction events.
     */
    public static boolean canUseTool(ItemStack stack, ItemUsage.Type usageType) {
        return ItemUsage.check(stack, usageType)
                && !VanillaDamageableType.isWhitelistedItemKnownBroken(stack);
    }
}
