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

@Pseudo
@Mixin(targets = "com.simibubi.create.content.contraptions.components.deployer.DeployerHandler", remap = false)
public class LegacyCreateDeployerHandlerMixin {
    @Inject(
            method = "activate(Lcom/simibubi/create/content/contraptions/components/deployer/DeployerFakePlayer;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/Vec3;Lcom/simibubi/create/content/contraptions/components/deployer/DeployerTileEntity$Mode;)V",
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
