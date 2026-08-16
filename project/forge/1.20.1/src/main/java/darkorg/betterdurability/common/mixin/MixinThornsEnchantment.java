package darkorg.betterdurability.common.mixin;

import darkorg.betterdurability.common.impl.ModItemStack;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ThornsEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Map;
import java.util.Map.Entry;

@Mixin(ThornsEnchantment.class)
public class MixinThornsEnchantment {
    /**
     * Redirect Entry to use predicate.
     */
    @Redirect(
            method = "doPostHurt",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;getRandomItemWith(Lnet/minecraft/world/item/enchantment/Enchantment;Lnet/minecraft/world/entity/LivingEntity;)Ljava/util/Map$Entry;"
            )
    )
    private Map.Entry<EquipmentSlot, ItemStack> BetterDurability_doPostHurt_getRandomItemWith(Enchantment pEnchantment, LivingEntity pLivingEntity) {
        return EnchantmentHelper.getRandomItemWith(Enchantments.THORNS, pLivingEntity, ModItemStack::isUsable);
    }

    /**
     * Prevent Thorns from applying.
     */
    @Inject(
            method = "doPostHurt",
            cancellable = true,
            locals = LocalCapture.CAPTURE_FAILHARD,
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"
            )
    )
    private void BetterDurability_doPostHurt_hurt(LivingEntity pLivingEntity, Entity pEntity, int pDamageAmount, CallbackInfo pCallbackInfo, RandomSource pRandomSource, Entry<EquipmentSlot, ItemStack> pEntry) {
        if (pEntry == null) {
            pCallbackInfo.cancel();
        }
    }
}
