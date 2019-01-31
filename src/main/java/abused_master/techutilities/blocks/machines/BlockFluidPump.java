package abused_master.techutilities.blocks.machines;

import abused_master.techutilities.TechUtilities;
import abused_master.techutilities.blocks.BlockWithEntityBase;
import abused_master.techutilities.tiles.machine.BlockEntityFluidPump;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

import javax.annotation.Nullable;

public class BlockFluidPump extends BlockWithEntityBase {

    public BlockFluidPump() {
        super("fluid_pump", Material.STONE, 1.0f, TechUtilities.modItemGroup);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return new BlockEntityFluidPump();
    }
}
