package PinkCats.betterdurability.durability;

import PinkCats.betterdurability.setup.ConfigurationHandler;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;

import java.util.List;

/** Create Deployer-only tool consumption policy. */
public final class DeployerToolPolicy {
    private static final String DEPLOYER_FAKE_PLAYER =
            "com.simibubi.create.content.kinetics.deployer.DeployerFakePlayer";

    private DeployerToolPolicy() {
    }

    public static boolean isDeployer(LivingEntity entity) {
        return entity != null && DEPLOYER_FAKE_PLAYER.equals(entity.getClass().getName());
    }

    /** Configured tools are deliberately consumable in a Create Deployer. */
    public static boolean destroysWhenBroken(ItemStack stack) {
        List<? extends String> configuredIds = ConfigurationHandler.DEPLOYER_CONSUMABLE_ITEM_IDS.get();
        List<? extends String> effectiveIds = configuredIds.equals(ConfigurationHandler.LEGACY_DEFAULT_DEPLOYER_CONSUMABLE_ITEM_IDS)
                ? ConfigurationHandler.DEFAULT_DEPLOYER_CONSUMABLE_ITEM_IDS
                : configuredIds;
        return effectiveIds
                .contains(stack.getItem().builtInRegistryHolder().key().location().toString());
    }

    /** The warning is relevant only when Create can actually consume the item. */
    public static boolean shouldShowConsumableWarning(ItemStack stack) {
        return ModList.get().isLoaded("create") && destroysWhenBroken(stack);
    }

}
