package abused_master.techutilities.api.utils.world;

import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkProvider;
import net.minecraft.world.gen.chunk.ChunkGenerator;

import java.util.Random;

public interface IWorldGenerator {

    void generate(Random random, int chunkX, int chunkZ, World world, ChunkGenerator chunkGenerator, ChunkProvider chunkProvider);
}
