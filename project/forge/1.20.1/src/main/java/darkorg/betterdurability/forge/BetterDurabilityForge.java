package darkorg.betterdurability.forge;

import darkorg.betterdurability.common.BetterDurability;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(BetterDurability.MOD_ID)
public class BetterDurabilityForge {
    public static IEventBus MOD_EVENT_BUS;

    public BetterDurabilityForge() {
        MOD_EVENT_BUS = FMLJavaModLoadingContext.get().getModEventBus();
        BetterDurability.init();
        BetterDurability.initClient();
    }
}
