package darkorg.betterdurability.common.api;

/**
 * <p>Mixin Interface.</p>
 * <p>Implemented in: {@link net.minecraft.world.item.ItemStack}</p>
 * <p>Mixin: {@link darkorg.betterdurability.common.mixin.MixinItemStack}</p>
 * <p>Exposed via: {@link darkorg.betterdurability.common.impl.ModItemStack}</p>
 */
public interface UnbreakableItemStack {
    boolean betterDurability$isBroken();
}
