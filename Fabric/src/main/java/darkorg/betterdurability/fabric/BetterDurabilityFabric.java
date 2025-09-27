package darkorg.betterdurability.fabric;

import darkorg.betterdurability.common.BetterDurability;
import net.fabricmc.api.ModInitializer;

public class BetterDurabilityFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        BetterDurability.init();
    }
}
