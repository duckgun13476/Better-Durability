package PinkCats.betterdurability.event;

import PinkCats.betterdurability.BetterDurability;
import PinkCats.betterdurability.util.VanillaDamageableType;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = BetterDurability.MOD_ID)
public class ForgeEventsHandler {
    @SubscribeEvent
    public static void onLeftClickBlock(PlayerEvent.BreakSpeed event) {
        if (event.getState().getBlock().getSpeedFactor() != 0.0F) {
            ItemStack targetStack = event.getEntity().getMainHandItem();
            if (!targetStack.isDamageableItem()) return;
            boolean canFunction = ItemDurabilityEvent.ItemUsage.check(targetStack,
                    ItemDurabilityEvent.ItemUsage.Type.TOOL_LEFT_CLICK_BLOCK);
            if (!canFunction || VanillaDamageableType.isItemKnownBrokenAnother(targetStack)) { event.setCanceled(true); }
        }
    }

    @SubscribeEvent
    public static void onLeftClickEntity(AttackEntityEvent event) {
        ItemStack targetStack = event.getEntity().getMainHandItem();
        if (!targetStack.isDamageableItem()) return;
        boolean canFunction = ItemDurabilityEvent.ItemUsage.check(targetStack,
                ItemDurabilityEvent.ItemUsage.Type.TOOL_LEFT_CLICK_ENTITY);
        if (!canFunction || VanillaDamageableType.isItemKnownBrokenAnother(targetStack)) { event.setCanceled(true); }
    }

    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        ItemStack targetStack = event.getItemStack();
        if (!targetStack.isDamageableItem()) return;
        boolean canFunction = ItemDurabilityEvent.ItemUsage.check(targetStack,
                ItemDurabilityEvent.ItemUsage.Type.TOOL_RIGHT_CLICK_ITEM);
        if (!canFunction || VanillaDamageableType.isItemKnownBrokenAnother(targetStack)) { event.setCanceled(true); }
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        ItemStack targetStack = event.getItemStack();
        if (!targetStack.isDamageableItem()) return;
        boolean canFunction = ItemDurabilityEvent.ItemUsage.check(targetStack,
                ItemDurabilityEvent.ItemUsage.Type.TOOL_RIGHT_CLICK_BLOCK);
        if (!canFunction || VanillaDamageableType.isItemKnownBrokenAnother(targetStack)) { event.setCanceled(true); }
    }

    @SubscribeEvent
    public static void onRightClickEntity(PlayerInteractEvent.EntityInteract event) {
        ItemStack targetStack = event.getItemStack();
        if (!targetStack.isDamageableItem()) return;
        boolean canFunction = ItemDurabilityEvent.ItemUsage.check(targetStack,
                ItemDurabilityEvent.ItemUsage.Type.TOOL_RIGHT_CLICK_ENTITY);
        if (!canFunction || VanillaDamageableType.isItemKnownBrokenAnother(targetStack)) { event.setCanceled(true); }
    }

    @SubscribeEvent
    public static void onRightClickEntitySpecific(PlayerInteractEvent.EntityInteractSpecific event) {
        ItemStack targetStack = event.getItemStack();
        if (!targetStack.isDamageableItem()) return;
        boolean canFunction = ItemDurabilityEvent.ItemUsage.check(targetStack,
                ItemDurabilityEvent.ItemUsage.Type.TOOL_RIGHT_CLICK_ENTITY);
        if (!canFunction || VanillaDamageableType.isItemKnownBrokenAnother(targetStack)) { event.setCanceled(true); }
    }
}
