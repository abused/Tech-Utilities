package abused_master.techutilities.blocks.machines;

import abused_master.techutilities.tiles.TileEntityQuarry;
import net.fabricmc.fabric.block.FabricBlockSettings;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.block.BlockRenderLayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.StringTextComponent;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class Quarry extends BlockWithEntity {

    public Quarry() {
        super(FabricBlockSettings.of(Material.STONE).build().strength(1, 1));
    }

    @Override
    public boolean activate(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, Direction direction, float float_1, float float_2, float float_3) {
        TileEntityQuarry quarry = (TileEntityQuarry) world.getBlockEntity(blockPos);

        if(!quarry.isRunning() && quarry.canRun()) {
            quarry.setRunning(true);
            if(quarry.torchPositions.length <= 0 || quarry.torchPositions[0] == null || quarry.torchPositions[1] == null || quarry.torchPositions[2] == null) {
                if(!world.isClient)
                    playerEntity.addChatMessage(new StringTextComponent("Error locating quarry markers!"), false);
            }else {
                if(!world.isClient)
                    playerEntity.addChatMessage(new StringTextComponent("Set quarry to now running!"), false);
            }
        }else {
            if(quarry.miningPos == null) {
                if(!world.isClient)
                    playerEntity.addChatMessage(new StringTextComponent("Unknown error with quarry!"), false);
                return true;
            }

            if(!world.isClient)
                playerEntity.addChatMessage(new StringTextComponent("Quarry now mining at X: " + quarry.miningPos.getX() + ", Y: " + quarry.miningPos.getY() + ", Z: " + quarry.miningPos.getY()), false);
        }

        return true;
    }

    @Override
    public BlockRenderType getRenderType(BlockState var1) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean isSimpleFullBlock(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1) {
        return false;
    }

    @Override
    public boolean isSideVisible(BlockState blockState_1, BlockState blockState_2, Direction direction_1) {
        return blockState_1.getBlock() == this ? true : super.isSideVisible(blockState_1, blockState_2, direction_1);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return new TileEntityQuarry();
    }
}
