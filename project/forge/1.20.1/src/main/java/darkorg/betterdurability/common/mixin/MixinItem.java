package darkorg.betterdurability.common.mixin;

import darkorg.betterdurability.common.config.BetterDurabilityConfig;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(Item.class)
public class MixinItem {
    @ModifyConstant(method = "getDestroySpeed", constant = @Constant(floatValue = 1.0F))
    private float BetterDurability$getDestroySpeed(float pOriginalSpeed) {
        return BetterDurabilityConfig.GAMEPLAY.defaultItemDestroySpeed.get().floatValue();
    }
}
