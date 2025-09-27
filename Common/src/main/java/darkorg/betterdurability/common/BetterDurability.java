package darkorg.betterdurability.common;

import darkorg.betterdurability.common.platform.Services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BetterDurability {
    public static final String MOD_ID = "betterdurability";
    public static final String MOD_NAME = "Better Durability";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static void init() {
        LOGGER.debug("COMMON initialization started on {}! We are currently in a {} environment!",
                Services.PLATFORM_HELPER.getPlatformName(),
                Services.PLATFORM_HELPER.getEnvironmentName()
        );

        //Init server configs before anything else!
        Services.CONFIG_HELPER.initServerConfigs();

        //Init event listeners for configuration files.
        Services.CONFIG_HELPER.initListeners();

        //Init mod event listeners
        Services.EVENT_HELPER.initListeners();
    }

    public static void initClient() {
        LOGGER.debug("CLIENT initialization started on {}! We are currently in a {} environment!",
                Services.PLATFORM_HELPER.getPlatformName(),
                Services.PLATFORM_HELPER.getEnvironmentName()
        );

        //Init client configs before anything else!
        Services.CONFIG_HELPER.initClientConfigs();
        //Init event listeners for configuration files.
        Services.EVENT_HELPER.initClientListeners();
    }

    public static void initServer() {
        LOGGER.debug("SERVER initialization started on {}! We are currently in a {} environment!",
                Services.PLATFORM_HELPER.getPlatformName(),
                Services.PLATFORM_HELPER.getEnvironmentName()
        );
    }

    public static void initDataGenerator() {
        LOGGER.debug("DATA_GENERATOR initialization started on {}! We are currently in a {} environment!",
                Services.PLATFORM_HELPER.getPlatformName(),
                Services.PLATFORM_HELPER.getEnvironmentName()
        );
    }
}