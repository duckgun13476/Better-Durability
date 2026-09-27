package darkorg.betterdurability.util;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModList;

import java.util.List;

/**
 * Deployer-only durability policy. Create remains an optional dependency and
 * is recognized through its fake-player class name.
 */
public final class DeployerToolPolicy {
    private static final List<String> LEGACY_DEFAULT_CONSUMABLE_ITEM_IDS = List.of(
            "minecraft:wooden_sword", "minecraft:wooden_shovel", "minecraft:wooden_pickaxe", "minecraft:wooden_axe", "minecraft:wooden_hoe",
            "minecraft:stone_sword", "minecraft:stone_shovel", "minecraft:stone_pickaxe", "minecraft:stone_axe", "minecraft:stone_hoe",
            "minecraft:iron_sword", "minecraft:iron_shovel", "minecraft:iron_pickaxe", "minecraft:iron_axe", "minecraft:iron_hoe"
    );
    private static final List<String> DEFAULT_CONSUMABLE_ITEM_IDS = List.of(
            "minecraft:wooden_sword", "minecraft:wooden_shovel", "minecraft:wooden_pickaxe", "minecraft:wooden_axe", "minecraft:wooden_hoe",
            "minecraft:stone_sword", "minecraft:stone_shovel", "minecraft:stone_pickaxe", "minecraft:stone_axe", "minecraft:stone_hoe",
            "minecraft:iron_sword", "minecraft:iron_shovel", "minecraft:iron_pickaxe", "minecraft:iron_axe", "minecraft:iron_hoe",
            "ae2:fluix_sword", "ae2:fluix_shovel", "ae2:fluix_pickaxe", "ae2:fluix_axe", "ae2:fluix_hoe"
    );
    private static final String MODERN_DEPLOYER_FAKE_PLAYER =
            "com.simibubi.create.content.kinetics.deployer.DeployerFakePlayer";
    private static final String LEGACY_DEPLOYER_FAKE_PLAYER =
            "com.simibubi.create.content.contraptions.components.deployer.DeployerFakePlayer";
    public static final ForgeConfigSpec SERVER_CONFIG;
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> CONSUMABLE_ITEM_IDS;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.comment("Create Deployer settings").push("deployer");
        CONSUMABLE_ITEM_IDS = builder
                .comment("Tools that disappear when a Create Deployer exhausts them.")
                .comment("All other Deployer tools remain broken and stop the machine. Use fully qualified item IDs.")
                .defineList("consumableItemIds", defaultConsumableItemIds(), value -> value instanceof String);
        builder.pop();
        SERVER_CONFIG = builder.build();
    }

    private DeployerToolPolicy() {
    }

    public static boolean isDeployer(LivingEntity entity) {
        if (entity == null) {
            return false;
        }

        String className = entity.getClass().getName();
        return MODERN_DEPLOYER_FAKE_PLAYER.equals(className) || LEGACY_DEPLOYER_FAKE_PLAYER.equals(className);
    }

    public static boolean destroysWhenBroken(ItemStack stack) {
        Item item = stack.getItem();
        List<? extends String> configuredIds = CONSUMABLE_ITEM_IDS.get();
        List<? extends String> effectiveIds = configuredIds.equals(LEGACY_DEFAULT_CONSUMABLE_ITEM_IDS)
                ? DEFAULT_CONSUMABLE_ITEM_IDS
                : configuredIds;
        return effectiveIds.contains(item.getRegistryName().toString());
    }

    public static boolean shouldShowConsumableWarning(ItemStack stack) {
        return ModList.get().isLoaded("create") && destroysWhenBroken(stack);
    }

    private static List<String> defaultConsumableItemIds() {
        return DEFAULT_CONSUMABLE_ITEM_IDS;
    }
}
