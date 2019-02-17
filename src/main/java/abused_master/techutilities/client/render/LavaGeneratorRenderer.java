package abused_master.techutilities.client.render;

import abused_master.abusedlib.client.render.RenderHelper;
import abused_master.abusedlib.client.render.hud.HudRender;
import abused_master.techutilities.tiles.generator.BlockEntityLavaGenerator;
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
            RenderHelper.renderFluid(tile.tank.getFluidStack(), tile.getPos(), -0.06, 0, -0.06, 0.25, 0.25, 0.26, 0.87, tile.tank.getFluidAmount() / tile.tank.getFluidCapacity() * 0.75, 0.87);
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }

    @Override
    public boolean method_3563(BlockEntityLavaGenerator blockEntity_1) {
        return true;
    }
}
