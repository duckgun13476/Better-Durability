package darkorg.betterdurability.event;

import darkorg.betterdurability.BetterDurability;
import darkorg.betterdurability.util.DeployerToolPolicy;
import darkorg.betterdurability.util.StackUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = BetterDurability.MOD_ID, value = Dist.CLIENT)
public class ForgeClientEvents {
    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        List<Component> tooltip = event.getToolTip();

        if (StackUtil.isBroken(event.getItemStack())) {
            int enchantmentLines = Math.min(event.getItemStack().getEnchantmentTags().size(), tooltip.size() - 1);
            for (int index = 1; index <= enchantmentLines; index++) {
                tooltip.set(index, tooltip.get(index).copy().withStyle(ChatFormatting.RED));
            }
        }

        if (DeployerToolPolicy.shouldShowConsumableWarning(event.getItemStack())) {
            tooltip.add(Component.translatable("tooltip.betterdurability.deployer_consumable")
                    .withStyle(ChatFormatting.RED));
        }
    }
}
