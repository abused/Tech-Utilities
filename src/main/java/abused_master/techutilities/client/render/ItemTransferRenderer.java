package abused_master.techutilities.client.render;

import abused_master.abusedlib.client.render.RenderHelper;
import abused_master.techutilities.tiles.crystal.BlockEntityItemTransfer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;

public class ItemTransferRenderer extends BlockEntityRenderer<BlockEntityItemTransfer> {

    @Override
    public void render(BlockEntityItemTransfer tile, double x, double y, double z, float float_1, int int_1) {
        super.render(tile, x, y, z, float_1, int_1);
        if(tile.receiverPosition != null) {
            RenderHelper.renderLaser(tile.getPos().getX() + 0.5, tile.getPos().getY() + 0.5, tile.getPos().getZ() + 0.5, tile.receiverPosition.getX() + 0.5, tile.receiverPosition.getY() + 0.5, tile.receiverPosition.getZ() + 0.5, 120, 0.35F, 0.07, new float[] {104 / 255f, 163 / 255f, 124 / 255f});
        }
    }

    @Override
    public boolean method_3563(BlockEntityItemTransfer blockEntity_1) {
        return true;
    }
}
