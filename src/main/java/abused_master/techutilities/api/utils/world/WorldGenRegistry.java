package abused_master.techutilities.api.utils.world;

import com.google.common.collect.Sets;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkProvider;
import net.minecraft.world.gen.chunk.ChunkGenerator;

import java.util.Random;
import java.util.Set;

public class WorldGenRegistry {

    private static Set<IWorldGenerator> worldGenerators = Sets.newHashSet();

    public static void registerWorldGenerator(IWorldGenerator generator) {
        worldGenerators.add(generator);
    }

    public static void generateWorld(int chunkX, int chunkZ, World world, ChunkGenerator chunkGenerator, ChunkProvider chunkProvider) {
        long worldSeed = world.getSeed();
        Random random = new Random(worldSeed);
        long xSeed = random.nextLong() >> 2 + 1L;
        long zSeed = random.nextLong() >> 2 + 1L;
        long chunkSeed = (xSeed * chunkX + zSeed * chunkZ) ^ worldSeed;

        for (IWorldGenerator generator : worldGenerators) {
            random.setSeed(chunkSeed);
            generator.generate(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
        }
    }
}
