package darkorg.betterdurability.fabric.datagen.client;

import darkorg.betterdurability.common.registry.ModComponents;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class ModLanguageProvider extends FabricLanguageProvider {
    public ModLanguageProvider(FabricDataOutput pOutput) {
        super(pOutput);
    }

    @Override
    public void generateTranslations(TranslationBuilder pBuilder) {
        pBuilder.add(ModComponents.BROKEN.getString(), "Broken");
        pBuilder.add(ModComponents.DURABILITY.getString(), "Durability");
    }
}
