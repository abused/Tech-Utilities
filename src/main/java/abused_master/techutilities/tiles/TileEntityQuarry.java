package abused_master.techutilities.tiles;

import abused_master.techutilities.api.phase.EnergyStorage;
import abused_master.techutilities.registry.ModBlocks;
import abused_master.techutilities.registry.ModTiles;
import net.fabricmc.fabric.block.entity.ClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.*;

public class TileEntityQuarry extends BlockEntity implements Tickable, ClientSerializable {

    //TODO CHANGE AMOUNT TO 0
    public EnergyStorage storage = new EnergyStorage(100000, 100000);
    private boolean running = false;
    public BlockPos miningPos;
    public BlockPos[] torchPositions = new BlockPos[3];
    public Direction forwardDirection, horizontalDirection;
    public int maxSize = 1000, energyUsagePerBlock = 500, blocksX = 0, blocksZ = 0, miningSpeed = 0;
    public BlockState miningBlock = null;
    public boolean completedArea = false, miningError = false;

    public boolean silkTouch = false;
    public int fortuneLevel = 0, speedMultiplier = 1;

    public TileEntityQuarry() {
        super(ModTiles.QUARRY);
    }

    @Override
    public void fromTag(CompoundTag nbt) {
        super.fromTag(nbt);
        this.storage.readFromNBT(nbt);
        this.running = nbt.getBoolean("running");
        this.blocksX = nbt.getInt("blocksX");
        this.blocksZ = nbt.getInt("blocksZ");
        this.silkTouch = nbt.getBoolean("silkTouch");
        this.fortuneLevel = nbt.getInt("fortuneLevel");
        this.speedMultiplier = nbt.getInt("speedMultiplier");

        if(nbt.containsKey("positions")) {
            for (int i = 0; i < 2; i++) {
                this.torchPositions[i] = new BlockPos(nbt.getInt("torchX" + i), nbt.getInt("torchY" + i), nbt.getInt("torchZ" + i));
            }
        }

        if(nbt.containsKey("forwardDirection")) {
            this.forwardDirection = Direction.byId(nbt.getInt("forwardDirection"));
        }

        if(nbt.containsKey("horizontalDirection")) {
            this.horizontalDirection = Direction.byId(nbt.getInt("horizontalDirection"));
        }
    }

    @Override
    public CompoundTag toTag(CompoundTag nbt) {
        super.toTag(nbt);
        this.storage.writeEnergyToNBT(nbt);
        nbt.putBoolean("running", this.running);
        nbt.putInt("blocksX", this.blocksX);
        nbt.putInt("blocksZ", this.blocksZ);

        nbt.putBoolean("silkTouch", this.silkTouch);
        nbt.putInt("fortuneLevel", this.fortuneLevel);
        nbt.putInt("speedMultiplier", this.speedMultiplier);

        if (torchPositions.length > 0) {
            int posNumber = 0;
            for (BlockPos torchPos : torchPositions) {
                if(torchPos != null) {
                    nbt.putInt("torchX" + posNumber, torchPos.getX());
                    nbt.putInt("torchY" + posNumber, torchPos.getY());
                    nbt.putInt("torchZ" + posNumber, torchPos.getZ());
                    posNumber++;
                }
            }

            nbt.putBoolean("positions", true);
        }else {
            nbt.putBoolean("positions", false);
        }

        if(forwardDirection != null) {
            nbt.putInt("forwardDirection", forwardDirection.getId());
        }

        if(horizontalDirection != null) {
            nbt.putInt("horizontalDirection", horizontalDirection.getId());
        }

        return nbt;
    }

    @Override
    public void tick() {
        this.storage.recieveEnergy(1000);
        if(!miningError) {
            if (running && torchPositionsActive() && storage.getEnergyStored() >= energyUsagePerBlock && !completedArea) {
                if(!world.isChunkLoaded(pos.getX(), pos.getZ())) {
                    world.getChunk(pos).loadToWorld();
                }

                miningSpeed++;
                if (miningSpeed >= (5 / speedMultiplier)) {
                    Inventory inventory = getNearbyInventory();
                    this.mineBlocks(inventory);
                }
            } else if (running && !torchPositionsActive()) {
                this.setRunning(false);
                BlockState state = world.getBlockState(pos);
                world.updateListeners(pos, state, state, 3);
            }
        }else {
            if (insertItemIfPossible(getNearbyInventory(), new ItemStack(world.getBlockState(miningPos).getBlock()), true)) {
                this.setMiningError(false);
            }
        }
    }

    public void mineBlocks(Inventory inventory) {
        BlockPos firstCorner = new BlockPos(torchPositions[0]).down(1).offset(horizontalDirection, 1).offset(forwardDirection, 1);
        BlockPos secondCorner = new BlockPos(completeSquare().getX(), 0, completeSquare().getZ()).offset(horizontalDirection, -1).offset(forwardDirection, -1);
        Iterable<BlockPos> blocksInQuarry = BlockPos.iterateBoxPositions(firstCorner, secondCorner);

        for (Iterator<BlockPos> it = blocksInQuarry.iterator(); it.hasNext();) {
            BlockPos currentMiningPos = it.next();

            if (world.isAir(currentMiningPos) || world.getBlockState(currentMiningPos).getBlock() == Blocks.BEDROCK || world.getBlockState(currentMiningPos).getBlock() instanceof FluidBlock || world.getBlockEntity(currentMiningPos) != null) {
                continue;
            }

            if (miningSpeed >= (20 / speedMultiplier)) {
                this.miningPos = currentMiningPos;
                miningSpeed = 0;
                BlockState state = world.getBlockState(currentMiningPos);
                miningBlock = state;
                //List<ItemStack> drops = Block.getDroppedStacks(state, world instanceof ServerWorld ? (ServerWorld) world : null, currentMiningPos, world.getBlockEntity(currentMiningPos));
                List<ItemStack> drops = Arrays.asList(new ItemStack[]{new ItemStack(state.getBlock())});
                world.setBlockState(currentMiningPos, Blocks.AIR.getDefaultState());

                if (silkTouch) {
                    if (!insertItemIfPossible(inventory, new ItemStack(state.getBlock()), false)) {
                        setMiningError(true);
                    }
                } else {
                    for (ItemStack itemStack : drops) {
                        Random random = new Random();
                        ItemStack stackWithFortune = new ItemStack(itemStack.getItem(), fortuneLevel == 0 ? 1 : random.nextInt(fortuneLevel * 2));

                        if (!insertItemIfPossible(inventory, stackWithFortune, false)) {
                            setMiningError(true);
                        }
                    }
                }
            }

            if(!it.hasNext()) {
                completedArea = true;
            }
        }
    }

    public BlockPos completeSquare() {
        return new BlockPos(torchPositions[0]).offset(forwardDirection, blocksZ).offset(horizontalDirection, blocksX);
    }

    public boolean torchPositionsActive() {
        if(torchPositions.length <= 0) {
            return false;
        }

        for (BlockPos torchPos : torchPositions) {
            if(torchPos == null || world.getBlockState(torchPos).getBlock() != ModBlocks.QUARRY_MARKER) {
                return false;
            }
        }

        return true;
    }

    public boolean canRun() {
        runTorchLocationChecker();
        if(!torchPositionsActive()) {
            return false;
        }

        return true;
    }

    public boolean insertItemIfPossible(Inventory inventory, ItemStack stack, boolean simulate) {
        for (int i = 0; i < inventory.getInvSize(); i++) {
            if (inventory.getInvStack(i).isEmpty()) {
                if(!simulate)
                    inventory.setInvStack(i, stack);
                return true;
            }else if (!inventory.getInvStack(i).isEmpty() && ItemStack.areEqual(inventory.getInvStack(i), stack)) {
                if(inventory.getInvStack(i).getAmount() < 64) {
                    stack.addAmount(inventory.getInvStack(i).getAmount());
                    if(!simulate)
                        inventory.setInvStack(i, stack);
                    return true;
                }
            }
        }

        return false;
    }

    public Inventory getNearbyInventory() {
        for (Direction direction : Direction.values()) {
            BlockPos offsetPosition = new BlockPos(pos).offset(direction);
            BlockEntity entity = world.getBlockEntity(offsetPosition);
            if(entity != null && entity instanceof Inventory) {
                Inventory inventory = (Inventory) entity;
                return inventory;
            }
        }

        return null;
    }

    public void runTorchLocationChecker() {
        BlockPos adjacentTorchPos = null, lengthTorchPos = null, widthTorchPos = null;
        Direction adjacentTorchDirection = null;
        Direction horizontalTorchDirection = null;
        int distanceZ = 0, distanceX = 0;

        for (Direction direction : Direction.values()) {
            BlockPos directionPos = new BlockPos(pos).offset(direction);
            if(world.getBlockState(directionPos).getBlock() == ModBlocks.QUARRY_MARKER) {
                adjacentTorchPos = directionPos;
                adjacentTorchDirection = direction;

                if(adjacentTorchDirection == Direction.NORTH || adjacentTorchDirection == Direction.SOUTH) {
                    horizontalTorchDirection = Direction.EAST;
                }else if(adjacentTorchDirection == Direction.EAST || adjacentTorchDirection == Direction.WEST) {
                    horizontalTorchDirection = Direction.NORTH;
                }

                for (int z = 0; z < maxSize; z++) {
                    BlockPos zPos = new BlockPos(adjacentTorchPos).offset(direction, z);
                    if(world.getBlockState(zPos).getBlock() == ModBlocks.QUARRY_MARKER && !areEqual(zPos, adjacentTorchPos)) {
                        lengthTorchPos = zPos;
                        distanceZ = (int) adjacentTorchPos.distanceTo(lengthTorchPos);

                        for (int x = 0; x < maxSize; x++) {
                            BlockPos xPos = new BlockPos(adjacentTorchPos).offset(horizontalTorchDirection, x);
                            if(world.getBlockState(xPos).getBlock() == ModBlocks.QUARRY_MARKER && !areEqual(xPos, adjacentTorchPos)) {
                                widthTorchPos = xPos;
                                distanceX = (int) adjacentTorchPos.distanceTo(widthTorchPos);
                                break;
                            }

                            BlockPos xPosOpposite = new BlockPos(adjacentTorchPos).offset(horizontalTorchDirection.getOpposite(), x);

                            if(world.getBlockState(xPosOpposite).getBlock() == ModBlocks.QUARRY_MARKER && !areEqual(xPosOpposite, adjacentTorchPos)) {
                                widthTorchPos = xPosOpposite;
                                horizontalTorchDirection = horizontalTorchDirection.getOpposite();
                                distanceX = (int) adjacentTorchPos.distanceTo(widthTorchPos);
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
        }

        this.torchPositions[0] = adjacentTorchPos;
        this.torchPositions[1] = lengthTorchPos;
        this.torchPositions[2] = widthTorchPos;
        this.forwardDirection = adjacentTorchDirection;
        this.horizontalDirection = horizontalTorchDirection;
        this.blocksX = distanceX;
        this.blocksZ = distanceZ;
    }

    public boolean areEqual(BlockPos pos1, BlockPos pos2) {
        return pos1.getX() == pos2.getX() && pos1.getZ() == pos2.getZ() && pos1.getY() == pos2.getY();
    }

    public void setRunning(boolean running) {
        if (running) runTorchLocationChecker();
        BlockState state = world.getBlockState(pos);
        world.updateListeners(pos, state, state, 3);
        this.running = running;
    }

    public void setMiningError(boolean miningError) {
        BlockState state = world.getBlockState(pos);
        world.updateListeners(pos, state, state, 3);
        this.miningError = miningError;
    }

    public boolean isRunning() {
        return running;
    }

    @Override
    public void fromClientTag(CompoundTag tag) {
        this.fromTag(tag);
    }

    @Override
    public CompoundTag toClientTag(CompoundTag tag) {
        return this.toTag(tag);
    }
}
