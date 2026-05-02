package PinkCats.betterdurability.mixin;

import PinkCats.betterdurability.BetterDurability;
import PinkCats.betterdurability.event.ItemDurabilityEvent.ItemBreaking;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.NeoForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.function.Consumer;


@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow public abstract int getDamageValue();
    @Shadow public abstract void setDamageValue(int pDamage);
    @Shadow public abstract int getMaxDamage();

    @Inject(method = "hurtAndBreak(ILnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/LivingEntity;Ljava/util/function/Consumer;)V",
            cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;setDamageValue(I)V"))
    private void injectItemStackHurtAndBreak(int damage, ServerLevel level, LivingEntity entity, Consumer<Item> onBreak,
                                             CallbackInfo ci, int newDamageValue) {
        int maxDamageValue = this.getMaxDamage();
        ItemStack self = (ItemStack)(Object)this;
        BetterDurability.LOGGER.info("hurtAndBreak path: item={}, oldDamage={}, incomingDamage={}, newDamage={}, maxDamage={}, entity={}",
                self.getItem(), this.getDamageValue(), damage, newDamageValue, maxDamageValue,
                entity == null ? "null" : entity.getType());

        if (newDamageValue >= maxDamageValue) {
            ItemBreaking event = new ItemBreaking(self, damage);
            NeoForge.EVENT_BUS.post(event);
            BetterDurability.LOGGER.info("hurtAndBreak break check: item={}, reserveDurability={}", self.getItem(), event.reserveDurability);
            if (event.reserveDurability > 0) {
                this.setDamageValue(maxDamageValue - event.reserveDurability);
                BetterDurability.LOGGER.info("hurtAndBreak protected: item={}, finalDamage={}, durabilityLeft={}",
                        self.getItem(), this.getDamageValue(), event.reserveDurability);
                ci.cancel();
            }
        }
    }

    @Inject(method = "hurt(ILnet/minecraft/util/RandomSource;Lnet/minecraft/server/level/ServerPlayer;)Z",
            cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getDamageValue()I", ordinal = 1))
    private void injectItemStackHurt(int pAmount, RandomSource pRandom, ServerPlayer pUser, CallbackInfoReturnable<Boolean> cir) {
        int newDamageValue = this.getDamageValue() + pAmount;
        int maxDamageValue = this.getMaxDamage();
        if (newDamageValue >= maxDamageValue) {
            ItemBreaking event = new ItemBreaking((ItemStack)(Object)this, pAmount);
            NeoForge.EVENT_BUS.post(event);
            BetterDurability.LOGGER.info("hurt path break check: item={}, oldDamage={}, incomingDamage={}, newDamage={}, maxDamage={}, reserveDurability={}, player={}",
                    ((ItemStack)(Object)this).getItem(), this.getDamageValue(), pAmount, newDamageValue, maxDamageValue,
                    event.reserveDurability, pUser == null ? "null" : pUser.getGameProfile().getName());
            if (event.reserveDurability > 0) {
                this.setDamageValue(maxDamageValue - event.reserveDurability);
                BetterDurability.LOGGER.info("hurt path protected: item={}, finalDamage={}, durabilityLeft={}",
                        ((ItemStack)(Object)this).getItem(), this.getDamageValue(), event.reserveDurability);
                cir.setReturnValue(false);
            } else {
                this.setDamageValue(newDamageValue);
                cir.setReturnValue(true);
            }
        } else {
            this.setDamageValue(newDamageValue);
            cir.setReturnValue(false);
        }
        cir.cancel();
    }
}
