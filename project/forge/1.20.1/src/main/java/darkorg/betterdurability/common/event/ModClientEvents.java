package darkorg.betterdurability.common.event;

import darkorg.betterdurability.common.impl.DeployerToolPolicy;
import darkorg.betterdurability.common.impl.ModItemStack;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public abstract class ModClientEvents {
    public static void onItemTooltip(ItemStack pItemStack, TooltipFlag pTooltipFlag, List<Component> pComponents) {
        if (pItemStack.isDamageableItem()) {
            if (!pTooltipFlag.isAdvanced()) {
                pComponents.add(Component.translatable("item.durability", pItemStack.getMaxDamage() - pItemStack.getDamageValue(), pItemStack.getMaxDamage()));
            }

            if (ModItemStack.isBroken(pItemStack)) {
                int enchantmentLines = Math.min(pItemStack.getEnchantmentTags().size(), pComponents.size() - 1);
                for (int index = 1; index <= enchantmentLines; index++) {
                    pComponents.set(index, pComponents.get(index).copy().withStyle(ChatFormatting.RED));
                }
            }

            if (DeployerToolPolicy.shouldShowConsumableWarning(pItemStack)) {
                pComponents.add(Component.translatable("tooltip.betterdurability.deployer_consumable")
                        .withStyle(ChatFormatting.RED));
            }
        }
    }
}
