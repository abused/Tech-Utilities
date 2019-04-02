package abused_master.techutilities.client.render;

import abused_master.abusedlib.client.render.RenderHelper;
import abused_master.techutilities.tiles.crystal.BlockEntityEnergyCrystal;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.util.math.BlockPos;

public class CrystalRenderer extends BlockEntityRenderer<BlockEntityEnergyCrystal> {

    @Override
    public void render(BlockEntityEnergyCrystal tile, double x, double y, double z, float float_1, int int_1) {
        super.render(tile, x, y, z, float_1, int_1);
        /*
        GlStateManager.pushMatrix();
        GlStateManager.translated(x + 0.5, y, z + 0.5);
        long angle = (System.currentTimeMillis() / 10) % 360;
        GlStateManager.rotatef(MathHelper.lerp(float_1, angle - 1, angle), 0, 1, 0);
        GlStateManager.translated(-0.5, 0, -0.5);
        GlStateManager.disableLighting();
        GlStateManager.color3f(1, 1, 1);
        MinecraftClient.getInstance().getBlockRenderManager().renderDynamic(getWorld().getBlockState(tile.getPos()), 1);
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
        */

        if(tile.tilePositions.size() > 0) {
            for (BlockPos pos : tile.tilePositions) {
                RenderHelper.renderLaser(tile.getPos().getX() + 0.5, tile.getPos().getY() + 0.5, tile.getPos().getZ() + 0.5, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 120, 0.35F, 0.07, new float[] {85 / 255f, 130 / 255f, 101 / 255f});
            }
        }
    }

    @Override
    public boolean method_3563(BlockEntityEnergyCrystal blockEntity_1) {
        return true;
    }
}
