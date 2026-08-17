package PinkCats.betterdurability.event;


import PinkCats.betterdurability.BetterDurability;
import PinkCats.betterdurability.durability.DurabilityPolicy;
import PinkCats.betterdurability.setup.ConfigurationHandler;
import PinkCats.betterdurability.util.VanillaDamageableType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;

/**
 * Core logic is here.
 */
@EventBusSubscriber(modid = BetterDurability.MOD_ID)
public class DurabilityEventsHandler {
    @SubscribeEvent
    public static void onItemBreaking(ItemDurabilityEvent.ItemBreaking event) {
        if (hasForcedEquipLock(event.targetStack)) {
            return;
        }

        Item targetItem = event.targetStack.getItem();
        if (targetItem.isDamageable(event.targetStack) && DurabilityPolicy.isWhitelisted(targetItem)) {
            event.reserveDurability = 2;
            return;
        }
        VanillaDamageableType itemType = VanillaDamageableType.getTypeByItem(targetItem);
        if (itemType != null && !DurabilityPolicy.isBlacklisted(targetItem, itemType)) {
            event.reserveDurability = itemType.protectValue;
        }
    }

    @SubscribeEvent
    public static void onItemUsage(ItemDurabilityEvent.ItemUsage event) {
        Item targetItem = event.targetStack.getItem();
        VanillaDamageableType itemType = VanillaDamageableType.getTypeByItem(targetItem);
        if (itemType != null && itemType.isItemBroken(event.targetStack)
                && !DurabilityPolicy.isBlacklisted(targetItem, itemType)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onItemAttributeModifiers(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();
        VanillaDamageableType itemType = VanillaDamageableType.getTypeByItem(item);
        if ((itemType != null && itemType.isItemBroken(stack) && !DurabilityPolicy.isBlacklisted(item, itemType))
                || VanillaDamageableType.isWhitelistedItemKnownBroken(stack)) {
            event.clearModifiers();
        }
    }

    private static boolean hasForcedEquipLock(ItemStack stack) {
        return EnchantmentHelper.has(stack, EnchantmentEffectComponents.PREVENT_ARMOR_CHANGE);
    }
}
