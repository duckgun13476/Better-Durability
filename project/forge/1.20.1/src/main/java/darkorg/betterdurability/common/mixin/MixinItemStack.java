package darkorg.betterdurability.common.mixin;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import darkorg.betterdurability.common.api.UnbreakableItemStack;
import darkorg.betterdurability.common.config.BetterDurabilityConfig;
import darkorg.betterdurability.common.impl.DeployerToolPolicy;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class MixinItemStack implements UnbreakableItemStack {
    @Inject(method = "hurtAndBreak", at = @At("HEAD"), cancellable = true)
    private void betterDurability$handleDeployerBreak(
            int damage,
            LivingEntity entity,
            Consumer<LivingEntity> onBreak,
            CallbackInfo ci
    ) {
        ItemStack self = (ItemStack) (Object) this;
        if (!DeployerToolPolicy.isDeployer(entity)
                || !self.isDamageableItem()
                || self.getDamageValue() + damage < self.getMaxDamage() - 1) {
            return;
        }

        if (DeployerToolPolicy.destroysWhenBroken(self)) {
            onBreak.accept(entity);
            self.shrink(1);
            ci.cancel();
        }
    }

    /**
     * @return True, if item is broken.
     */
    @Override
    public boolean betterDurability$isBroken() {
        ItemStack itemStack = (ItemStack) (Object) this;
        if (itemStack.isDamageableItem()) {
            if (!itemStack.isDamaged()) {
                return false;
            } else {
                int damageValue = itemStack.getDamageValue();
                int maxDamage = itemStack.getMaxDamage();
                return damageValue + 1 >= maxDamage;
            }
        } else {
            return false;
        }
    }

    @Inject(method = "getHoverName", at = @At("RETURN"), cancellable = true)
    private void BetterDurability$getHoverName(CallbackInfoReturnable<Component> pCallbackInfoReturnable) {
        if (this.betterDurability$isBroken()) {
            pCallbackInfoReturnable.setReturnValue(pCallbackInfoReturnable.getReturnValue().copy()
                    .append(Component.literal(" "))
                    .append(Component.translatable("tooltip.betterdurability.broken_suffix").withStyle(ChatFormatting.RED)));
        }
    }

    /**
     * Prevent hurt when broken.
     */
    @Inject(method = "hurt", at = @At(value = "HEAD", target = "Lnet/minecraft/world/item/ItemStack;setDamageValue(I)V"), cancellable = true)
    private void BetterDurability$hurt_setDamageValue(int pDamage, RandomSource pRandomSource, ServerPlayer pServerPlayer, CallbackInfoReturnable<Boolean> pCallbackInfoReturnable) {
        ItemStack self = (ItemStack) (Object) this;
        if (!BetterDurabilityConfig.isBlacklisted(self.getItem())) {
            if (this.betterDurability$isBroken()) {
                pCallbackInfoReturnable.setReturnValue(false);
            }
        }
    }

    /**
     * Set broken at 1 durability.
     */
    @Redirect(method = "hurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getMaxDamage()I"))
    private int BetterDurability$hurt_getMaxDamage(ItemStack stack) {
        return stack.getMaxDamage() - 1;
    }

    /**
     * Prevent negative durability.
     */
    @Redirect(method = "hurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;setDamageValue(I)V"))
    private void BetterDurability$hurt_setDamageValue(ItemStack pItemStack, int pDamageValue) {
        ItemStack itemStack = (ItemStack) (Object) this;
        pItemStack.setDamageValue(BetterDurabilityConfig.isBlacklisted(itemStack.getItem()) ? pDamageValue : Math.min(itemStack.getMaxDamage() - 1, pDamageValue));
    }

    /**
     * <p>Prevent the stack from shrinking.</p>
     *
     * <p>(If item is blacklisted it will shrink (break and disappear)</p>
     */
    @Redirect(method = "hurtAndBreak", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;shrink(I)V"))
    private void BetterDurability$hurtAndBreak_shrink(ItemStack pItemStack, int pAmount) {
        if (BetterDurabilityConfig.isBlacklisted(pItemStack.getItem())) {
            pItemStack.shrink(1);
        } else {
            // Do nothing: prevents the stack from shrinking
        }
    }

    /**
     * ItemStack gets broken (1 durability) instead of going back to full durability.
     */
    @Redirect(method = "hurtAndBreak", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;setDamageValue(I)V"))
    private void BetterDurability$hurtAndBreak_setDamageValue(ItemStack pItemStack, int pAmount) {
        // Do nothing: prevents the stack from repairing
    }

    /**
     * Discard destroy speed.
     */
    @Inject(method = "getDestroySpeed", at = @At(value = "HEAD"), cancellable = true)
    public void BetterDurability$getDestroySpeed(BlockState pBlockState, CallbackInfoReturnable<Float> pCallbackInfoReturnable) {
        if (this.betterDurability$isBroken()) {
            float defaultItemBrokenSpeed = BetterDurabilityConfig.GAMEPLAY.defaultItemBrokenDestroySpeed.get().floatValue();
            pCallbackInfoReturnable.setReturnValue(defaultItemBrokenSpeed);
        }
    }

    /**
     * Discard harvest check.
     */
    @Inject(method = "isCorrectToolForDrops", at = @At(value = "HEAD"), cancellable = true)
    public void BetterDurability$isCorrectToolForDrops(BlockState pBlockState, CallbackInfoReturnable<Boolean> pCallbackInfoReturnable) {
        if (this.betterDurability$isBroken()) {
            pCallbackInfoReturnable.setReturnValue(false);
        }
    }

    /**
     * Discard attribute modifiers.
     */
    @Inject(method = "getAttributeModifiers", at = @At(value = "HEAD"), cancellable = true)
    private void BetterDurability$getAttributeModifiers(EquipmentSlot pEquipmentSlot, CallbackInfoReturnable<Multimap<Attribute, AttributeModifier>> pCallbackInfoReturnable) {
        if (this.betterDurability$isBroken()) {
            pCallbackInfoReturnable.setReturnValue(ImmutableMultimap.of());
        }
    }
}
