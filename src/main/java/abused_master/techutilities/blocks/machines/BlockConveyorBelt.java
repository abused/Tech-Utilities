package abused_master.techutilities.blocks.machines;

import abused_master.abusedlib.blocks.BlockBase;
import abused_master.techutilities.TechUtilities;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.VerticalEntityPosition;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockConveyorBelt extends BlockBase {

    public static final DirectionProperty FACING = HorizontalFacingBlock.field_11177;

    public BlockConveyorBelt() {
        super("conveyor_belt", Material.STONE, 1.0f, TechUtilities.modItemGroup);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        Direction direction = state.get(FACING).getOpposite();

        if (entity instanceof PlayerEntity && entity.isSneaking()) {
            return;
        }

        entity.setVelocity(entity.getVelocity().add(.15 * direction.getOffsetX(), 0, .15 * direction.getOffsetZ()));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, VerticalEntityPosition verticalEntityPosition_1) {
        return Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 1.0D, 15.0D);
    }

    @Override
    public VoxelShape getRayTraceShape(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1) {
        return Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 1.0D, 15.0D);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext itemPlacementContext_1) {
        return this.getDefaultState().with(FACING, itemPlacementContext_1.getPlayerHorizontalFacing().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState blockState_1, Rotation rotation_1) {
        return blockState_1.with(FACING, rotation_1.rotate(blockState_1.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState blockState_1, Mirror mirror_1) {
        return blockState_1.rotate(mirror_1.getRotation(blockState_1.get(FACING)));
    }

    @Override
    protected void appendProperties(StateFactory.Builder<Block, BlockState> stateFactory$Builder_1) {
        stateFactory$Builder_1.with(new Property[]{FACING});
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
    public boolean skipRenderingSide(BlockState blockState_1, BlockState blockState_2, Direction direction_1) {
        return blockState_1.getBlock() == this ? true : super.skipRenderingSide(blockState_1, blockState_2, direction_1);
    }

    @Override
    public boolean isTranslucent(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1) {
        return true;
    }

    @Override
    public BlockRenderType getRenderType(BlockState var1) {
        return BlockRenderType.MODEL;
    }
}
