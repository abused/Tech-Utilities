package abused_master.techutilities.blocks.machines;

import abused_master.abusedlib.blocks.BlockWithEntityBase;
import abused_master.techutilities.TechUtilities;
import abused_master.techutilities.tiles.machine.BlockEntityFluidPump;
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

public class BlockFluidPump extends BlockWithEntityBase implements IWrenchable {

    public BlockFluidPump() {
        super("fluid_pump", Material.STONE, 1.0f, TechUtilities.modItemGroup);
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState state2, boolean boolean_1) {
        if(world.getBlockEntity(pos) instanceof BlockEntityFluidPump) {
            BlockEntityFluidPump pump = (BlockEntityFluidPump) world.getBlockEntity(pos);
            pump.cacheDrainingArea();
        }
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return new BlockEntityFluidPump();
    }

    @Override
    public boolean onWrenched(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        return player.isSneaking() ? WrenchHelper.dropBlock(world, pos) : false;
    }
}
