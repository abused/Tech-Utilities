package abused_master.techutilities.client.gui.gui;

import abused_master.techutilities.TechUtilities;
import abused_master.techutilities.client.gui.container.ContainerVacuum;
import abused_master.techutilities.tiles.machine.BlockEntityVacuum;
import com.mojang.blaze3d.platform.GlStateManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.ContainerScreen;
import net.minecraft.text.StringTextComponent;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class GuiVacuum extends ContainerScreen {

    public Identifier vacuumGui = new Identifier(TechUtilities.MODID, "textures/gui/vacuum_gui.png");
    public BlockEntityVacuum tile;
    public int guiLeft, guiTop;

    public GuiVacuum(BlockEntityVacuum tile, ContainerVacuum containerVacuum) {
        super(containerVacuum, containerVacuum.playerInventory, new StringTextComponent("Vacuum"));
        this.tile = tile;
    }

    @Override
    protected void onInitialized() {
        super.onInitialized();
        this.guiLeft = (this.width - this.containerWidth) / 2;
        this.guiTop = (this.height - this.containerHeight) / 2;
    }

    @Override
    public void draw(int var1, int var2, float var3) {
        this.drawBackground();
        super.draw(var1, var2, var3);
        this.drawMousoverTooltip(var1, var2);
    }

    @Override
    public void drawForeground(int int_1, int int_2) {
        String string_1 = "Vacuum";
        this.fontRenderer.draw(string_1, (float)(this.containerWidth / 2 - this.fontRenderer.getStringWidth(string_1) / 2), 6.0F, 4210752);
    }

    @Override
    public void drawBackground(float v, int i, int i1) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        client.getTextureManager().bindTexture(vacuumGui);
        drawTexturedRect(guiLeft, guiTop, 0, 0, containerWidth, containerHeight);
    }
}
