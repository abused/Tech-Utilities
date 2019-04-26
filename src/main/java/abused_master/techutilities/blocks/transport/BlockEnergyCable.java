package abused_master.techutilities.blocks.transport;

import abused_master.abusedlib.blocks.BlockWithEntityBase;
import abused_master.techutilities.TechUtilities;
import abused_master.techutilities.tiles.transport.BlockEntityEnergyCable;
import nerdhub.cardinalenergy.api.IEnergyHandler;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockEnergyCable extends BlockWithEntityBase {

    public static final BooleanProperty[] PROPS = new BooleanProperty[] {
            BooleanProperty.create("down"),
            BooleanProperty.create("up"),
            BooleanProperty.create("north"),
            BooleanProperty.create("south"),
            BooleanProperty.create("west"),
            BooleanProperty.create("east"),
            BooleanProperty.create("none")
    };

    public BlockEnergyCable() {
        super("energy_cable", Material.STONE, 1.0f, TechUtilities.modItemGroup);
        this.setDefaultState(this.getStateFactory().getDefaultState().with(PROPS[6], true));
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
        BlockEntityEnergyCable energyCable = (BlockEntityEnergyCable) world.getBlockEntity(pos);

        for (Direction direction : Direction.values()) {
            BlockEntity blockEntity = world.getBlockEntity(pos.offset(direction));
            if(blockEntity instanceof IEnergyHandler) {
                IEnergyHandler energyHandler = (IEnergyHandler) blockEntity;

                if(energyHandler.isEnergyProvider(direction)) {
                    energyCable.addPowerSource(pos.offset(direction));

                }else if(energyHandler.isEnergyReceiver(direction)) {
                    energyCable.addConnection(pos.offset(direction));
                }
            }
        }
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos neighborPos, boolean boolean_1) {
        BlockEntityEnergyCable energyCable = (BlockEntityEnergyCable) world.getBlockEntity(pos);

        if(world.isAir(neighborPos)) {
            if (energyCable.powerSources.contains(neighborPos)) {
                energyCable.removePowerSource(neighborPos);
                return;
            } else if (energyCable.connections.contains(neighborPos)) {
                energyCable.removeConnection(neighborPos);
                return;
            }
        }

        BlockEntity blockEntity = world.getBlockEntity(neighborPos);
        if(blockEntity instanceof IEnergyHandler) {
            IEnergyHandler energyHandler = (IEnergyHandler) blockEntity;
            Direction direction = Direction.fromVector(pos.subtract(neighborPos));

            if(energyHandler.isEnergyProvider(direction.getOpposite())) {
                energyCable.addPowerSource(neighborPos);
            }else if(energyHandler.isEnergyReceiver(direction.getOpposite())) {
                energyCable.addConnection(neighborPos);
            }
        }else if(blockEntity instanceof BlockEntityEnergyCable) {
            ((BlockEntityEnergyCable) blockEntity).powerSources.addAll(energyCable.powerSources);
            ((BlockEntityEnergyCable) blockEntity).updateEntity();
        }

        super.neighborUpdate(state, world, pos, block, neighborPos, boolean_1);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return getStateWithProps(this.getStateFactory().getDefaultState(), context.getWorld(), context.getBlockPos());
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState state2, IWorld world, BlockPos blockPos, BlockPos blockPos_2) {
        return getStateWithProps(state, world, blockPos);
    }

    public BlockState getStateWithProps(BlockState state, IWorld world, BlockPos pos) {
        for (int i = 0; i < PROPS.length - 1; i++) {
            Direction facing = Direction.values()[i];
            BlockEntity blockEntity = world.getBlockEntity(pos.offset(facing));

            state = state.with(PROPS[i], blockEntity instanceof BlockEntityEnergyCable || (blockEntity instanceof IEnergyHandler && ((IEnergyHandler) blockEntity).canConnectEnergy(facing.getOpposite())));
        }

        return state;
    }

    @Override
    public void appendProperties(StateFactory.Builder<Block, BlockState> stateBuilder) {
        super.appendProperties(stateBuilder.with(PROPS));
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
    public BlockEntity createBlockEntity(BlockView blockView) {
        return new BlockEntityEnergyCable();
    }
}
