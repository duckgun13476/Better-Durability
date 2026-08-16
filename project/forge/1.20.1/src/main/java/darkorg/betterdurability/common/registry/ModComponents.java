package darkorg.betterdurability.common.registry;

import darkorg.betterdurability.common.BetterDurability;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class ModComponents {
    public static final MutableComponent BROKEN = translatable("gui.broken");
    public static final Component DURABILITY = translatable("gui.durability");

    public static MutableComponent translatable(String pKey) {
        return Component.translatable(BetterDurability.MOD_ID + "." + pKey);
    }
}
