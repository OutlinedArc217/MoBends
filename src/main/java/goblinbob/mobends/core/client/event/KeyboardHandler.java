/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: KeyboardHandler.java
 */

package goblinbob.mobends.core.client.event;

import com.mojang.blaze3d.platform.InputConstants;
// TODO: Create Object /* TODO: Implement IAnimationController */ interface - package missing
import goblinbob.mobends.core.client.gui.GuiMainMenu;
import goblinbob.mobends.standard.main.ModStatics;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class KeyboardHandler {
    private static final List<KeyMapping> KEY_MAPPINGS = new ArrayList<>();
    
    private static final KeyMapping TOGGLE_ANIMATIONS = registerKey("toggle_animations", 
        GLFW.GLFW_KEY_B, "key.categories." + ModStatics.MODID);
    
    private static final KeyMapping OPEN_MENU = registerKey("open_menu",
        GLFW.GLFW_KEY_RIGHT_SHIFT, "key.categories." + ModStatics.MODID);

    public static void initKeyBindings() {
        MinecraftForge.EVENT_BUS.register(KeyboardHandler.class);
    }

    private static KeyMapping registerKey(String name, int keyCode, String category) {
        KeyMapping keyMapping = new KeyMapping(
            "key." + ModStatics.MODID + "." + name,
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            keyCode,
            category
        );
        KEY_MAPPINGS.add(keyMapping);
        return keyMapping;
    }

    @SubscribeEvent
    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        KEY_MAPPINGS.forEach(event::register);
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.screen != null) return;

        while (TOGGLE_ANIMATIONS.consumeClick()) {
            boolean newState = !Object /* TODO: Implement IAnimationController */.getGlobalAnimationsEnabled();
            Object /* TODO: Implement IAnimationController */.setGlobalAnimationsEnabled(newState);
            
            String key = newState ? "message.animations_enabled" : "message.animations_disabled";
            mc.player.displayClientMessage(
                Component.translatable(ModStatics.MODID + "." + key),
                true
            );
        }

        while (OPEN_MENU.consumeClick()) {
            mc.setScreen(new GuiMainMenu());
        }
    }

    public static void refreshMappings() {
        KEY_MAPPINGS.forEach(KeyMapping::resetMapping);
    }
}
