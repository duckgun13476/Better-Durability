package darkorg.betterdurability.fabric.event;

import darkorg.betterdurability.common.event.ModClientEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;

public abstract class FabricClientEvents {
    public static void init() {
        onItemTooltip();
    }

    public static void onItemTooltip() {
        ItemTooltipCallback.EVENT.register(ModClientEvents::onItemTooltip);
    }
}