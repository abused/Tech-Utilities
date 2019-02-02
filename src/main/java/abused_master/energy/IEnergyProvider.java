package abused_master.energy;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IEnergyProvider extends IEnergyHandler {

    boolean sendEnergy(World world, BlockPos pos, int amount);
}
