package abused_master.techutilities.client.render;

import abused_master.techutilities.tiles.generator.BlockEntityLavaGenerator;
import abused_master.techutilities.utils.render.RenderHelper;
import abused_master.techutilities.utils.render.hud.HudRender;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;

public class LavaGeneratorRenderer extends BlockEntityRenderer<BlockEntityLavaGenerator> {

    @Override
    public void render(BlockEntityLavaGenerator tile, double x, double y, double z, float float_1, int int_1) {
        super.render(tile, x, y, z, float_1, int_1);
        HudRender.renderHud(tile, x, y, z);

        if (tile.tank.getFluidStack() != null && tile.tank.getFluidAmount() > 0) {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            RenderHelper.translateAgainstPlayer(tile.getPos(), false);
            RenderHelper.renderFluid(tile.tank.getFluidStack(), tile.getPos(), 0.06, 0.08, 0.06, 0.01, 0.0, 0.01, 0.87, tile.tank.getFluidAmount() / tile.tank.getFluidCapacity() * 0.84, 0.87);
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }

    @Override
    public boolean method_3563(BlockEntityLavaGenerator blockEntity_1) {
        return true;
    }
}
