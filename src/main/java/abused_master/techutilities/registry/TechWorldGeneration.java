package abused_master.techutilities.registry;

import abused_master.techutilities.blocks.BlockResources;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;

public class TechWorldGeneration {

    public static void generateOres() {
        System.out.println("Beginning Tech Utilities World Generation!");
        for (Biome biome : Biome.BIOMES) {
            for (BlockResources.EnumResourceOres ore : BlockResources.EnumResourceOres.values()) {
                if (ore.generateOre()) {
                    biome.addFeature(GenerationStep.Feature.UNDERGROUND_ORES, Biome.configureFeature(net.minecraft.world.gen.feature.Feature.ORE, new OreFeatureConfig(OreFeatureConfig.Target.NATURAL_STONE, ore.getBlockOres().getDefaultState(), ore.getVeinSize()), Decorator.COUNT_RANGE, new RangeDecoratorConfig(ore.getSpawnRate(), 0, 0, ore.getMaxHeight())));
                }
            }
        }
        System.out.println("Completed Tech Utilities World Generation");
    }
}
