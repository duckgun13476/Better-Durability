package darkorg.betterdurability.common.mixin;

import darkorg.betterdurability.common.impl.ModItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.FrostWalkerEnchantment;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FrostWalkerEnchantment.class)
public class MixinFrostWalkerEnchantment {
    /**
     * Broken boots must not keep applying Frost Walker's movement effect.
     */
    @Inject(method = "onEntityMoved", cancellable = true, at = @At("HEAD"))
    private static void BetterDurability$discardBrokenBootsEffect(LivingEntity entity, Level level,
                                                                  BlockPos pos, int enchantmentLevel,
                                                                  CallbackInfo ci) {
        if (ModItemStack.isBroken(entity.getItemBySlot(EquipmentSlot.FEET))) {
            ci.cancel();
        }
    }
}
