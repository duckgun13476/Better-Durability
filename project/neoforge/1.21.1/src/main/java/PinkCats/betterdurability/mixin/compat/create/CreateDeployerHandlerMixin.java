package PinkCats.betterdurability.mixin.compat.create;

import PinkCats.betterdurability.durability.DurabilityPolicy;
import PinkCats.betterdurability.durability.DeployerToolPolicy;
import PinkCats.betterdurability.util.VanillaDamageableType;
import PinkCats.betterdurability.event.ItemDurabilityEvent.ItemUsage;
import com.simibubi.create.content.kinetics.deployer.DeployerFakePlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Create deployers use fake players and bypass the normal player interaction
 * events. Stop the action before it can consume inputs with a broken tool.
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
    private static void betterdurability$rejectBrokenDeployerTool(
        DeployerFakePlayer player,
        Vec3 position,
        BlockPos targetPos,
        Vec3 movement,
        @Coerce Object mode,
        CallbackInfo ci
    ) {
        // Mode is package-private in Create. The broken-tool policy itself is
        // independent of the action type, so use the public right-click slot.
        ItemStack held = player.getMainHandItem();
        if (!DurabilityPolicy.canUseTool(
            held,
            ItemUsage.Type.TOOL_RIGHT_CLICK_BLOCK
        )) {
            // Better Durability marks axes, pickaxes, shovels, and swords as
            // broken before their vanilla durability reaches zero. Consume
            // configured disposable tools here, before the broken-tool guard
            // stops Create from reaching ItemStack#hurtAndBreak.
            if (VanillaDamageableType.isItemKnownBroken(held)
                    && DeployerToolPolicy.destroysWhenBroken(held)) {
                player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
            }
            ci.cancel();
        }
    }
}
