package abused_master.techutilities.tiles.machine;

import abused_master.abusedlib.client.render.hud.IHudSupport;
import abused_master.abusedlib.tiles.BlockEntityBase;
import abused_master.abusedlib.utils.InventoryHelper;
import abused_master.techutilities.registry.ModBlockEntities;
import abused_master.techutilities.utils.linker.ILinkerHandler;
import nerdhub.cardinalenergy.api.IEnergyHandler;
import nerdhub.cardinalenergy.impl.EnergyStorage;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.StringTextComponent;
import net.minecraft.util.TagHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.*;

//TODO MAKE UPGRADES FOR QUARRY
public class BlockEntityQuarry extends BlockEntityBase implements IEnergyHandler, IHudSupport, ILinkerHandler {

    public EnergyStorage storage = new EnergyStorage(100000);
    public List<BlockPos> cachedAreaPos = new ArrayList<>();
    public BlockPos miningPos = null, firstCorner = null, secondCorner = null;
    public int energyUsagePerBlock = 500, miningSpeed = 0;
    public BlockState miningBlock = null;
    public boolean miningError = false, hasQuarryRecorder = false, running = false;

    public boolean silkTouch = false;
    public int fortuneLevel = 0, speedMultiplier = 1;

    public BlockEntityQuarry() {
        super(ModBlockEntities.QUARRY);
    }

    @Override
    public void fromTag(CompoundTag tag) {
        super.fromTag(tag);
        this.storage.readEnergyFromTag(tag);
        if(tag.containsKey("cachedAreaPos")) {
            this.cachedAreaPos.clear();
            ListTag listTag = tag.getList("cachedAreaPos", NbtType.COMPOUND);

            for (Iterator<Tag> it = listTag.iterator(); it.hasNext(); ) {
                CompoundTag compoundTag = (CompoundTag) it.next();
                cachedAreaPos.add(TagHelper.deserializeBlockPos(compoundTag));
            }
        }

        this.running = tag.getBoolean("running");
        this.silkTouch = tag.getBoolean("silkTouch");
        this.fortuneLevel = tag.getInt("fortuneLevel");
        this.speedMultiplier = tag.getInt("speedMultiplier");
        this.hasQuarryRecorder = tag.getBoolean("hasQuarryRecorder");
        if (tag.containsKey("firstCorner")) {
            this.firstCorner = TagHelper.deserializeBlockPos(tag.getCompound("firstCorner"));
        }

        if (tag.containsKey("secondCorner")) {
            this.secondCorner = TagHelper.deserializeBlockPos(tag.getCompound("secondCorner"));
        }
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        this.storage.writeEnergyToTag(tag);
        if(!this.cachedAreaPos.isEmpty()) {
            ListTag listTag = new ListTag();

            for (BlockPos areaPos : cachedAreaPos) {
                listTag.add(TagHelper.serializeBlockPos(areaPos));
            }

            tag.put("cachedAreaPos", listTag);
        }

        tag.putBoolean("running", this.running);
        tag.putBoolean("silkTouch", this.silkTouch);
        tag.putInt("fortuneLevel", this.fortuneLevel);
        tag.putInt("speedMultiplier", this.speedMultiplier);
        tag.putBoolean("hasQuarryRecorder", this.hasQuarryRecorder);
        if(firstCorner != null) {
            tag.put("firstCorner", TagHelper.serializeBlockPos(firstCorner));
        }

        if(secondCorner != null) {
            tag.put("secondCorner", TagHelper.serializeBlockPos(secondCorner));
        }

        return tag;
    }

    @Override
    public void tick() {
        if (running && canRun() && storage.getEnergyStored() >= energyUsagePerBlock) {
            if (!miningError) {
                miningSpeed++;
                if (miningSpeed >= (20 / speedMultiplier)) {
                    Inventory inventory = InventoryHelper.getNearbyInventory(world, pos);
                    this.mineBlocks(inventory);
                }
            } else if (world.getBlockState(miningPos) != null && InventoryHelper.insertItemIfPossible(InventoryHelper.getNearbyInventory(world, pos), new ItemStack(world.getBlockState(miningPos).getBlock()), true)) {
                this.setMiningError(false);
            }
        } else if (running && !canRun()) {
            this.setRunning(false);
        }
    }

    public void mineBlocks(Inventory inventory) {
        for (Iterator<BlockPos> it = cachedAreaPos.iterator(); it.hasNext();) {
            BlockPos currentMiningPos = it.next();

            if (world.isAir(currentMiningPos) || world.getBlockState(currentMiningPos).getBlock() == Blocks.BEDROCK || world.getBlockState(currentMiningPos).getBlock() instanceof FluidBlock || world.getBlockEntity(currentMiningPos) != null) {
                it.remove();
                continue;
            }

            if (miningSpeed >= (20 / speedMultiplier)) {
                this.miningPos = currentMiningPos;
                miningSpeed = 0;
                BlockState state = world.getBlockState(currentMiningPos);
                miningBlock = state;

                if(!world.isClient()) {
                    List<ItemStack> drops = Block.getDroppedStacks(state, (ServerWorld) world, currentMiningPos, world.getBlockEntity(currentMiningPos));
                    world.setBlockState(currentMiningPos, Blocks.AIR.getDefaultState());

                    if (silkTouch) {
                        if (!InventoryHelper.insertItemIfPossible(inventory, new ItemStack(state.getBlock()), false)) {
                            setMiningError(true);
                        }else {
                            storage.extractEnergy(energyUsagePerBlock);
                            it.remove();
                        }
                    } else {
                        for (ItemStack itemStack : drops) {
                            Random random = new Random();
                            ItemStack stackWithFortune = new ItemStack(itemStack.getItem(), fortuneLevel == 0 ? 1 : random.nextInt(fortuneLevel * 2));

                            if (!InventoryHelper.insertItemIfPossible(inventory, stackWithFortune, false)) {
                                setMiningError(true);
                            }else {
                                storage.extractEnergy(energyUsagePerBlock);
                                it.remove();
                            }
                        }
                    }
                }
            }
        }
    }

    public void cacheMiningArea() {
        Iterable<BlockPos> blocksInQuarry = BlockPos.iterateBoxPositions(secondCorner, firstCorner);
        cachedAreaPos = listBlocksInIterable(blocksInQuarry);

        this.markDirty();
    }

    public List<BlockPos> listBlocksInIterable(Iterable<BlockPos> iterable) {
        List<BlockPos> list = new ArrayList<>();

        for (Iterator<BlockPos> it = iterable.iterator(); it.hasNext(); ) {
            BlockPos pos = it.next();
            list.add(pos.toImmutable());
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
        if (!blockPositionsActive() && !cachedAreaPos.isEmpty()) {
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

        toDisplay.add("Energy: " + storage.getEnergyStored() + " / " + storage.getEnergyCapacity() + " CE");
        return toDisplay;
    }

    @Override
    public EnergyStorage getEnergyStorage(Direction direction) {
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
