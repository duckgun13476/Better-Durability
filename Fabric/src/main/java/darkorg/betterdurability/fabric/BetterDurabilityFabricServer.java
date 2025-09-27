package darkorg.betterdurability.fabric;

import darkorg.betterdurability.common.BetterDurability;
import net.fabricmc.api.DedicatedServerModInitializer;

public class BetterDurabilityFabricServer implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        BetterDurability.initServer();
    }
}
