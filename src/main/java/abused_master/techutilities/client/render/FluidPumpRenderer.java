package abused_master.techutilities.client.render;

import abused_master.techutilities.tiles.machine.BlockEntityFluidPump;
import abused_master.techutilities.utils.render.hud.HudRender;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;

public class FluidPumpRenderer extends BlockEntityRenderer<BlockEntityFluidPump> {

    @Override
    public void render(BlockEntityFluidPump tile, double x, double y, double z, float float_1, int int_1) {
        super.render(tile, x, y, z, float_1, int_1);
        HudRender.renderHud(tile, x, y, z);
    }

    @Override
    public boolean method_3563(BlockEntityFluidPump blockEntity_1) {
        return true;
    }
}
