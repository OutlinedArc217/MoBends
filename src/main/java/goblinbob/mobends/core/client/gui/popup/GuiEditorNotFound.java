/*
 * MIGRATED TO MC 1.20.1 by automated script
 * This file has been automatically updated for Minecraft 1.20.1 compatibility
 * Manual review and testing required for proper functionality
 * Original file: GuiEditorNotFound.java
 */

package goblinbob.mobends.core.client.gui.popup;

import net.minecraft.network.chat.Component;

public class GuiEditorNotFound extends GuiPopUp
{
    boolean errorOccurred;

    public GuiEditorNotFound(ButtonAction onBack, ButtonAction onGetEditor)
    {
        super(Component.format("mobends.gui.editornotfound"), 200, 100, new ButtonProps[] {
                new ButtonProps(Component.format("mobends.gui.back"), onBack),
                new ButtonProps(Component.format("mobends.gui.geteditor"), onGetEditor)
        });
    }

    public void setErrorOccurred(boolean errorOccurred)
    {
        this.errorOccurred = errorOccurred;
    }

    @Override
    public void display(int mouseX, int mouseY, float partialTicks)
    {
        super.display(mouseX, mouseY, partialTicks);

        if (errorOccurred)
        {
            String message = "There seems to be something wrong. Please check your internet connection," +
                    " or contact the developers.";
            fontRenderer.drawStringWithShadow(message, 5, 5, 0xffff0000);
        }
    }
}
