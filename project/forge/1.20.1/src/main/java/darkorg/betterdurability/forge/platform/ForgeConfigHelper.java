package darkorg.betterdurability.forge.platform;

import darkorg.betterdurability.common.BetterDurability;
import darkorg.betterdurability.common.config.BetterDurabilityConfig;
import darkorg.betterdurability.common.platform.services.IConfigHelper;
import darkorg.betterdurability.forge.BetterDurabilityForge;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;

public final class ForgeConfigHelper implements IConfigHelper {
    @Override
    public void initClientConfigs() {
        //registerClientConfig(BetterDurabilityConfig.CLIENT_SPEC, BetterDurabilityConfig.CLIENT_CONFIG_FILE_NAME);
    }

    @Override
    public void initServerConfigs() {
        registerServerConfig(BetterDurabilityConfig.GAMEPLAY_SPEC, BetterDurabilityConfig.GAMEPLAY_CONFIG_FILE_NAME);
    }

    @Override
    public void registerClientConfig(ForgeConfigSpec pConfigSpec, String pFileName) {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, pConfigSpec, pFileName);
    }

    @Override
    public void registerServerConfig(ForgeConfigSpec pConfigSpec, String pFileName) {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, pConfigSpec, pFileName);
    }

    @Override
    public void initListeners() {
        BetterDurabilityForge.MOD_EVENT_BUS.addListener((ModConfigEvent.Loading pEvent) -> {
            BetterDurability.LOGGER.debug("Loading mod config file: {}", pEvent.getConfig().getFileName());
        });

        BetterDurabilityForge.MOD_EVENT_BUS.addListener((ModConfigEvent.Reloading pEvent) -> {
            BetterDurability.LOGGER.debug("Reloading mod config file: {}", pEvent.getConfig().getFileName());
        });

        BetterDurabilityForge.MOD_EVENT_BUS.addListener((ModConfigEvent.Unloading pEvent) -> {
            BetterDurability.LOGGER.debug("Unloading mod config file: {}", pEvent.getConfig().getFileName());
        });
    }
}
