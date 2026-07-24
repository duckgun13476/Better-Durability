package PinkCats.betterdurability.event;


import PinkCats.betterdurability.BetterDurability;
import PinkCats.betterdurability.event.ItemDurabilityEvent.ItemUsage;
import PinkCats.betterdurability.setup.ConfigurationHandler;
import PinkCats.betterdurability.util.VanillaDamageableType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

/**
 * Core logic is here.
 */
@EventBusSubscriber(modid = BetterDurability.MOD_ID)
public class DurabilityEventsHandler {
    public static boolean isBlacklisted(Item targetItem, VanillaDamageableType itemType) {
        return ConfigurationHandler.DISABLED_CATEGORIES.contains(itemType.category)
            || ConfigurationHandler.DISABLED_TYPES.contains(itemType)
            || ConfigurationHandler.BLACKLISTED_ITEMS.contains(targetItem);
    }

    public static boolean isWhitelisted(Item targetItem) {
        return ConfigurationHandler.WHITELISTED_ITEMS.contains(targetItem)
            && !ConfigurationHandler.BLACKLISTED_ITEMS.contains(targetItem);
    }

    /**
     * Applies the same broken-tool policy to automation entry points that do not
     * produce NeoForge player interaction events, such as Create deployers.
     */
    public static boolean canUseTool(ItemStack targetStack, ItemUsage.Type usageType) {
        return ItemUsage.check(targetStack, usageType)
            && !VanillaDamageableType.isItemKnownBrokenAnother(targetStack);
    }

    @SubscribeEvent
    public static void onItemBreaking(ItemDurabilityEvent.ItemBreaking event) {
        if (hasForcedEquipLock(event.targetStack)) {
            return;
        }

        Item targetItem = event.targetStack.getItem();
        if (targetItem.isDamageable(event.targetStack)&& isWhitelisted(targetItem)) {
            event.reserveDurability = 2;
            return;
        }
        VanillaDamageableType itemType = VanillaDamageableType.getTypeByItem(targetItem);
        if (itemType != null && !isBlacklisted(targetItem, itemType)) {
            event.reserveDurability = itemType.protectValue;
        }
    }

    @SubscribeEvent
    public static void onItemUsage(ItemDurabilityEvent.ItemUsage event) {
        Item targetItem = event.targetStack.getItem();
        VanillaDamageableType itemType = VanillaDamageableType.getTypeByItem(targetItem);
        if (itemType != null && itemType.isItemBroken(event.targetStack) && !isBlacklisted(targetItem, itemType)) {
            event.setCanceled(true);
        }
    }

    private static boolean hasForcedEquipLock(ItemStack stack) {
        return EnchantmentHelper.has(stack, EnchantmentEffectComponents.PREVENT_ARMOR_CHANGE);
    }
}
