package darkorg.betterdurability.forge.event;

import darkorg.betterdurability.common.BetterDurability;
import darkorg.betterdurability.common.event.ModEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BetterDurability.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class ForgeEvents {
    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock pEvent) {
        Player player = pEvent.getEntity();
        pEvent.setCancellationResult(ModEvents.onRightClickBlock(player, player.level(), pEvent.getHand(), null));
    }

    @SubscribeEvent
    public static void onRightClickEntitySpecific(PlayerInteractEvent.EntityInteractSpecific pEvent) {
        Player player = pEvent.getEntity();
        pEvent.setCancellationResult(ModEvents.onRightClickEntitySpecific(player, player.level(), pEvent.getHand(), pEvent.getTarget(), null));
    }

    @SubscribeEvent
    public static void onRightClickEntity(PlayerInteractEvent.EntityInteract pEvent) {
        Player player = pEvent.getEntity();
        pEvent.setCancellationResult(ModEvents.onRightClickEntity(player, player.level(), pEvent.getHand(), pEvent.getTarget(), null));
    }

    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem pEvent) {
        Player player = pEvent.getEntity();
        pEvent.setCancellationResult(ModEvents.onRightClickItem(player, player.level(), pEvent.getHand()).getResult());
    }
}