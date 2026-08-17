package darkorg.betterdurability.mixin;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import darkorg.betterdurability.util.StackUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class MixinItemStack {
    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    private void betterDurability$preserveBrokenItem(int damage, RandomSource random, ServerPlayer player, CallbackInfoReturnable<Boolean> cir) {
        ItemStack stack = (ItemStack) (Object) this;
        if (StackUtil.wouldBreak(stack, damage)) {
            stack.setDamageValue(stack.getMaxDamage() - StackUtil.getBrokenThreshold(stack));
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "getHoverName", at = @At("RETURN"), cancellable = true)
    private void betterDurability$appendBrokenSuffix(CallbackInfoReturnable<Component> cir) {
        if (StackUtil.isBroken((ItemStack) (Object) this)) {
            cir.setReturnValue(cir.getReturnValue().copy()
                    .append(Component.literal(" "))
                    .append(Component.translatable("tooltip.betterdurability.broken_suffix").withStyle(ChatFormatting.RED)));
        }
    }

    @Inject(method = "getAttributeModifiers", at = @At("HEAD"), cancellable = true)
    private void betterDurability$discardBrokenAttributes(EquipmentSlot slot, CallbackInfoReturnable<Multimap<Attribute, AttributeModifier>> cir) {
        if (StackUtil.isBroken((ItemStack) (Object) this)) {
            cir.setReturnValue(ImmutableMultimap.of());
        }
    }
}
