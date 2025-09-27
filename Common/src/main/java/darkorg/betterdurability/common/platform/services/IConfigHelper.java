package darkorg.betterdurability.common.platform.services;

import net.minecraftforge.common.ForgeConfigSpec;

public interface IConfigHelper {
    void initClientConfigs();

    void initServerConfigs();

    void registerClientConfig(ForgeConfigSpec pConfigSpec, String pFileName);

    void registerServerConfig(ForgeConfigSpec pConfigSpec, String pFileName);

    void initListeners();
}
