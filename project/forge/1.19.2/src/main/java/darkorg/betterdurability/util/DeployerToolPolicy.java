package darkorg.betterdurability.util;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.Tiers;

/**
 * Deployer-only durability policy. The class-name check keeps Create optional
 * for this legacy build while still recognizing its fake player at runtime.
 */
public final class DeployerToolPolicy {
    private static final String DEPLOYER_FAKE_PLAYER =
            "com.simibubi.create.content.kinetics.deployer.DeployerFakePlayer";

    private DeployerToolPolicy() {
    }

    public static boolean isDeployer(LivingEntity entity) {
        return entity != null && DEPLOYER_FAKE_PLAYER.equals(entity.getClass().getName());
    }

    /** Wood and iron tools are deliberately consumable in a Create Deployer. */
    public static boolean destroysWhenBroken(ItemStack stack) {
        if (!(stack.getItem() instanceof TieredItem tieredItem)) {
            return false;
        }
        return tieredItem.getTier() == Tiers.WOOD || tieredItem.getTier() == Tiers.IRON;
    }

    public static int usableDamage(ItemStack stack, int brokenThreshold) {
        return Math.max(0, stack.getMaxDamage() - brokenThreshold - 1);
    }
}
