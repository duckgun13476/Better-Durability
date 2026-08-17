package PinkCats.betterdurability.event;

import PinkCats.betterdurability.BetterDurability;
import PinkCats.betterdurability.util.VanillaDamageableType;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.List;

@EventBusSubscriber(modid = BetterDurability.MOD_ID, value = Dist.CLIENT)
public class ForgeClientEventsHandler {
    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        List<Component> tooltip = event.getToolTip();
        if (VanillaDamageableType.isItemKnownBroken(event.getItemStack())
                || VanillaDamageableType.isWhitelistedItemKnownBroken(event.getItemStack())) {
            int enchantmentLines = Math.min(event.getItemStack().getEnchantments().size(), tooltip.size() - 1);
            for (int index = 1; index <= enchantmentLines; index++) {
                tooltip.set(index, tooltip.get(index).copy().withStyle(ChatFormatting.RED));
            }
        }
    }
}
