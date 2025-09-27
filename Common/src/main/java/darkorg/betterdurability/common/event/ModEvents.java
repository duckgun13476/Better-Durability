package darkorg.betterdurability.common.event;

import darkorg.betterdurability.common.impl.ModItemStack;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public abstract class ModEvents {
    public static InteractionResult onRightClickEntity(Player pPlayer, Level pLevel, InteractionHand pInteractionHand, Entity pEntity, EntityHitResult pEntityHitResult) {
        ItemStack itemStack = pPlayer.getItemInHand(pInteractionHand);
        if (ModItemStack.isBroken(itemStack)) {
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }
        return InteractionResult.PASS;
    }

    public static InteractionResult onRightClickEntitySpecific(Player pPlayer, Level pLevel, InteractionHand pInteractionHand, Entity pTargetEntity, EntityHitResult pEntityHitResult) {
        ItemStack itemStack = pPlayer.getItemInHand(pInteractionHand);
        if (ModItemStack.isBroken(itemStack)) {
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }
        return InteractionResult.PASS;
    }

    public static InteractionResult onRightClickBlock(Player pPlayer, Level pLevel, InteractionHand pInteractionHand, BlockHitResult pBlockHitResult) {
        ItemStack itemStack = pPlayer.getItemInHand(pInteractionHand);
        if (ModItemStack.isBroken(itemStack)) {
            return InteractionResult.sidedSuccess(pLevel.isClientSide());
        }
        return InteractionResult.PASS;
    }

    public static InteractionResultHolder<ItemStack> onRightClickItem(Player pPlayer, Level pLevel, InteractionHand pInteractionHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pInteractionHand);
        if (ModItemStack.isBroken(itemStack)) {
            return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide());
        }
        return InteractionResultHolder.pass(itemStack);
    }
}