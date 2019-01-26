package abused_master.techutilities.client.gui.gui;

import abused_master.techutilities.TechUtilities;
import abused_master.techutilities.client.gui.container.ContainerEnergyFurnace;
import abused_master.techutilities.tiles.machine.BlockEntityEnergyFurnace;
import com.mojang.blaze3d.platform.GlStateManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.ContainerGui;
import net.minecraft.text.StringTextComponent;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class GuiEnergyFurnace extends ContainerGui {

    public Identifier RFFurnace = new Identifier(TechUtilities.MODID, "textures/gui/energy_furnace_gui.png");
    public BlockEntityEnergyFurnace tile;
    public int guiLeft, guiTop;

    public GuiEnergyFurnace(BlockEntityEnergyFurnace tile, ContainerEnergyFurnace containerRFFurnace) {
        super(containerRFFurnace, containerRFFurnace.playerInventory, new StringTextComponent("RF Furnace"));
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
        String string_1 = "RF Furnace";
        this.fontRenderer.draw(string_1, (float)(this.containerWidth / 2 - this.fontRenderer.getStringWidth(string_1) / 2), 6.0F, 4210752);
    }

    @Override
    public void drawBackground(float v, int i, int i1) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        client.getTextureManager().bindTexture(RFFurnace);
        drawTexturedRect(guiLeft, guiTop, 0, 0, containerWidth, containerHeight);

        renderEnergy(guiLeft, guiTop);
        renderProgress(guiLeft, guiTop);

        if(this.isPointWithinBounds(10, 9, 20, 64, i, i1)) {
            this.drawTooltip(tile.storage.getEnergyStored() + " / " + tile.storage.getEnergyCapacity() + " Phase Energy", i, i1);
        }
    }

    public void renderEnergy(int guiLeft, int guiTop) {
        if(this.tile.storage.getEnergyStored() > 0) {
            int k = 62;
            int i = tile.storage.getEnergyStored() * k / tile.storage.getEnergyCapacity();
            this.drawTexturedRect(guiLeft + 10, guiTop + 71 - i, 178, 66 - i, 18, i);
        }
    }

    public void renderProgress(int guiLeft, int guiTop) {
        if(tile.getSmeltTime() > 0) {
            int k = 22;
            int i = tile.getSmeltTime() * k / tile.getTotalSmeltTime();
            this.drawTexturedRect(guiLeft + 80, guiTop + 26, 177, 68, i, 15);
        }
    }
}
