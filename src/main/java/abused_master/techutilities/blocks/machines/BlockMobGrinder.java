package abused_master.techutilities.blocks.machines;

import abused_master.abusedlib.blocks.BlockWithEntityBase;
import abused_master.techutilities.TechUtilities;
import abused_master.techutilities.tiles.machine.BlockEntityMobGrinder;
import abused_master.techutilities.utils.IWrenchable;
import abused_master.techutilities.utils.WrenchHelper;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockMobGrinder extends BlockWithEntityBase implements IWrenchable {

    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;

    public BlockMobGrinder() {
        super("mob_grinder", TechUtilities.modItemGroup, FabricBlockSettings.of(Material.STONE).strength(1.0f, 100.0f).build());
        this.setDefaultState(this.stateFactory.getDefaultState().with(FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext itemPlacementContext_1) {
        return this.getDefaultState().with(FACING, itemPlacementContext_1.getPlayerHorizontalFacing());
    }

    @Override
    public BlockState rotate(BlockState blockState_1, BlockRotation rotation) {
        return blockState_1.with(FACING, rotation.rotate(blockState_1.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState blockState_1, BlockMirror mirror) {
        return blockState_1.rotate(mirror.getRotation(blockState_1.get(FACING)));
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

    @Override
    public boolean onWrenched(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        return player.isSneaking() ? WrenchHelper.dropBlock(world, pos) : WrenchHelper.rotateBlock(world, pos, state);
    }
}
