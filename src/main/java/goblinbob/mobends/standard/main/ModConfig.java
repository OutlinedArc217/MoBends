package goblinbob.mobends.standard.main;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import goblinbob.mobends.core.util.WildcardPattern;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Mod.EventBusSubscriber(modid = ModStatics.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModConfig {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    private static final General GENERAL = new General(BUILDER);
    public static final ForgeConfigSpec SPEC = BUILDER.build();

    // 缓存
    private static final Map<Item, Boolean> keepArmorAsVanillaCache = new HashMap<>();
    private static final Map<Entity, Boolean> keepEntityAsVanillaCache = new HashMap<>();
    private static final Map<Item, ItemClassification> itemClassificationCache = new HashMap<>();

    public static class General {
        public final ForgeConfigSpec.BooleanValue showArrowTrails;
        public final ForgeConfigSpec.BooleanValue showSwordTrail;
        public final ForgeConfigSpec.BooleanValue performSpinAttack;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> weaponItems;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> toolItems;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> keepArmorAsVanilla;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> keepEntityAsVanilla;

        General(ForgeConfigSpec.Builder builder) {
            builder.push("general");

            showArrowTrails = builder
                .comment("Enable arrow trail effects")
                .translation(ModStatics.MODID + ".config.show_arrow_trails")
                .define("showArrowTrails", true);

            showSwordTrail = builder
                .comment("Enable sword trail effects")
                .translation(ModStatics.MODID + ".config.show_sword_trails")
                .define("showSwordTrail", true);

            performSpinAttack = builder
                .comment("Enable spin attack animations")
                .translation(ModStatics.MODID + ".config.perform_spin_attack")
                .define("performSpinAttack", true);

            weaponItems = builder
                .comment("List of items to be treated as weapons")
                .translation(ModStatics.MODID + ".config.weapon_items")
                .defineList("weaponItems", Arrays.asList(), o -> o instanceof String);

            toolItems = builder
                .comment("List of items to be treated as tools")
                .translation(ModStatics.MODID + ".config.tool_items")
                .defineList("toolItems", Arrays.asList(), o -> o instanceof String);

            keepArmorAsVanilla = builder
                .comment("List of armor items to keep vanilla rendering")
                .translation(ModStatics.MODID + ".config.keep_armor_as_vanilla")
                .defineList("keepArmorAsVanilla", Arrays.asList(), o -> o instanceof String);

            keepEntityAsVanilla = builder
                .comment("List of entities to keep vanilla animations")
                .translation(ModStatics.MODID + ".config.keep_entity_as_vanilla")
                .defineList("keepEntityAsVanilla", Arrays.asList(), o -> o instanceof String);

            builder.pop();
        }
    }

    public enum ItemClassification {
        UNKNOWN,
        WEAPON,
        TOOL
    }

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent.Loading event) {
        clearCaches();
    }

    @SubscribeEvent
    public static void onReload(final ModConfigEvent.Reloading event) {
        clearCaches();
        MoBends.refreshSystems();
    }

    private static void clearCaches() {
        keepArmorAsVanillaCache.clear();
        keepEntityAsVanillaCache.clear();
        itemClassificationCache.clear();
    }

    private static boolean checkForPatterns(ResourceLocation resourceLocation, List<? extends String> patterns) {
        final String resourceNamespace = resourceLocation.getNamespace();
        final String resourcePath = resourceLocation.getPath();

        return patterns.stream().anyMatch(pattern -> {
            final ResourceLocation patternLocation = new ResourceLocation(pattern);

            if (resourceLocation.equals(patternLocation)) {
                return true;
            }

            WildcardPattern namespacePattern = new WildcardPattern(patternLocation.getNamespace());
            WildcardPattern pathPattern = new WildcardPattern(patternLocation.getPath());

            return namespacePattern.matches(resourceNamespace) && pathPattern.matches(resourcePath);
        });
    }

    public static ItemClassification getItemClassification(Item item) {
        return itemClassificationCache.computeIfAbsent(item, i -> {
            ResourceLocation location = Registries.ITEM.getKey(item);
            
            if (checkForPatterns(location, GENERAL.weaponItems.get())) {
                return ItemClassification.WEAPON;
            }
            
            if (checkForPatterns(location, GENERAL.toolItems.get())) {
                return ItemClassification.TOOL;
            }

            return ItemClassification.UNKNOWN;
        });
    }

    public static boolean shouldKeepArmorAsVanilla(Item item) {
        return keepArmorAsVanillaCache.computeIfAbsent(item, i -> 
            checkForPatterns(Registries.ITEM.getKey(i), GENERAL.keepArmorAsVanilla.get())
        );
    }

    public static boolean shouldKeepEntityAsVanilla(Entity entity) {
        return keepEntityAsVanillaCache.computeIfAbsent(entity, e -> {
            ResourceLocation location = Registries.ENTITY_TYPE.getKey(e.getType());
            return location != null && checkForPatterns(location, GENERAL.keepEntityAsVanilla.get());
        });
    }

    public static void register() {
        ModLoadingContext.get().registerConfig(Type.COMMON, SPEC, ModStatics.MODID + "-common.toml");
    }
}
