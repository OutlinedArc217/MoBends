package goblinbob.mobends.core.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import goblinbob.mobends.standard.main.ModStatics;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class AnimationConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FMLPaths.CONFIGDIR.get().resolve(ModStatics.MODID);
    
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.BooleanValue ENABLE_ANIMATIONS;
    public static final ForgeConfigSpec.DoubleValue ANIMATION_SPEED;
    public static final ForgeConfigSpec.IntValue ANIMATION_QUALITY;

    private Map<String, AnimationPreset> presets;

    static {
        BUILDER.push("General");
        
        ENABLE_ANIMATIONS = BUILDER.comment("Enable/disable all animations")
                .define("enableAnimations", true);
                
        ANIMATION_SPEED = BUILDER.comment("Global animation speed multiplier")
                .defineInRange("animationSpeed", 1.0, 0.1, 2.0);
                
        ANIMATION_QUALITY = BUILDER.comment("Animation quality (affects performance)")
                .defineInRange("animationQuality", 2, 1, 3);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }

    public AnimationConfig() {
        this.presets = new HashMap<>();
        loadPresets();
    }

    public static void register() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, SPEC);
        CONFIG_PATH.toFile().mkdirs();
    }

    private void loadPresets() {
        File presetsFile = CONFIG_PATH.resolve("animation_presets.json").toFile();
        
        if (presetsFile.exists()) {
            try (FileReader reader = new FileReader(presetsFile)) {
                PresetContainer container = GSON.fromJson(reader, PresetContainer.class);
                if (container != null && container.presets != null) {
                    this.presets = container.presets;
                }
            } catch (IOException e) {
                ModStatics.LOGGER.error("Failed to load animation presets", e);
            }
        }
    }

    public void savePresets() {
        File presetsFile = CONFIG_PATH.resolve("animation_presets.json").toFile();
        
        try (FileWriter writer = new FileWriter(presetsFile)) {
            PresetContainer container = new PresetContainer(presets);
            GSON.toJson(container, writer);
        } catch (IOException e) {
            ModStatics.LOGGER.error("Failed to save animation presets", e);
        }
    }

    public AnimationPreset getPreset(String name) {
        return presets.get(name);
    }

    public void addPreset(String name, AnimationPreset preset) {
        presets.put(name, preset);
        savePresets();
    }

    public void removePreset(String name) {
        presets.remove(name);
        savePresets();
    }

    private static class PresetContainer {
        public Map<String, AnimationPreset> presets;

        public PresetContainer(Map<String, AnimationPreset> presets) {
            this.presets = presets;
        }
    }

    public static class AnimationPreset {
        public float speed;
        public Map<String, Float> boneWeights;
        public boolean enabled;

        public AnimationPreset() {
            this.speed = 1.0f;
            this.boneWeights = new HashMap<>();
            this.enabled = true;
        }
    }
          }
