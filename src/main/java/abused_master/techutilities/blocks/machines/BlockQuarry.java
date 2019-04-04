package abused_master.techutilities.blocks.machines;

import abused_master.abusedlib.blocks.BlockWithEntityBase;
import abused_master.techutilities.TechUtilities;
import abused_master.techutilities.items.ItemQuarryRecorder;
import abused_master.techutilities.registry.ModItems;
import abused_master.techutilities.tiles.machine.BlockEntityEnergyCharger;
import abused_master.techutilities.tiles.machine.BlockEntityQuarry;
import net.minecraft.block.BlockRenderLayer;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sortme.ItemScatterer;
import net.minecraft.text.StringTextComponent;
import net.minecraft.text.Style;
import net.minecraft.text.TextFormat;
import net.minecraft.util.Hand;
import net.minecraft.util.TagHelper;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class BlockQuarry extends BlockWithEntityBase {

    public BlockQuarry() {
        super("quarry", Material.STONE, 1.0f, TechUtilities.modItemGroup);
    }

    @Override
    public boolean activate(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockHitResult blockHitResult) {
        BlockEntityQuarry quarry = (BlockEntityQuarry) world.getBlockEntity(blockPos);
        ItemStack stack = playerEntity.getStackInHand(hand);

        if (!(stack.getItem() instanceof ItemQuarryRecorder)) {
            if (playerEntity.isSneaking() && stack.isEmpty() && quarry.hasQuarryRecorder) {
                playerEntity.setStackInHand(hand, new ItemStack(ModItems.RECORDER));
                quarry.setRunning(false);
                quarry.setHasQuarryRecorder(false);
                quarry.setCorners(null, null);
                return true;
            }

            if (!quarry.isRunning() && quarry.blockPositionsActive()) {
                quarry.setRunning(true);
                quarry.cacheMiningArea();
                if (!world.isClient) {
                    playerEntity.addChatMessage(new StringTextComponent("Set quarry to now running!").setStyle(new Style().setColor(TextFormat.DARK_RED)), true);
                }
            } else if (!quarry.blockPositionsActive()) {
                playerEntity.addChatMessage(new StringTextComponent("Error, no mining positions are set!").setStyle(new Style().setColor(TextFormat.DARK_RED)), true);
            }
        } else {
            CompoundTag tag = stack.getTag();

            if (tag == null) {
                if (!world.isClient) {
                    playerEntity.addChatMessage(new StringTextComponent("Missing coordinate points for recorder").setStyle(new Style().setColor(TextFormat.DARK_RED)), true);
                }
                return true;
            }

            if (!tag.containsKey("coordinates1") || !tag.containsKey("coordinates2")) {
                if (!world.isClient) {
                    playerEntity.addChatMessage(new StringTextComponent("Missing coordinate points for recorder").setStyle(new Style().setColor(TextFormat.DARK_RED)), true);
                }
                return true;
            }

            quarry.setCorners(TagHelper.deserializeBlockPos(tag.getCompound("coordinates1")), TagHelper.deserializeBlockPos(tag.getCompound("coordinates2")));
            quarry.hasQuarryRecorder = true;
            playerEntity.setStackInHand(Hand.MAIN, ItemStack.EMPTY);
            playerEntity.addChatMessage(new StringTextComponent("Successfully linked quarry to positions").setStyle(new Style().setColor(TextFormat.DARK_RED)), true);
        }

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

    @Override
    public void onBlockRemoved(BlockState state, World world, BlockPos pos, BlockState state2, boolean boolean_1) {
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if(state.getBlock() != state2.getBlock() && blockEntity instanceof BlockEntityQuarry) {
            if(((BlockEntityQuarry) blockEntity).hasQuarryRecorder) {
                ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModItems.RECORDER));
            }
            world.updateHorizontalAdjacent(pos, this);
        }

        super.onBlockRemoved(state, world, pos, state2, boolean_1);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return new BlockEntityQuarry();
    }
}
