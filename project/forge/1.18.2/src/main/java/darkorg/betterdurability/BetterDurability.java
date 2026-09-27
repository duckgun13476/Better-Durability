package darkorg.betterdurability;

import darkorg.betterdurability.util.DeployerToolPolicy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(BetterDurability.MOD_ID)
public class BetterDurability {
    public static final String MOD_ID = "betterdurability";

    public IEventBus forgeBus = MinecraftForge.EVENT_BUS;

    public BetterDurability() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, DeployerToolPolicy.SERVER_CONFIG);
        forgeBus.register(this);
    }
}
