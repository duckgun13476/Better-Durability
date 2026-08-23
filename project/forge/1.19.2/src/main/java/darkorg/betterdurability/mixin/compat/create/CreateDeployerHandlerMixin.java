package darkorg.betterdurability.mixin.compat.create;

import darkorg.betterdurability.util.DeployerToolPolicy;
import darkorg.betterdurability.util.StackUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Create rejects an already-broken tool before ItemStack#hurt can run again.
 * Handle that real Deployer entrypoint so configured disposable tools do not
 * remain stuck in the machine, while all other broken tools stay and jam it.
 */
@Pseudo
@Mixin(targets = "com.simibubi.create.content.kinetics.deployer.DeployerHandler", remap = false)
public class CreateDeployerHandlerMixin {
    @Inject(
            method = "activate(Lcom/simibubi/create/content/kinetics/deployer/DeployerFakePlayer;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/Vec3;Lcom/simibubi/create/content/kinetics/deployer/DeployerBlockEntity$Mode;)V",
            at = @At("HEAD"),
            cancellable = true,
            require = 1
    )
    private static void betterdurability$handleBrokenDeployerTool(
            @Coerce Object deployerPlayer,
            Vec3 position,
            BlockPos targetPos,
            Vec3 movement,
            @Coerce Object mode,
            CallbackInfo ci
    ) {
        if (!(deployerPlayer instanceof LivingEntity player)) {
            return;
        }

        ItemStack held = player.getMainHandItem();
        if (!StackUtil.isBroken(held)) {
            return;
        }

        if (DeployerToolPolicy.destroysWhenBroken(held)) {
            player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
        }
        ci.cancel();
    }
}
