package abused_master.techutilities.blocks.transport;

import abused_master.abusedlib.blocks.BlockWithEntityBase;
import abused_master.techutilities.TechUtilities;
import abused_master.techutilities.tiles.transport.BlockEntityWirelessTransmitter;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.WallMountLocation;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateFactory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.ViewableWorld;

import javax.annotation.Nullable;

public class BlockWirelessTransmitter extends BlockWithEntityBase {

    public BlockWirelessTransmitter() {
        super("wireless_transmitter", Material.STONE, 1.0f, TechUtilities.modItemGroup);
        this.setDefaultState(this.stateFactory.getDefaultState().with(HorizontalFacingBlock.FACING, Direction.NORTH).with(WallMountedBlock.FACE, WallMountLocation.WALL));
    }

    @Override
    public boolean canPlaceAt(BlockState state, ViewableWorld viewableWorld, BlockPos pos) {
        return WallMountedBlock.canPlaceAt(viewableWorld, pos, getDirection(state).getOpposite());
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, IWorld world, BlockPos pos, BlockPos neighborPos) {
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        Direction[] directions = context.getPlacementFacings();
        int facings = directions.length;

        for(int i = 0; i < facings; ++i) {
            Direction direction = directions[i];
            BlockState state;
            if (direction.getAxis() == Direction.Axis.Y) {
                state = this.getDefaultState().with(WallMountedBlock.FACE, direction == Direction.UP ? WallMountLocation.CEILING : WallMountLocation.FLOOR).with(HorizontalFacingBlock.FACING, context.getPlayerHorizontalFacing());
            } else {
                state = this.getDefaultState().with(WallMountedBlock.FACE, WallMountLocation.WALL).with(HorizontalFacingBlock.FACING, direction.getOpposite());
            }

            if (state.canPlaceAt(context.getWorld(), context.getBlockPos())) {
                return state;
            }
        }

        return null;
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
    protected void appendProperties(StateFactory.Builder<Block, BlockState> builder) {
        super.appendProperties(builder.with(WallMountedBlock.FACE, HorizontalFacingBlock.FACING));
    }

    public Direction getDirection(BlockState state) {
        switch (state.get(WallMountedBlock.FACE)) {
            case CEILING:
                return Direction.DOWN;
            case FLOOR:
                return Direction.UP;
            default:
                return state.get(HorizontalFacingBlock.FACING);
        }
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return new BlockEntityWirelessTransmitter();
    }
}
