package abused_master.techutilities.api.utils.world;

import com.google.common.base.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.config.feature.OreFeatureConfig;

import java.util.BitSet;
import java.util.Random;

public class WorldGenMinable extends WorldGenerator {

    private final BlockState oreBlock;
    private final int numberOfBlocks;
    private final Predicate<BlockState> predicate;

    public WorldGenMinable(BlockState blockState, int blockCount) {
        this(blockState, blockCount, new WorldGenMinable.StonePredicate());
    }

    public WorldGenMinable(BlockState blockState, int blockCount, Predicate<BlockState> predicate) {
        this.oreBlock = blockState;
        this.numberOfBlocks = blockCount;
        this.predicate = predicate;
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        float float_1 = rand.nextFloat() * 3.1415927F;
        float float_2 = (float)numberOfBlocks / 8.0F;
        int int_1 = MathHelper.ceil(((float)numberOfBlocks / 16.0F * 2.0F + 1.0F) / 2.0F);
        double double_1 = (double)((float)position.getX() + MathHelper.sin(float_1) * float_2);
        double double_2 = (double)((float)position.getX() - MathHelper.sin(float_1) * float_2);
        double double_3 = (double)((float)position.getZ() + MathHelper.cos(float_1) * float_2);
        double double_4 = (double)((float)position.getZ() - MathHelper.cos(float_1) * float_2);
        double double_5 = (double)(position.getY() + rand.nextInt(3) - 2);
        double double_6 = (double)(position.getY() + rand.nextInt(3) - 2);
        int int_3 = position.getX() - MathHelper.ceil(float_2) - int_1;
        int int_4 = position.getY() - 2 - int_1;
        int int_5 = position.getZ() - MathHelper.ceil(float_2) - int_1;
        int int_6 = 2 * (MathHelper.ceil(float_2) + int_1);
        int int_7 = 2 * (2 + int_1);

        for(int int_8 = int_3; int_8 <= int_3 + int_6; ++int_8) {
            for(int int_9 = int_5; int_9 <= int_5 + int_6; ++int_9) {
                if (int_4 <= worldIn.getTop(Heightmap.Type.OCEAN_FLOOR_WG, int_8, int_9)) {
                    return this.method_13629(worldIn, rand, double_1, double_2, double_3, double_4, double_5, double_6, int_3, int_4, int_5, int_6, int_7);
                }
            }
        }

        return false;
    }

    protected boolean method_13629(IWorld iWorld_1, Random random_1, double double_1, double double_2, double double_3, double double_4, double double_5, double double_6, int int_1, int int_2, int int_3, int int_4, int int_5) {
        int int_6 = 0;
        BitSet bitSet_1 = new BitSet(int_4 * int_5 * int_4);
        BlockPos.Mutable blockPos$Mutable_1 = new BlockPos.Mutable();
        double[] doubles_1 = new double[numberOfBlocks * 4];

        int int_8;
        double double_12;
        double double_13;
        double double_14;
        double double_15;
        for(int_8 = 0; int_8 < numberOfBlocks; ++int_8) {
            float float_1 = (float)int_8 / (float)numberOfBlocks;
            double_12 = MathHelper.lerp((double)float_1, double_1, double_2);
            double_13 = MathHelper.lerp((double)float_1, double_5, double_6);
            double_14 = MathHelper.lerp((double)float_1, double_3, double_4);
            double_15 = random_1.nextDouble() * (double)numberOfBlocks / 16.0D;
            double double_11 = ((double)(MathHelper.sin(3.1415927F * float_1) + 1.0F) * double_15 + 1.0D) / 2.0D;
            doubles_1[int_8 * 4 + 0] = double_12;
            doubles_1[int_8 * 4 + 1] = double_13;
            doubles_1[int_8 * 4 + 2] = double_14;
            doubles_1[int_8 * 4 + 3] = double_11;
        }

        for(int_8 = 0; int_8 < numberOfBlocks - 1; ++int_8) {
            if (doubles_1[int_8 * 4 + 3] > 0.0D) {
                for(int int_9 = int_8 + 1; int_9 < numberOfBlocks; ++int_9) {
                    if (doubles_1[int_9 * 4 + 3] > 0.0D) {
                        double_12 = doubles_1[int_8 * 4 + 0] - doubles_1[int_9 * 4 + 0];
                        double_13 = doubles_1[int_8 * 4 + 1] - doubles_1[int_9 * 4 + 1];
                        double_14 = doubles_1[int_8 * 4 + 2] - doubles_1[int_9 * 4 + 2];
                        double_15 = doubles_1[int_8 * 4 + 3] - doubles_1[int_9 * 4 + 3];
                        if (double_15 * double_15 > double_12 * double_12 + double_13 * double_13 + double_14 * double_14) {
                            if (double_15 > 0.0D) {
                                doubles_1[int_9 * 4 + 3] = -1.0D;
                            } else {
                                doubles_1[int_8 * 4 + 3] = -1.0D;
                            }
                        }
                    }
                }
            }
        }

        for(int_8 = 0; int_8 < numberOfBlocks; ++int_8) {
            double double_16 = doubles_1[int_8 * 4 + 3];
            if (double_16 >= 0.0D) {
                double double_17 = doubles_1[int_8 * 4 + 0];
                double double_18 = doubles_1[int_8 * 4 + 1];
                double double_19 = doubles_1[int_8 * 4 + 2];
                int int_11 = Math.max(MathHelper.floor(double_17 - double_16), int_1);
                int int_12 = Math.max(MathHelper.floor(double_18 - double_16), int_2);
                int int_13 = Math.max(MathHelper.floor(double_19 - double_16), int_3);
                int int_14 = Math.max(MathHelper.floor(double_17 + double_16), int_11);
                int int_15 = Math.max(MathHelper.floor(double_18 + double_16), int_12);
                int int_16 = Math.max(MathHelper.floor(double_19 + double_16), int_13);

                for(int int_17 = int_11; int_17 <= int_14; ++int_17) {
                    double double_20 = ((double)int_17 + 0.5D - double_17) / double_16;
                    if (double_20 * double_20 < 1.0D) {
                        for(int int_18 = int_12; int_18 <= int_15; ++int_18) {
                            double double_21 = ((double)int_18 + 0.5D - double_18) / double_16;
                            if (double_20 * double_20 + double_21 * double_21 < 1.0D) {
                                for(int int_19 = int_13; int_19 <= int_16; ++int_19) {
                                    double double_22 = ((double)int_19 + 0.5D - double_19) / double_16;
                                    if (double_20 * double_20 + double_21 * double_21 + double_22 * double_22 < 1.0D) {
                                        int int_20 = int_17 - int_1 + (int_18 - int_2) * int_4 + (int_19 - int_3) * int_4 * int_5;
                                        if (!bitSet_1.get(int_20)) {
                                            bitSet_1.set(int_20);
                                            blockPos$Mutable_1.set(int_17, int_18, int_19);
                                            if (OreFeatureConfig.Target.NATURAL_STONE.getCondition().test(iWorld_1.getBlockState(blockPos$Mutable_1))) {
                                                iWorld_1.setBlockState(blockPos$Mutable_1, oreBlock, 2);
                                                ++int_6;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return int_6 > 0;
    }

    public static class StonePredicate implements Predicate<BlockState> {

        private StonePredicate() {
        }

        @Override
        public boolean apply(BlockState input) {
            if (input != null && input.getBlock() == Blocks.STONE) {
                return Block.isNaturalStone(input.getBlock());
            }

            return false;
        }
    }
}
