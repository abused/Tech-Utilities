package abused_master.techutilities.blocks.machines;

import abused_master.abusedlib.blocks.BlockWithEntityBase;
import abused_master.techutilities.TechUtilities;
import abused_master.techutilities.tiles.machine.BlockEntityPhaseCell;
import abused_master.techutilities.utils.IWrenchable;
import abused_master.techutilities.utils.WrenchHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockEnergyCell extends BlockWithEntityBase implements IWrenchable {

    public BlockEnergyCell() {
        super("energy_cell", Material.STONE, 1.0f, TechUtilities.modItemGroup);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return new BlockEntityPhaseCell();
    }

    @Override
    public boolean onWrenched(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        return player.isSneaking() ? WrenchHelper.dropBlock(world, pos) : false;
    }
}
