package abused_master.techutilities.blocks.transport;

import abused_master.abusedlib.blocks.BlockWithEntityBase;
import abused_master.techutilities.TechUtilities;
import abused_master.techutilities.tiles.transport.BlockEntityWirelessController;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

import javax.annotation.Nullable;

public class BlockWirelessController extends BlockWithEntityBase {

    public static final EnumProperty<ControllerState> STATE = EnumProperty.create("state", ControllerState.class);

    public BlockWirelessController() {
        super("wireless_controller", Material.STONE, 1.0f, TechUtilities.modItemGroup);
        this.setDefaultState(this.getStateFactory().getDefaultState().with(STATE, ControllerState.idle));
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

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return super.getPlacementState(context).with(STATE, ControllerState.idle);
    }

    @Override
    protected void appendProperties(StateFactory.Builder<Block, BlockState> builder) {
        super.appendProperties(builder.with(STATE));
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView var1) {
        return new BlockEntityWirelessController();
    }

    public enum ControllerState implements StringRepresentable {
        idle,
        running;

        @Override
        public String asString() {
            return this.name();
        }
    }
}
