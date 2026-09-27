package darkorg.betterdurability.common.impl;

import darkorg.betterdurability.common.config.BetterDurabilityConfig;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;

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
        return BetterDurabilityConfig.isDeployerConsumable(stack.getItem());
    }

    /** The warning is relevant only when Create can actually consume the item. */
    public static boolean shouldShowConsumableWarning(ItemStack stack) {
        return ModList.get().isLoaded("create") && destroysWhenBroken(stack);
    }

}
