package abused_master.techutilities.blocks.machines;

import abused_master.abusedlib.blocks.BlockWithEntityBase;
import abused_master.techutilities.TechUtilities;
import abused_master.techutilities.registry.ModBlockEntities;
import abused_master.techutilities.tiles.machine.BlockEntityFarmer;
import abused_master.techutilities.tiles.machine.BlockEntityPulverizer;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sortme.ItemScatterer;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockFarmer extends BlockWithEntityBase {

    public BlockFarmer() {
        super("farmer", Material.STONE, 1.0f, TechUtilities.modItemGroup);
    }

    @Override
    public boolean activate(BlockState state, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockHitResult blockHitResult) {
        if (!world.isClient) {
            ContainerProviderRegistry.INSTANCE.openContainer(ModBlockEntities.FARMER_CONTAINER, player, buf -> buf.writeBlockPos(blockPos));
        }

        return true;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
        BlockEntityFarmer farmer = (BlockEntityFarmer) world.getBlockEntity(pos);
        Iterable<BlockPos> areaPos = BlockPos.iterateBoxPositions(new BlockPos(pos.getX() - farmer.farmerRange, pos.getY() - 1, pos.getZ() - farmer.farmerRange), new BlockPos(pos.getX() + farmer.farmerRange, pos.getY() - 1, pos.getZ() + farmer.farmerRange));
        for (BlockPos blockPos : areaPos) {
            if(!world.isAir(blockPos) && world.getBlockState(blockPos).getBlock() == Blocks.GRASS_BLOCK || world.getBlockState(blockPos).getBlock() == Blocks.DIRT) {
                world.setBlockState(blockPos, Blocks.FARMLAND.getDefaultState(), 3);
            }
        }
    }

    @Override
    public void onBlockRemoved(BlockState state, World world, BlockPos pos, BlockState state2, boolean boolean_1) {
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if(state.getBlock() != state2.getBlock() && blockEntity instanceof BlockEntityFarmer) {
            ItemScatterer.spawn(world, pos, (BlockEntityFarmer) blockEntity);
            world.updateHorizontalAdjacent(pos, this);
        }

        super.onBlockRemoved(state, world, pos, state2, boolean_1);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return new BlockEntityFarmer();
    }
}
