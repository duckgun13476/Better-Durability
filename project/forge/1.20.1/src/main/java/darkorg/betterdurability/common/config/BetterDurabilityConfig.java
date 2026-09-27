package darkorg.betterdurability.common.config;

import darkorg.betterdurability.common.BetterDurability;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public abstract class BetterDurabilityConfig {
    private static final String LEGACY_DEFAULT_DEPLOYER_CONSUMABLE_ITEMS = "minecraft:wooden_sword,minecraft:wooden_shovel,minecraft:wooden_pickaxe,minecraft:wooden_axe,minecraft:wooden_hoe,minecraft:stone_sword,minecraft:stone_shovel,minecraft:stone_pickaxe,minecraft:stone_axe,minecraft:stone_hoe,minecraft:iron_sword,minecraft:iron_shovel,minecraft:iron_pickaxe,minecraft:iron_axe,minecraft:iron_hoe";
    private static final String DEFAULT_DEPLOYER_CONSUMABLE_ITEMS = LEGACY_DEFAULT_DEPLOYER_CONSUMABLE_ITEMS + ",ae2:fluix_sword,ae2:fluix_shovel,ae2:fluix_pickaxe,ae2:fluix_axe,ae2:fluix_hoe";
    private static final List<String> AE2_FLUIX_TOOL_IDS = List.of(
            "ae2:fluix_sword", "ae2:fluix_shovel", "ae2:fluix_pickaxe", "ae2:fluix_axe", "ae2:fluix_hoe"
    );
    public static final Gameplay GAMEPLAY;
    public static final ForgeConfigSpec GAMEPLAY_SPEC;
    public static final String GAMEPLAY_CONFIG_FILE_NAME = BetterDurability.MOD_ID + "-gameplay.toml";

    static {
        final Pair<Gameplay, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Gameplay::new);
        GAMEPLAY = specPair.getLeft();
        GAMEPLAY_SPEC = specPair.getRight();
    }

    public static class Gameplay {
        public final ConfigValue<Double> defaultItemDestroySpeed;
        public final ConfigValue<Double> defaultItemBrokenDestroySpeed;
        public final ConfigValue<String> blacklist;
        public final ConfigValue<String> deployerConsumableItems;
        public final ForgeConfigSpec.BooleanValue protectEnchantedItems;

        Gameplay(ForgeConfigSpec.Builder pBuilder) {
            pBuilder.comment("Settings related to gameplay").push("gameplay");

            defaultItemDestroySpeed = pBuilder
                    .comment("Change default vanilla item destroy speed")
                    .comment("This value should always be higher than 'defaultBrokenSpeed'")
                    .defineInRange("defaultItemDestroySpeed", 1.0F, 1.0F, Double.MAX_VALUE);

            defaultItemBrokenDestroySpeed = pBuilder
                    .comment("Change default broken item destroy speed.")
                    .comment("This value should always be equal or lower than 'defaultDestroySpeed'")
                    .comment("Setting this value to 0.0, will essentially make broken tools not able to break a block")
                    .comment("If value is > 0.0, then broken items will still be able to break a block")
                    .comment("Note: Regardless of this value, broken items will never be able to 'harvest' a block, if the block requires the correct tool to drop.")
                    .comment("Example: Broken pickaxe will not drop stone, even if it can break the stone")
                    .defineInRange("defaultItemBrokenDestroySpeed", 1.0F, 0.0F, Double.MAX_VALUE);

            blacklist = pBuilder
                    .comment("Items in this list will follow vanilla durability logic")
                    .define("blacklist", "minecraft:carrot_on_a_stick, minecraft:warped_fungus_on_a_stick");

            deployerConsumableItems = pBuilder
                    .comment("Create Deployer tools that disappear when exhausted.")
                    .comment("All other Deployer tools remain broken and stop the machine. Use item IDs separated by commas.")
                    .define("deployerConsumableItems", DEFAULT_DEPLOYER_CONSUMABLE_ITEMS);

            protectEnchantedItems = pBuilder
                    .comment("Protect enchanted tools even when they are listed as Deployer consumables.")
                    .comment("Protected enchanted tools remain broken and stop the machine.")
                    .define("protectEnchantedItems", true);
            pBuilder.pop();
        }
    }

    private static List<Item> blacklist;

    public static boolean isBlacklisted(Item pItem) {
        if (BetterDurabilityConfig.blacklist == null) {
            BetterDurabilityConfig.blacklist = BetterDurabilityConfig.parseItemListConfig(BetterDurabilityConfig.GAMEPLAY.blacklist);
        }
        return BetterDurabilityConfig.blacklist.contains(pItem);
    }

    public static boolean isDeployerConsumable(Item pItem) {
        String configuredIds = BetterDurabilityConfig.GAMEPLAY.deployerConsumableItems.get().replaceAll("\\s+", "");
        String itemId = BuiltInRegistries.ITEM.getKey(pItem).toString();
        if (configuredIds.equals(LEGACY_DEFAULT_DEPLOYER_CONSUMABLE_ITEMS) && AE2_FLUIX_TOOL_IDS.contains(itemId)) {
            return true;
        }
        return BetterDurabilityConfig.parseItemListConfig(BetterDurabilityConfig.GAMEPLAY.deployerConsumableItems).contains(pItem);
    }

    public static List<Item> parseItemListConfig(ConfigValue<String> pConfigList) {
        List<Item> items = new ArrayList<>();
        //Strip all white-spaces from the config value
        String config = pConfigList.get().replaceAll("\\s+", "");
        if (!config.isEmpty()) {
            if (config.matches("^(\\w+:\\w+,)*(\\w+:\\w+)$")) {
                String[] split = config.split(",");
                for (String entry : split) {
                    String[] splitEntry = entry.strip().split(":");
                    items.add(BuiltInRegistries.ITEM.get(new ResourceLocation(splitEntry[0], splitEntry[1])));
                }
            } else {
                BetterDurability.LOGGER.error("Malformed [blacklist] config value! Cannot parse: [{}]", config);
            }
        }
        return items;
    }
}
