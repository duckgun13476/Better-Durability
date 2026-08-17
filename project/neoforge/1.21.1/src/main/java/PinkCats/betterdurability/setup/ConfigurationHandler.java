package PinkCats.betterdurability.setup;

import com.google.common.collect.ImmutableList;
import PinkCats.betterdurability.BetterDurability;
import PinkCats.betterdurability.util.VanillaDamageableType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@EventBusSubscriber(modid = BetterDurability.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ConfigurationHandler {
    public static ModConfigSpec SERVER_CONFIG;

    public static final String CATEGORY_BLACKLISTS = "blacklists";
    public static final ModConfigSpec.ConfigValue<List<? extends String>> DISABLED_CATEGORY_NAMES;
    public static final Set<VanillaDamageableType.Category> DISABLED_CATEGORIES = new HashSet<>();
    public static final ModConfigSpec.ConfigValue<List<? extends String>> DISABLED_TYPE_NAMES;
    public static final Set<VanillaDamageableType> DISABLED_TYPES = new HashSet<>();
    public static final ModConfigSpec.ConfigValue<List<? extends String>> BLACKLISTED_ITEM_IDS;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> WHITELISTED_ITEM_IDS;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> DEPLOYER_CONSUMABLE_ITEM_IDS;
    public static final Set<Item> BLACKLISTED_ITEMS = new HashSet<>();
    public static final Set<Item> WHITELISTED_ITEMS = new HashSet<>();

    static {
        ModConfigSpec.Builder SERVER_BUILDER = new ModConfigSpec.Builder();

        SERVER_BUILDER.comment("Blacklist things, loved by server owners ;)").push(CATEGORY_BLACKLISTS);
        DISABLED_CATEGORY_NAMES = SERVER_BUILDER.comment("List of disabled damage protection categories. Available: TOOL, ARMOR, SHIELD.")
                .defineList("disabledCategoryNames", ImmutableList.of(), obj -> true);
        DISABLED_TYPE_NAMES = SERVER_BUILDER.comment("List of disabled damage protection types. Available: AXE, PICKAXE, SHOVEL, HOE, SHEARS, SWORD, FISHING_ROD, FLINT_AND_STEEL, BOW, TRIDENT, CROSSBOW, HELMET, CHESTPLATE, LEGGINGS, BOOTS, SHIELD.")
                .defineList("disabledTypeNames", ImmutableList.of(), obj -> true);
        BLACKLISTED_ITEM_IDS = SERVER_BUILDER.comment("List of blacklisted items. Format is modId:itemId, modId can be omitted for vanilla.")
                .defineList("blacklistedItemIds", ImmutableList.of(), obj -> true);
        WHITELISTED_ITEM_IDS = SERVER_BUILDER
                .comment("EXPERIMENTAL: Whitelist is intended for items whose types are not included above, such as knives from Farmer's Delight. ")
                .comment("Items here will be forcefully protected as long as they are not blacklisted. Their durability will be set to 2 when being about to break.")
                .comment("However, since only the click event is canceled, some illogical situations may occur.")
                .comment("Format is modId:itemId, modId can be omitted for vanilla.")
                .defineList("whitelistedItemIds", ImmutableList.of(), obj -> true);
        SERVER_BUILDER.pop();

        SERVER_BUILDER.comment("Create Deployer settings").push("deployer");
        DEPLOYER_CONSUMABLE_ITEM_IDS = SERVER_BUILDER
                .comment("Tools that disappear when a Create Deployer exhausts them.")
                .comment("All other Deployer tools are kept usable. Use fully qualified item IDs.")
                .defineList("consumableItemIds", ImmutableList.of(
                        "minecraft:wooden_sword", "minecraft:wooden_shovel", "minecraft:wooden_pickaxe", "minecraft:wooden_axe", "minecraft:wooden_hoe",
                        "minecraft:iron_sword", "minecraft:iron_shovel", "minecraft:iron_pickaxe", "minecraft:iron_axe", "minecraft:iron_hoe"
                ), obj -> obj instanceof String);
        SERVER_BUILDER.pop();

        SERVER_CONFIG = SERVER_BUILDER.build();
    }

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent.Loading configEvent) {
        BetterDurability.LOGGER.info("Loading config ...");
        loadEnumList(VanillaDamageableType.Category.class, DISABLED_CATEGORY_NAMES.get(), DISABLED_CATEGORIES);
        loadEnumList(VanillaDamageableType.class, DISABLED_TYPE_NAMES.get(), DISABLED_TYPES);
        loadItemIdList(BLACKLISTED_ITEM_IDS.get(), BLACKLISTED_ITEMS);
        loadItemIdList(WHITELISTED_ITEM_IDS.get(), WHITELISTED_ITEMS);
    }

    @SubscribeEvent
    public static void onReload(final ModConfigEvent.Reloading configEvent) {
        BetterDurability.LOGGER.info("Reloading config ...");
        reloadEnumList(VanillaDamageableType.Category.class, DISABLED_CATEGORY_NAMES.get(), DISABLED_CATEGORIES);
        reloadEnumList(VanillaDamageableType.class, DISABLED_TYPE_NAMES.get(), DISABLED_TYPES);
        reloadItemIdList(BLACKLISTED_ITEM_IDS.get(), BLACKLISTED_ITEMS);
        reloadItemIdList(WHITELISTED_ITEM_IDS.get(), WHITELISTED_ITEMS);
    }

    private static void loadItemIdList(final List<? extends String> src, final Set<Item> dst) {
        for (String itemId : src) {
            ResourceLocation itemLocation = ResourceLocation.tryParse(itemId);
            if (itemLocation == null) {
                BetterDurability.LOGGER.error("Invalid ResourceLocation format for item {}", itemId);
                continue;
            }

            Item item = BuiltInRegistries.ITEM.get(itemLocation);
            if (item == Items.AIR) {
                BetterDurability.LOGGER.error("Trying to blacklist item {} but it does not exist ...", itemId);
            } else {
                dst.add(item);
            }
        }
    }
    private static void reloadItemIdList(final List<? extends String> src, final Set<Item> dst) {
        dst.clear();
        loadItemIdList(src, dst);
    }

    private static <T extends Enum<T>> void loadEnumList(Class<T> clazz, final List<? extends String> src, final Set<T> dst) {
        for (String name : src) {
            try {
                dst.add(Enum.valueOf(clazz, name));
            } catch (IllegalArgumentException e) {
                BetterDurability.LOGGER.error("Trying to blacklist enum {} in {} but it does not exist ...", name, clazz);
            }
        }
    }

    private static <T extends Enum<T>> void reloadEnumList(Class<T> clazz, final List<? extends String> src, final Set<T> dst) {
        dst.clear();
        loadEnumList(clazz, src, dst);
    }
}
