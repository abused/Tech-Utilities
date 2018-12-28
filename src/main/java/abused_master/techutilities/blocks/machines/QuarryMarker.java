package abused_master.techutilities.blocks.machines;

import net.fabricmc.fabric.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.client.render.block.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class QuarryMarker extends Block {

    public static final VoxelShape BOUNDING_BOX = Block.createCubeShape(6.0D, 0.0D, 6.0D, 10.0D, 10.0D, 10.0D);

    public QuarryMarker() {
        super(FabricBlockSettings.of(Material.STONE).strength(0.5f, 1.0f).build());
    }

    @Override
    public VoxelShape getBoundingShape(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1) {
        return BOUNDING_BOX;
    }

    @Override
    public BlockRenderType getRenderType(BlockState var1) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}
