package PinkCats.betterdurability.mixin.compat.create;

import PinkCats.betterdurability.durability.DurabilityPolicy;
import PinkCats.betterdurability.event.ItemDurabilityEvent.ItemUsage;
import com.simibubi.create.content.kinetics.belt.behaviour.TransportedItemStackHandlerBehaviour;
import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import com.simibubi.create.content.kinetics.deployer.DeployerBlockEntity;
import net.minecraft.world.item.crafting.Recipe;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Belt deployers apply recipes directly, so they need the same guard before
 * RecipeApplier consumes the transported input or emits its output.
 */
@Pseudo
@Mixin(targets = "com.simibubi.create.content.kinetics.deployer.BeltDeployerCallbacks", remap = false)
public class CreateBeltDeployerCallbacksMixin {
    @Inject(method = "activate", at = @At("HEAD"), cancellable = true, require = 1)
    private static void betterdurability$rejectBrokenBeltTool(
        TransportedItemStack transported,
        TransportedItemStackHandlerBehaviour handler,
        DeployerBlockEntity blockEntity,
        Recipe<?> recipe,
        CallbackInfo ci
    ) {
        var player = blockEntity.getPlayer();
        if (player == null || !DurabilityPolicy.canUseTool(
            player.getMainHandItem(),
            ItemUsage.Type.TOOL_RIGHT_CLICK_BLOCK
        )) {
            ci.cancel();
        }
    }
}
