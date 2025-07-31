package goblinbob.mobends.core.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import goblinbob.mobends.core.animation.controller.IAnimationController;
import goblinbob.mobends.core.configuration.AnimationConfig;
import goblinbob.mobends.core.util.GUIHelper;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import org.joml.Matrix4f;
import org.joml.Matrix3f;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;

@OnlyIn(Dist.CLIENT)
public class GuiAnimationEditor extends Screen {
    private static final ResourceLocation BACKGROUND = ResourceLocation.parse("mobends:textures/gui/editor_background.png");
    private static final int WINDOW_WIDTH = 248;
    private static final int WINDOW_HEIGHT = 166;

    private final List<TimelineTrack> tracks;
    private final AnimationConfig config;
    private float scrollOffset;
    private boolean isDragging;
    private int lastMouseX;
    private int lastMouseY;

    public GuiAnimationEditor() {
        super(Component.translatable("mobends.gui.editor.title"));
        this.tracks = new ArrayList<>();
        this.config = new AnimationConfig();
        this.scrollOffset = 0;
        this.isDragging = false;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int left = centerX - WINDOW_WIDTH / 2;
        int top = centerY - WINDOW_HEIGHT / 2;

        this.addRenderableWidget(Button.builder(
            Component.translatable("mobends.gui.editor.play"),
            (button) -> playAnimation())
            .pos(left + 10, top + WINDOW_HEIGHT - 30)
            .size(60, 20)
            .build());

        this.addRenderableWidget(Button.builder(
            Component.translatable("mobends.gui.editor.save"),
            (button) -> saveAnimation())
            .pos(left + WINDOW_WIDTH - 70, top + WINDOW_HEIGHT - 30)
            .size(60, 20)
            .build());

        initializeTracks();
    }

    private void initializeTracks() {
        tracks.clear();
        int trackY = 40;

        tracks.add(new TimelineTrack(
            Component.translatable("mobends.gui.editor.track.position"),
            trackY
        ));
        trackY += 30;
      
        tracks.add(new TimelineTrack(
            Component.translatable("mobends.gui.editor.track.rotation"),
            trackY
        ));
        trackY += 30;

        tracks.add(new TimelineTrack(
            Component.translatable("mobends.gui.editor.track.scale"),
            trackY
        ));
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        PoseStack poseStack = graphics.pose();
        
        this.renderBackground(graphics);
      
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int left = centerX - WINDOW_WIDTH / 2;
        int top = centerY - WINDOW_HEIGHT / 2;

        poseStack.pushPose();
        poseStack.translate(left, top, 0);

        RenderSystem.setShaderTexture(0, BACKGROUND);
        GUIHelper.drawTexturedRect(graphics, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        renderTimeline(graphics);

        for (TimelineTrack track : tracks) {
            track.render(graphics, mouseX - left, mouseY - top, partialTicks);
        }

        poseStack.popPose();

        renderTooltips(graphics, mouseX, mouseY);

        super.render(graphics, mouseX, mouseY, partialTicks);
    }

    private void renderTimeline(GuiGraphics graphics) {
        int timelineY = 25;
        float timeScale = 20.0f; // pixels per second
        
        graphics.fill(10, timelineY, WINDOW_WIDTH - 10, timelineY + 2, 0xFF404040);

        for (int i = 0; i <= 10; i++) {
            float time = i + scrollOffset;
            int x = 10 + (int)(time * timeScale);
            if (x >= 10 && x <= WINDOW_WIDTH - 10) {
                graphics.fill(x, timelineY - 3, x + 1, timelineY + 5, 0xFFFFFFFF);
                graphics.drawString(
                    this.font,
                    String.format("%.1f", time),
                    x - 10,
                    timelineY - 15,
                    0xFFFFFFFF
                );
            }
        }
    }

    private void renderTooltips(GuiGraphics graphics, int mouseX, int mouseY) {
        // Add tooltip rendering logic here
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        isDragging = true;
        lastMouseX = (int) mouseX;
        lastMouseY = (int) mouseY;
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        isDragging = false;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (isDragging) {
            scrollOffset -= (mouseX - lastMouseX) / 20.0f;
            lastMouseX = (int) mouseX;
            lastMouseY = (int) mouseY;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    private void playAnimation() {
    }

    private void saveAnimation() {
        config.savePresets();
    }

    private class TimelineTrack {
        private final Component name;
        private final int yPosition;
        private final List<KeyFrame> keyFrames;

        public TimelineTrack(Component name, int yPosition) {
            this.name = name;
            this.yPosition = yPosition;
            this.keyFrames = new ArrayList<>();
        }

        public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
            // Render track name
            graphics.drawString(font, name, 10, yPosition, 0xFFFFFFFF);

            // Render track timeline
            graphics.fill(10, yPosition + 15, WINDOW_WIDTH - 10, yPosition + 17, 0xFF303030);

            // Render keyframes
            for (KeyFrame keyFrame : keyFrames) {
                keyFrame.render(graphics, yPosition + 15);
            }
        }
    }

    private class KeyFrame {
        private float time;
        private float value;

        public KeyFrame(float time, float value) {
            this.time = time;
            this.value = value;
        }

        public void render(GuiGraphics graphics, int y) {
            int x = 10 + (int)((time - scrollOffset) * 20.0f);
            if (x >= 10 && x <= WINDOW_WIDTH - 10) {
                graphics.fill(x - 2, y - 2, x + 2, y + 2, 0xFFFFFF00);
            }
        }
    }
          }
