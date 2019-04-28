package abused_master.techutilities.utils;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IWrenchable {

    boolean onWrenched(World world, BlockPos pos, BlockState state, PlayerEntity player);
}
