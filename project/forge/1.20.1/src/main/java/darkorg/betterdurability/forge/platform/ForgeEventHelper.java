package darkorg.betterdurability.forge.platform;

import darkorg.betterdurability.common.platform.services.IEventHelper;
import net.minecraftforge.fml.common.Mod;

public final class ForgeEventHelper implements IEventHelper {
    /**
     * On Forge, we have no event listeners init, because {@link Mod.EventBusSubscriber} handles everything.
     */
    @Override
    public void initListeners() {

    }

    /**
     * On Forge, we have no event listeners init, because {@link Mod.EventBusSubscriber} handles everything.
     */
    @Override
    public void initClientListeners() {

    }
}
