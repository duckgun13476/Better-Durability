package PinkCats.betterdurability.mixin;

import PinkCats.betterdurability.event.ItemDurabilityEvent.ItemUsage;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentTarget;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Enchantment.class)
public class EnchantmentMixin {
    @Inject(method = "doPostAttack(Lnet/minecraft/server/level/ServerLevel;ILnet/minecraft/world/item/enchantment/EnchantedItemInUse;Lnet/minecraft/world/item/enchantment/EnchantmentTarget;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/damagesource/DamageSource;)V",
            cancellable = true, at = @At("HEAD"))
    private void modifyThornsCheck$discardEffect(ServerLevel level, int enchantmentLevel, EnchantedItemInUse item,
                                                EnchantmentTarget target, Entity entity, DamageSource damageSource,
                                                CallbackInfo ci) {
        if (target != EnchantmentTarget.VICTIM || item.owner() == null) {
            return;
        }

        Registry<Enchantment> registry = item.owner().registryAccess().registryOrThrow(Registries.ENCHANTMENT);
        Holder.Reference<Enchantment> thorns = registry.getHolderOrThrow(Enchantments.THORNS);
        if (thorns.value() != (Object) this) {
            return;
        }

        if (!ItemUsage.check(item.itemStack(), ItemUsage.Type.ARMOR_THORNS)) {
            ci.cancel();
        }
    }
}
