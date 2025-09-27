package darkorg.betterdurability.forge.event;

import darkorg.betterdurability.common.BetterDurability;
import darkorg.betterdurability.common.event.ModClientEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BetterDurability.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class ForgeClientEvents {
    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent pEvent) {
        ModClientEvents.onItemTooltip(pEvent.getItemStack(), pEvent.getFlags(), pEvent.getToolTip());
    }
}
