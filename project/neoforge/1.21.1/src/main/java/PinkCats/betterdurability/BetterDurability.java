package PinkCats.betterdurability;

import PinkCats.betterdurability.setup.ConfigurationHandler;
import PinkCats.betterdurability.util.NaiveLoggerWrapper;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;

@Mod(BetterDurability.MOD_ID)
public class BetterDurability {
    public static final String MOD_ID = "betterdurability";
    public static final NaiveLoggerWrapper LOGGER = new NaiveLoggerWrapper(LogManager.getLogger())
            .withPrefix("[Better Durability] ");

    public BetterDurability(ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.SERVER, ConfigurationHandler.SERVER_CONFIG);
    }


}
