package abused_master.techutilities.blocks.machines;

import abused_master.techutilities.items.ItemQuarryRecorder;
import abused_master.techutilities.registry.ModItems;
import abused_master.techutilities.tiles.TileEntityQuarry;
import net.fabricmc.fabric.block.FabricBlockSettings;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.block.BlockRenderLayer;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.StringTextComponent;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class Quarry extends BlockWithEntity {

    public Quarry() {
        super(FabricBlockSettings.of(Material.STONE).build().strength(1, 1));
    }

    @Override
    public boolean activate(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, Direction direction, float float_1, float float_2, float float_3) {
        TileEntityQuarry quarry = (TileEntityQuarry) world.getBlockEntity(blockPos);
        ItemStack stack = playerEntity.getStackInHand(hand);

        if(!(stack.getItem() instanceof ItemQuarryRecorder)) {
            if(playerEntity.isSneaking() && stack.isEmpty() && quarry.hasQuarryRecorder) {
                playerEntity.setStackInHand(hand, new ItemStack(ModItems.recorder));
                quarry.setRunning(false);
                quarry.setHasQuarryRecorder(false);
                quarry.setCorners(null, null);
            }

            if (!quarry.isRunning() && quarry.canRun()) {
                quarry.setRunning(true);
                if (!quarry.blockPositionsActive()) {
                    if (!world.isClient) {
                        playerEntity.addChatMessage(new StringTextComponent("Error locating quarry mining corners!"), false);
                    }
                    quarry.setRunning(false);
                } else {
                    if (!world.isClient) {
                        playerEntity.addChatMessage(new StringTextComponent("Set quarry to now running!"), false);
                    }
                }
            }
        }else {
            CompoundTag tag = stack.getTag();

            if (tag == null) {
                if (!world.isClient) {
                    playerEntity.addChatMessage(new StringTextComponent("Missing coordinate points for recorder"), true);
                }
                return true;
            }

            if (!tag.containsKey("coordinates1") || !tag.containsKey("coordinates2")) {
                if (!world.isClient) {
                    playerEntity.addChatMessage(new StringTextComponent("Missing coordinate points for recorder"), true);
                }
                return true;
            }

            int x = tag.getIntArray("coordinates1")[0], y = tag.getIntArray("coordinates1")[1], z = tag.getIntArray("coordinates1")[2];
            int x1 = tag.getIntArray("coordinates2")[0], y1 = tag.getIntArray("coordinates2")[1], z1 = tag.getIntArray("coordinates2")[2];
            quarry.setCorners(new BlockPos(x, y, z), new BlockPos(x1, y1, z1));
            quarry.hasQuarryRecorder = true;
            playerEntity.setStackInHand(Hand.MAIN, ItemStack.EMPTY);
            playerEntity.addChatMessage(new StringTextComponent("Successfully linked quarry to positions"), true);
        }

        return true;
    }

    @Override
    public void onBroken(IWorld world, BlockPos blockPos, BlockState blockState) {
        TileEntityQuarry quarry = (TileEntityQuarry) world.getBlockEntity(blockPos);

        if(quarry != null && quarry.hasQuarryRecorder) {
            world.spawnEntity(new ItemEntity(world.getWorld(), blockPos.getX(), blockPos.getY(), blockPos.getZ(), new ItemStack(ModItems.recorder)));
        }
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
