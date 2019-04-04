package abused_master.techutilities.client.render;

import abused_master.abusedlib.client.render.RenderHelper;
import abused_master.techutilities.tiles.crystal.BlockEntityWirelessController;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.util.math.BlockPos;

public class WirelessControllerRenderer extends BlockEntityRenderer<BlockEntityWirelessController> {

    @Override
    public void render(BlockEntityWirelessController tile, double x, double y, double z, float float_1, int int_1) {
        super.render(tile, x, y, z, float_1, int_1);
        for (BlockPos pos : tile.tilePositions) {
            RenderHelper.renderLaser(tile.getPos().getX() + 0.5, tile.getPos().getY() + 0.5, tile.getPos().getZ() + 0.5, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 120, 0.35F, 0.07, new float[]{85 / 255f, 130 / 255f, 101 / 255f});
        }
    }

    @Override
    public boolean method_3563(BlockEntityWirelessController blockEntity_1) {
        return true;
    }
}
