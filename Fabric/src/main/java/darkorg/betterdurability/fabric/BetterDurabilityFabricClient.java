package darkorg.betterdurability.fabric;

import darkorg.betterdurability.common.BetterDurability;
import net.fabricmc.api.ClientModInitializer;

public class BetterDurabilityFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BetterDurability.initClient();
    }
}
