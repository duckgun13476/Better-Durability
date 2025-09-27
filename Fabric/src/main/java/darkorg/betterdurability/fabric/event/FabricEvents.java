package darkorg.betterdurability.fabric.event;

import darkorg.betterdurability.common.event.ModEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;

public abstract class FabricEvents {
    public static void init() {
        onRightClickBlock();
        onRightClickEntity();
        onRightClickItem();
    }

    public static void onRightClickEntity() {
        UseEntityCallback.EVENT.register(ModEvents::onRightClickEntity);
    }

    public static void onRightClickBlock() {
        UseBlockCallback.EVENT.register(ModEvents::onRightClickBlock);
    }

    public static void onRightClickItem() {
        UseItemCallback.EVENT.register(ModEvents::onRightClickItem);
    }
}