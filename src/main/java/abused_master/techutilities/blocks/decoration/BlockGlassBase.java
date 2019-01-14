package abused_master.techutilities.blocks.decoration;

import abused_master.abusedlib.blocks.BlockBase;
import abused_master.abusedlib.blocks.property.BlockFacings;
import abused_master.abusedlib.blocks.property.PropertyBlockFacings;
import abused_master.techutilities.TechUtilities;
import net.fabricmc.fabric.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateFactory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockGlassBase extends BlockBase {

    private boolean[] actualFacings;

    public BlockGlassBase(String name) {
        super(name, TechUtilities.modItemGroup, FabricBlockSettings.of(Material.GLASS).sounds(BlockSoundGroup.GLASS).strength(1, 1).build());
        this.actualFacings = new boolean[Direction.values().length];
        this.setDefaultState(this.stateFactory.getDefaultState().with(PropertyBlockFacings.FACINGS, PropertyBlockFacings.None));
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction_1, BlockState blockState_2, IWorld iWorld_1, BlockPos blockPos_1, BlockPos blockPos_2) {
        return state.with(PropertyBlockFacings.FACINGS, handleBlockFacing(blockPos_1, iWorld_1.getWorld()).toProperty());
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext itemPlacementContext_1) {
        return super.getPlacementState(itemPlacementContext_1).with(PropertyBlockFacings.FACINGS, handleBlockFacing(itemPlacementContext_1.getPos(), itemPlacementContext_1.getWorld()).toProperty());
    }

    @Override
    protected void appendProperties(StateFactory.Builder<Block, BlockState> stateBuilder) {
        super.appendProperties(stateBuilder.with(PropertyBlockFacings.FACINGS));
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
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean isSimpleFullBlock(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1) {
        return false;
    }

    public BlockFacings handleBlockFacing(BlockPos pos, World world) {
        for (int i = 0; i < actualFacings.length; i++) {
            Direction direction = Direction.values()[i];
            BlockPos facingOffset = pos.offset(direction);
            this.actualFacings[i] = world.getBlockState(facingOffset).getBlock() instanceof BlockGlassBase;
        }
        return BlockFacings.from(this.actualFacings);
    }
}
