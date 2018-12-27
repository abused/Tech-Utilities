package abused_master.techutilities.client.render;

import abused_master.techutilities.api.utils.RenderHelper;
import abused_master.techutilities.tiles.TileEntityQuarry;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.util.math.BlockPos;

public class QuarryRenderer extends BlockEntityRenderer<TileEntityQuarry> {

    @SuppressWarnings("Duplicates")
    @Override
    public void render(TileEntityQuarry tile, double x, double y, double z, float float_1, int int_1) {
        if(tile.torchPositions[0] != null && tile.torchPositions[1] != null && tile.torchPositions[2] != null) {
            BlockPos first = tile.torchPositions[0];
            BlockPos second = tile.torchPositions[1];
            BlockPos third = tile.torchPositions[2];
            BlockPos fourth = completeSquare(tile);

            RenderHelper.renderLaser(first.getX() + 0.5, first.getY() + 0.5, first.getZ() + 0.5, second.getX() + 0.5, second.getY() + 0.5, second.getZ() + 0.5, 120, 0.35F, 0.15, new float[]{0, 191 / 255f, 255 / 255f});
            RenderHelper.renderLaser(first.getX() + 0.5, first.getY() + 0.5, first.getZ() + 0.5, third.getX() + 0.5, third.getY() + 0.5, third.getZ() + 0.5, 120, 0.35F, 0.15, new float[]{0, 191 / 255f, 255 / 255f});
            RenderHelper.renderLaser(second.getX() + 0.5, second.getY() + 0.5, second.getZ() + 0.5, fourth.getX() + 0.5, fourth.getY() + 0.5, fourth.getZ() + 0.5, 120, 0.35F, 0.15, new float[]{0, 191 / 255f, 255 / 255f});
            RenderHelper.renderLaser(third.getX() + 0.5, third.getY() + 0.5, third.getZ() + 0.5, fourth.getX() + 0.5, fourth.getY() + 0.5, fourth.getZ() + 0.5, 120, 0.35F, 0.15, new float[]{0, 191 / 255f, 255 / 255f});
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
