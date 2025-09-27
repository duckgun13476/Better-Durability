package darkorg.betterdurability.fabric.platform;

import darkorg.betterdurability.common.BetterDurability;
import darkorg.betterdurability.common.platform.services.IEventHelper;
import darkorg.betterdurability.fabric.event.FabricClientEvents;
import darkorg.betterdurability.fabric.event.FabricEvents;

public class FabricEventHelper implements IEventHelper {
    @Override
    public void initListeners() {
        BetterDurability.LOGGER.debug("Registering event listeners for Fabric");
        FabricEvents.init();
    }

    @Override
    public void initClientListeners() {
        FabricClientEvents.init();
    }
}
