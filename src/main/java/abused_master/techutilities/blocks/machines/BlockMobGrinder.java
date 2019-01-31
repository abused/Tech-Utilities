package abused_master.techutilities.blocks.machines;

import abused_master.techutilities.TechUtilities;
import abused_master.techutilities.blocks.BlockWithEntityBase;
import abused_master.techutilities.tiles.machine.BlockEntityMobGrinder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

import javax.annotation.Nullable;

public class BlockMobGrinder extends BlockWithEntityBase {

    public static final DirectionProperty FACING = HorizontalFacingBlock.field_11177;

    public BlockMobGrinder() {
        super("mob_grinder", Material.STONE, 1.0f, TechUtilities.modItemGroup);
        this.setDefaultState(this.stateFactory.getDefaultState().with(FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext itemPlacementContext_1) {
        return this.getDefaultState().with(FACING, itemPlacementContext_1.getPlayerHorizontalFacing());
    }

    @Override
    public BlockState applyRotation(BlockState blockState_1, Rotation rotation_1) {
        return blockState_1.with(FACING, rotation_1.method_10503(blockState_1.get(FACING)));
    }

    @Override
    public BlockState applyMirror(BlockState blockState_1, Mirror mirror_1) {
        return blockState_1.applyRotation(mirror_1.getRotation(blockState_1.get(FACING)));
    }

    @Override
    protected void appendProperties(StateFactory.Builder<Block, BlockState> stateFactory$Builder_1) {
        stateFactory$Builder_1.with(new Property[]{FACING});
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return new BlockEntityMobGrinder();
    }
}
