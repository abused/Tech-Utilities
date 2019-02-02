package abused_master.techutilities.tiles.machine;

import abused_master.techutilities.registry.ModBlockEntities;
import abused_master.techutilities.tiles.BlockEntityEnergy;
import abused_master.techutilities.utils.InventoryHelper;
import abused_master.energy.EnergyStorage;
import abused_master.energy.IEnergyReceiver;
import abused_master.techutilities.utils.linker.ILinkerHandler;
import abused_master.techutilities.utils.render.hud.IHudSupport;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.StringTextComponent;
import net.minecraft.util.TagHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

//TODO MAKE UPGRADES FOR QUARRY
public class BlockEntityQuarry extends BlockEntityEnergy implements IHudSupport, IEnergyReceiver, ILinkerHandler {

    public EnergyStorage storage = new EnergyStorage(100000);
    private boolean running = false;
    public BlockPos miningPos = null, firstCorner = null, secondCorner = null;
    public int energyUsagePerBlock = 500, miningSpeed = 0;
    public BlockState miningBlock = null;
    public boolean completedArea = false, miningError = false, hasQuarryRecorder = false;

    public boolean silkTouch = false;
    public int fortuneLevel = 0, speedMultiplier = 1;

    public BlockEntityQuarry() {
        super(ModBlockEntities.QUARRY);
    }

    @Override
    public void fromTag(CompoundTag nbt) {
        super.fromTag(nbt);
        this.storage.readFromNBT(nbt);
        this.running = nbt.getBoolean("running");
        this.silkTouch = nbt.getBoolean("silkTouch");
        this.fortuneLevel = nbt.getInt("fortuneLevel");
        this.speedMultiplier = nbt.getInt("speedMultiplier");
        this.hasQuarryRecorder = nbt.getBoolean("hasQuarryRecorder");
        if (nbt.containsKey("firstCorner")) {
            this.firstCorner = BlockPos.fromLong(nbt.getLong("firstCorner"));
        }

        if (nbt.containsKey("secondCorner")) {
            this.secondCorner = BlockPos.fromLong(nbt.getLong("secondCorner"));
        }
    }

    @Override
    public CompoundTag toTag(CompoundTag nbt) {
        super.toTag(nbt);
        this.storage.writeEnergyToNBT(nbt);
        nbt.putBoolean("running", this.running);
        nbt.putBoolean("silkTouch", this.silkTouch);
        nbt.putInt("fortuneLevel", this.fortuneLevel);
        nbt.putInt("speedMultiplier", this.speedMultiplier);
        nbt.putBoolean("hasQuarryRecorder", this.hasQuarryRecorder);
        if(firstCorner != null) {
            nbt.putLong("firstCorner", firstCorner.asLong());
        }

        if(secondCorner != null) {
            nbt.putLong("secondCorner", secondCorner.asLong());
        }

        return nbt;
    }

    @Override
    public void tick() {
        if (running && canRun() && storage.getEnergyStored() >= energyUsagePerBlock && !completedArea) {
            if (!miningError) {
                miningSpeed++;
                if (miningSpeed >= (20 / speedMultiplier)) {
                    Inventory inventory = InventoryHelper.getNearbyInventory(world, pos);
                    this.mineBlocks(inventory);
                    storage.extractEnergy(energyUsagePerBlock);
                }
            } else {
                if (world.getBlockState(miningPos) != null && InventoryHelper.insertItemIfPossible(InventoryHelper.getNearbyInventory(world, pos), new ItemStack(world.getBlockState(miningPos).getBlock()), true)) {
                    this.setMiningError(false);
                }
            }
        } else if (running && !canRun()) {
            this.setRunning(false);
        }
    }

    public void mineBlocks(Inventory inventory) {
        Iterable<BlockPos> blocksInQuarry = BlockPos.iterateBoxPositions(secondCorner, firstCorner);

        for (BlockPos currentMiningPos : listBlocksInIterable(blocksInQuarry)) {
            if (world.isAir(currentMiningPos) || world.getBlockState(currentMiningPos).getBlock() == Blocks.BEDROCK || world.getBlockState(currentMiningPos).getBlock() instanceof FluidBlock || world.getBlockEntity(currentMiningPos) != null) {
                continue;
            }

            if (miningSpeed >= (20 / speedMultiplier)) {
                this.miningPos = currentMiningPos;
                miningSpeed = 0;
                BlockState state = world.getBlockState(currentMiningPos);
                miningBlock = state;
                if (!world.isClient) {
                    List<ItemStack> drops = Block.getDroppedStacks(state, (ServerWorld) world, currentMiningPos, world.getBlockEntity(currentMiningPos));
                    world.setBlockState(currentMiningPos, Blocks.AIR.getDefaultState());

                    if (silkTouch) {
                        if (!InventoryHelper.insertItemIfPossible(inventory, new ItemStack(state.getBlock()), false)) {
                            setMiningError(true);
                        }
                    } else {
                        for (ItemStack itemStack : drops) {
                            Random random = new Random();
                            ItemStack stackWithFortune = new ItemStack(itemStack.getItem(), fortuneLevel == 0 ? 1 : random.nextInt(fortuneLevel * 2));

                            if (!InventoryHelper.insertItemIfPossible(inventory, stackWithFortune, false)) {
                                setMiningError(true);
                            }
                        }
                    }
                }
            }
        }
    }

    public List<BlockPos> listBlocksInIterable(Iterable<BlockPos> iterable) {
        List<BlockPos> list = new ArrayList<>();

        for (BlockPos pos : iterable) {
            list.add(pos);
        }

        Collections.sort(list, Collections.reverseOrder());

        return list;
    }

    public BlockPos[] listFourCorners() {
        if(!blockPositionsActive()) {
            return null;
        }

        BlockPos corner1 = this.firstCorner.offset(Direction.UP, 1);
        BlockPos corner2 = new BlockPos(firstCorner.getX(), corner1.getY(), secondCorner.getZ());
        BlockPos corner3 = new BlockPos(secondCorner.getX(), corner1.getY(), firstCorner.getZ());
        BlockPos corner4 = new BlockPos(secondCorner.getX(), corner1.getY(), secondCorner.getZ());

        return new BlockPos[] {corner1, corner2, corner3, corner4};
    }

    public void setCorners(BlockPos firstCorner, BlockPos secondCorner) {
        this.firstCorner = firstCorner;
        this.secondCorner = secondCorner;
    }

    public boolean blockPositionsActive() {
        if(firstCorner == null || secondCorner == null) {
            return false;
        }

        return true;
    }

    public boolean canRun() {
        if (!blockPositionsActive()) {
            return false;
        }

        return true;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setMiningError(boolean miningError) {
        this.miningError = miningError;
    }

    public void setHasQuarryRecorder(boolean hasQuarryRecorder) {
        this.hasQuarryRecorder = hasQuarryRecorder;
    }

    public boolean isRunning() {
        return running;
    }

    @Override
    public Direction getBlockOrientation() {
        return null;
    }

    @Override
    public boolean isBlockAboveAir() {
        return getWorld().isAir(pos.up());
    }

    @Override
    public BlockPos getBlockPos() {
        return getPos();
    }

    @Override
    public List<String> getClientLog() {
        List<String> toDisplay = new ArrayList<>();
        if(miningPos == null) {
            toDisplay.add("No mining coords set");
        }else {
            toDisplay.add("Mining at: x: " + miningPos.getX() + " y: " + miningPos.getY() + " z: " + miningPos.getZ());
        }

        toDisplay.add("Energy: " + storage.getEnergyStored() + " / " + storage.getEnergyCapacity() + " PE");
        return toDisplay;
    }

    @Override
    public boolean receiveEnergy(int amount) {
        return handleEnergyReceive(storage, amount);
    }

    @Override
    public EnergyStorage getEnergyStorage() {
        return storage;
    }

    @Override
    public void link(PlayerEntity player, CompoundTag tag) {
        if(!world.isClient) {
            if (tag.containsKey("collectorPos")) {
                tag.remove("collectorPos");
            }
            tag.put("blockPos", TagHelper.serializeBlockPos(pos));
            player.addChatMessage(new StringTextComponent("Saved block position!"), true);
        }
    }
}
