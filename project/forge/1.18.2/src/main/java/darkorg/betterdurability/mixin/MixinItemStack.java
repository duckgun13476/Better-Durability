package darkorg.betterdurability.mixin;

import darkorg.betterdurability.util.DeployerToolPolicy;
import darkorg.betterdurability.util.StackUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(ItemStack.class)
public abstract class MixinItemStack {
    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    private void betterDurability$applyDeployerPolicy(
            int damage,
            Random random,
            ServerPlayer player,
            CallbackInfoReturnable<Boolean> cir
    ) {
        if (!DeployerToolPolicy.isDeployer(player)) {
            return;
        }

        ItemStack stack = (ItemStack) (Object) this;
        if (!StackUtil.wouldBreak(stack, damage) || DeployerToolPolicy.destroysWhenBroken(stack)) {
            return;
        }

        stack.setDamageValue(stack.getMaxDamage() - StackUtil.getBrokenThreshold(stack));
        cir.setReturnValue(false);
    }
}
