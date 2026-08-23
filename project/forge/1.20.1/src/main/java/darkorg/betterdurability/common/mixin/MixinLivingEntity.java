package darkorg.betterdurability.common.mixin;

import darkorg.betterdurability.common.impl.ModItemStack;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity {
    private static final EquipmentSlot[] BETTER_DURABILITY$ARMOR_SLOTS = {
            EquipmentSlot.HEAD,
            EquipmentSlot.CHEST,
            EquipmentSlot.LEGS,
            EquipmentSlot.FEET
    };

    @Shadow
    protected ItemStack useItem;

    @Shadow
    public abstract ItemStack getItemBySlot(EquipmentSlot pSlot);

    @Shadow
    public abstract boolean isUsingItem();

    /**
     * Increase damage dealt from {@link net.minecraft.tags.DamageTypeTags#DAMAGES_HELMET} if helmet is broken.
     */
    @ModifyVariable(method = "hurt", argsOnly = true, at = @At(value = "CONSTANT", args = "floatValue=0.75"))
    private float BetterDurability$hurt(float value) {
        // Fun fact: Helmet can be hurt by ANVIL and FALLING_BLOCK damage, which does not bypass armor, so helmet will be double-damaged in such occasions.
        return ModItemStack.isBroken(this.getItemBySlot(EquipmentSlot.HEAD)) ? value / 0.75F : value;
    }

    /**
     * Keep broken armor from contributing toughness, including armor whose
     * modifiers were applied before it reached the broken threshold.
     */
    @Inject(method = "getAttributeValue(Lnet/minecraft/world/entity/ai/attributes/Attribute;)D", cancellable = true,
            at = @At("TAIL"))
    private void BetterDurability$discardBrokenArmorToughness(Attribute attribute, CallbackInfoReturnable<Double> cir) {
        if (attribute != Attributes.ARMOR_TOUGHNESS) {
            return;
        }

        double invalidToughness = 0.0D;
        for (EquipmentSlot slot : BETTER_DURABILITY$ARMOR_SLOTS) {
            ItemStack armorStack = this.getItemBySlot(slot);
            if (armorStack.getItem() instanceof ArmorItem armorItem && ModItemStack.isBroken(armorStack)) {
                invalidToughness += armorItem.getToughness();
            }
        }

        // Item-declared values can exceed the current effective value after
        // other modifiers have applied. Do not expose negative attributes to
        // HUD integrations when broken armor is removed from the total.
        cir.setReturnValue(Math.max(0.0D, cir.getReturnValue() - invalidToughness));
    }

    /**
     * Keep broken armor from contributing defense without exposing a negative
     * armor value to HUD integrations.
     */
    @Inject(method = "getArmorValue()I", cancellable = true, at = @At("TAIL"))
    private void BetterDurability$discardBrokenArmorDefense(CallbackInfoReturnable<Integer> cir) {
        int invalidDefense = 0;
        for (EquipmentSlot slot : BETTER_DURABILITY$ARMOR_SLOTS) {
            ItemStack armorStack = this.getItemBySlot(slot);
            if (armorStack.getItem() instanceof ArmorItem armorItem && ModItemStack.isBroken(armorStack)) {
                invalidDefense += armorItem.getDefense();
            }
        }
        cir.setReturnValue(Math.max(0, cir.getReturnValue() - invalidDefense));
    }

    /**
     * Prevent shield from blocking if broken.
     */
    @Inject(method = "isBlocking()Z", cancellable = true, at = @At(value = "HEAD"))
    private void BetterDurability$isBlocking(CallbackInfoReturnable<Boolean> pCallbackInfoReturnable) {
        if (this.isUsingItem() && !this.useItem.isEmpty()) {
            Item item = this.useItem.getItem();
            boolean isUsingShield = item.getUseAnimation(this.useItem) == UseAnim.BLOCK;
            if (isUsingShield) {
                if (ModItemStack.isBroken(this.useItem)) {
                    pCallbackInfoReturnable.setReturnValue(false);
                    pCallbackInfoReturnable.cancel();
                }
            }
        }
    }

    /**
     * Prevent Soul Speed from applying and damaging the boots.
     */
    @Inject(method = "tryAddSoulSpeed()V", cancellable = true, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/attributes/AttributeInstance;addTransientModifier(Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;)V"))
    private void BetterDurability$tryAddSoulSpeed(CallbackInfo pCallbackInfo) {
        ItemStack itemStack = this.getItemBySlot(EquipmentSlot.FEET);
        if (ModItemStack.isBroken(itemStack)) {
            pCallbackInfo.cancel();
        }
    }
}
