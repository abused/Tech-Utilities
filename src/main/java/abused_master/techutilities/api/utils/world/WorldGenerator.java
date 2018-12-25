package abused_master.techutilities.api.utils.world;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public abstract class WorldGenerator {

    public WorldGenerator() {
    }

    public abstract boolean generate(World world, Random random, BlockPos pos);
}
