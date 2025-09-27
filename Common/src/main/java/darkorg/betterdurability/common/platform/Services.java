package darkorg.betterdurability.common.platform;

import darkorg.betterdurability.common.BetterDurability;
import darkorg.betterdurability.common.platform.services.IConfigHelper;
import darkorg.betterdurability.common.platform.services.IEventHelper;
import darkorg.betterdurability.common.platform.services.IPlatformHelper;

import java.util.ServiceLoader;

public abstract class Services {
    public static final IConfigHelper CONFIG_HELPER = load(IConfigHelper.class);
    public static final IEventHelper EVENT_HELPER = load(IEventHelper.class);
    public static final IPlatformHelper PLATFORM_HELPER = load(IPlatformHelper.class);

    public static <T> T load(Class<T> pService) {
        final T loadedService = ServiceLoader.load(pService).findFirst().orElseThrow(() -> {
            return new NullPointerException("Failed to load service for " + pService.getName());
        });

        BetterDurability.LOGGER.debug("Loaded {} for service {}", loadedService, pService);

        return loadedService;
    }
}