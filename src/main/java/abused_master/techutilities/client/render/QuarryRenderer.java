package abused_master.techutilities.client.render;

import abused_master.techutilities.api.utils.RenderHelper;
import abused_master.techutilities.tiles.TileEntityQuarry;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.util.math.BlockPos;

public class QuarryRenderer extends BlockEntityRenderer<TileEntityQuarry> {

    @Override
    public void render(TileEntityQuarry tile, double x, double y, double z, float float_1, int int_1) {
        if(tile.torchPositions[0] != null && tile.torchPositions[1] != null && tile.torchPositions[2] != null) {
            BlockPos first = tile.torchPositions[0];
            BlockPos second = tile.torchPositions[1];
            BlockPos third = tile.torchPositions[2];
            BlockPos fourth = completeSquare(tile);

            RenderHelper.renderLaser(first.getX(), first.getY(), first.getZ(), second.getX(), second.getY(), second.getZ(), 120, 0.35F, 0.05, new float[]{0F, 124F / 255F, 16F / 255F});
            RenderHelper.renderLaser(first.getX(), first.getY(), first.getZ(), third.getX(), third.getY(), third.getZ(), 120, 0.35F, 0.05, new float[]{0F, 124F / 255F, 16F / 255F});
            RenderHelper.renderLaser(second.getX(), second.getY(), second.getZ(), fourth.getX(), fourth.getY(), fourth.getZ(), 120, 0.35F, 0.05, new float[]{0F, 124F / 255F, 16F / 255F});
            RenderHelper.renderLaser(third.getX(), third.getY(), third.getZ(), fourth.getX(), fourth.getY(), fourth.getZ(), 120, 0.35F, 0.05, new float[]{0F, 124F / 255F, 16F / 255F});
        }
    }

    public BlockPos completeSquare(TileEntityQuarry tile) {
        return new BlockPos(tile.torchPositions[0]).offset(tile.forwardDirection, tile.blocksZ).offset(tile.horizontalDirection, tile.blocksX);
    }

    @Override
    public boolean method_3563(TileEntityQuarry blockEntity_1) {
        return true;
    }
}
