package darkorg.betterdurability.util;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

/**
 * Deployer-only durability policy. The class-name check keeps Create optional
 * for this legacy build while still recognizing its fake player at runtime.
 */
public final class DeployerToolPolicy {
    private static final String DEPLOYER_FAKE_PLAYER =
            "com.simibubi.create.content.kinetics.deployer.DeployerFakePlayer";
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
        return entity != null && DEPLOYER_FAKE_PLAYER.equals(entity.getClass().getName());
    }

    /** Configured tools are deliberately consumable in a Create Deployer. */
    public static boolean destroysWhenBroken(ItemStack stack) {
        Item item = stack.getItem();
        return CONSUMABLE_ITEM_IDS.get().contains(item.builtInRegistryHolder().key().location().toString());
    }

    public static int usableDamage(ItemStack stack, int brokenThreshold) {
        return Math.max(0, stack.getMaxDamage() - brokenThreshold - 1);
    }

    private static List<String> defaultConsumableItemIds() {
        return List.of(
                "minecraft:wooden_sword", "minecraft:wooden_shovel", "minecraft:wooden_pickaxe", "minecraft:wooden_axe", "minecraft:wooden_hoe",
                "minecraft:stone_sword", "minecraft:stone_shovel", "minecraft:stone_pickaxe", "minecraft:stone_axe", "minecraft:stone_hoe",
                "minecraft:iron_sword", "minecraft:iron_shovel", "minecraft:iron_pickaxe", "minecraft:iron_axe", "minecraft:iron_hoe"
        );
    }
}
