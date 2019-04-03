package abused_master.techutilities.blocks.machines;

import abused_master.abusedlib.blocks.BlockWithEntityBase;
import abused_master.techutilities.TechUtilities;
import abused_master.techutilities.tiles.machine.BlockEntityPhaseCell;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

import javax.annotation.Nullable;

public class BlockEnergyCell extends BlockWithEntityBase {

    public BlockEnergyCell() {
        super("energy_cell", Material.STONE, 1.0f, TechUtilities.modItemGroup);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return new BlockEntityPhaseCell();
    }
}
