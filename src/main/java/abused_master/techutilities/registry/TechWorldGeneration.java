package abused_master.techutilities.registry;

import abused_master.techutilities.api.utils.world.IWorldGenerator;
import abused_master.techutilities.api.utils.world.WorldGenerator;
import abused_master.techutilities.blocks.BlockResources;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkProvider;
import net.minecraft.world.gen.chunk.ChunkGenerator;

import java.util.Random;

public class TechWorldGeneration implements IWorldGenerator {

    public TechWorldGeneration() {
    }

    private void runGenerator(WorldGenerator generator, World world, Random rand, int chunk_X, int chunk_Z, int chancesToSpawn, int minHeight, int maxHeight) {
        if (minHeight < 0 || maxHeight > 256 || minHeight > maxHeight)
            throw new IllegalArgumentException("Illegal Height Arguments for WorldGenerator");

        int heightDiff = maxHeight - minHeight + 1;
        for (int i = 0; i < chancesToSpawn; i ++) {
            int x = chunk_X * 16 + rand.nextInt(16);
            int y = minHeight + rand.nextInt(heightDiff);
            int z = chunk_Z * 16 + rand.nextInt(16);
            generator.generate(world, rand, new BlockPos(x, y, z));
        }
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, ChunkGenerator chunkGenerator, ChunkProvider chunkProvider) {
        System.out.println("Beginning Tech Utilities World Generation!");
        for (BlockResources.EnumResourceOres ore : BlockResources.EnumResourceOres.values()) {
            if (ore.generateOre()) {
                this.runGenerator(ore.getOreWorldGenerator(), world, random, chunkX, chunkZ, ore.getSpawnRate(), 0, ore.getMaxHeight());
            }
        }
    }
}
