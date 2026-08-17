package darkorg.betterdurability.common.impl;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.Tiers;

/** Create Deployer-only tool consumption policy. */
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

    public static int usableDamage(ItemStack stack) {
        return Math.max(0, stack.getMaxDamage() - 2);
    }
}
