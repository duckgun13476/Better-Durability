package darkorg.betterdurability.fabric;

import darkorg.betterdurability.common.BetterDurability;
import darkorg.betterdurability.fabric.datagen.client.ModLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class BetterDurabilityFabricDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator pFabricDataGenerator) {
        BetterDurability.initDataGenerator();

        FabricDataGenerator.Pack pack = pFabricDataGenerator.createPack();
        //Client data
        pack.addProvider(ModLanguageProvider::new);
    }
}
