package darkorg.betterdurability.mixin;

import darkorg.betterdurability.util.StackUtil;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
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
    public abstract ItemStack getItemBySlot(EquipmentSlot slot);

    /**
     * Keep broken armor from contributing toughness, including armor whose
     * modifiers were applied before it reached the broken threshold.
     */
    @Inject(method = "getAttributeValue(Lnet/minecraft/world/entity/ai/attributes/Attribute;)D", cancellable = true,
            at = @At("TAIL"))
    private void betterDurability$discardBrokenArmorToughness(Attribute attribute, CallbackInfoReturnable<Double> cir) {
        if (attribute != Attributes.ARMOR_TOUGHNESS) {
            return;
        }

        double invalidToughness = 0.0D;
        for (EquipmentSlot slot : BETTER_DURABILITY$ARMOR_SLOTS) {
            ItemStack armorStack = this.getItemBySlot(slot);
            if (armorStack.getItem() instanceof ArmorItem armorItem && StackUtil.isBroken(armorStack)) {
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
    private void betterDurability$discardBrokenArmorDefense(CallbackInfoReturnable<Integer> cir) {
        int invalidDefense = 0;
        for (EquipmentSlot slot : BETTER_DURABILITY$ARMOR_SLOTS) {
            ItemStack armorStack = this.getItemBySlot(slot);
            if (armorStack.getItem() instanceof ArmorItem armorItem && StackUtil.isBroken(armorStack)) {
                invalidDefense += armorItem.getDefense();
            }
        }
        cir.setReturnValue(Math.max(0, cir.getReturnValue() - invalidDefense));
    }
}
