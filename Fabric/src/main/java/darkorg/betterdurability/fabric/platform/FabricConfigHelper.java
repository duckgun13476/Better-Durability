package darkorg.betterdurability.fabric.platform;

import darkorg.betterdurability.common.BetterDurability;
import darkorg.betterdurability.common.config.BetterDurabilityConfig;
import darkorg.betterdurability.common.platform.services.IConfigHelper;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import fuzs.forgeconfigapiport.api.config.v2.ModConfigEvents;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

public class FabricConfigHelper implements IConfigHelper {
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
        ForgeConfigRegistry.INSTANCE.register(BetterDurability.MOD_ID, ModConfig.Type.CLIENT, pConfigSpec, pFileName);
    }

    @Override
    public void registerServerConfig(ForgeConfigSpec pConfigSpec, String pFileName) {
        ForgeConfigRegistry.INSTANCE.register(BetterDurability.MOD_ID, ModConfig.Type.SERVER, pConfigSpec, pFileName);
    }

    @Override
    public void initListeners() {
        ModConfigEvents.loading(BetterDurability.MOD_ID).register(pModConfig -> {
            BetterDurability.LOGGER.debug("Loading mod configuration file: {}", pModConfig.getFileName());
        });

        ModConfigEvents.reloading(BetterDurability.MOD_ID).register(pModConfig -> {
            BetterDurability.LOGGER.debug("Reloading mod configuration file: {}", pModConfig.getFileName());
        });

        ModConfigEvents.unloading(BetterDurability.MOD_ID).register(pModConfig -> {
            BetterDurability.LOGGER.debug("Unloading mod configuration file: {}", pModConfig.getFileName());
        });
    }
}
