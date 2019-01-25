package abused_master.techutilities.client.render;

import abused_master.techutilities.tiles.crystal.BlockEntityEnergyCollector;
import abused_master.techutilities.utils.render.RenderHelper;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;

public class CrystalCollectorRenderer extends BlockEntityRenderer<BlockEntityEnergyCollector> {

    @Override
    public void render(BlockEntityEnergyCollector tile, double x, double y, double z, float float_1, int int_1) {
        if(tile.getCrystalPos() != null) {
            RenderHelper.renderLaser(tile.getPos().getX() + 0.5, tile.getPos().getY() + 0.5, tile.getPos().getZ() + 0.5, tile.getCrystalPos().getX() + 0.5, tile.getCrystalPos().getY() + 0.5, tile.getCrystalPos().getZ() + 0.5, 120, 0.35F, 0.07, new float[] {253 / 255, 104 / 255, 104 / 255});
        }
    }
}
