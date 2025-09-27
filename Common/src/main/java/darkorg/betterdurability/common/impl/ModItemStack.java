package darkorg.betterdurability.common.impl;

import darkorg.betterdurability.common.api.UnbreakableItemStack;
import net.minecraft.world.item.ItemStack;

public class ModItemStack {
    /**
     * Checks if the ItemStack is broken.
     */
    public static boolean isBroken(ItemStack pItemStack) {
        return ((UnbreakableItemStack) ((Object) pItemStack)).betterDurability$isBroken();
    }

    /**
     * Checks if the ItemStack can be used.
     */
    public static boolean isUsable(ItemStack pItemStack) {
        return !isBroken(pItemStack);
    }
}
