package PinkCats.betterdurability.durability;

import PinkCats.betterdurability.setup.ConfigurationHandler;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

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
        return ConfigurationHandler.DEPLOYER_CONSUMABLE_ITEM_IDS.get()
                .contains(stack.getItem().builtInRegistryHolder().key().location().toString());
    }

    public static int usableDamage(ItemStack stack, int brokenThreshold) {
        return Math.max(0, stack.getMaxDamage() - brokenThreshold - 1);
    }
}
