package abused_master.techutilities.tiles;

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

public class TileEntityQuarry extends BlockEntity implements Tickable, ClientSerializable {

    private boolean running = false;
    public BlockPos miningPos;
    public BlockPos[] torchPositions = new BlockPos[3];
    public Direction forwardDirection, horizontalDirection;
    public int maxSize = 1000;
    public int blocksX = 0, blocksZ = 0;
    public int miningSpeed = 0;

    public TileEntityQuarry() {
        super(ModTiles.QUARRY);
    }

    @Override
    public void fromTag(CompoundTag nbt) {
        super.fromTag(nbt);
        this.running = nbt.getBoolean("running");
        this.blocksX = nbt.getInt("blocksX");
        this.blocksZ = nbt.getInt("blocksZ");
        if (nbt.containsKey("x1") && nbt.containsKey("y1") && nbt.containsKey("z1")) {
            miningPos = new BlockPos(nbt.getInt("x1"), nbt.getInt("y1"), nbt.getInt("z1"));
        }

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
        nbt.putBoolean("running", this.running);
        nbt.putInt("blocksX", this.blocksX);
        nbt.putInt("blocksZ", this.blocksZ);
        if (miningPos != null) {
            nbt.putInt("x1", miningPos.getX());
            nbt.putInt("y1", miningPos.getY());
            nbt.putInt("z1", miningPos.getZ());
        }

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
        if (running && torchPositionsActive()) {
            miningSpeed++;
            Inventory inventory = getNearbyInventory();
            for (int y = pos.getY() - 1; y > 0; y--) {
                this.mineBlocks(inventory, y);
            }
        }else if(running && !torchPositionsActive()) {
            this.setRunning(false);
        }
    }

    public void mineBlocks(Inventory inventory, int yPos) {
        for (int x = 1; x < blocksX; x++) {
            BlockPos currentMiningPos = new BlockPos(pos.getX(), yPos, pos.getZ()).offset(horizontalDirection, x).offset(forwardDirection, 1);

            for (int z = 1; z < blocksZ; z++) {
                currentMiningPos = currentMiningPos.offset(forwardDirection, 1);
                BlockState state = world.getBlockState(currentMiningPos);

                if(!world.isAir(currentMiningPos) && state.getBlock() != Blocks.BEDROCK && !(state.getBlock() instanceof FluidBlock) && world.getBlockEntity(currentMiningPos) == null) {
                    this.miningPos = currentMiningPos;
                    ItemStack blockItemStack = new ItemStack(state.getBlock());
                    if(miningSpeed >= 20) {
                        miningSpeed = 0;
                        world.setBlockState(currentMiningPos, Blocks.AIR.getDefaultState());
                        if (!insertItemIfPossible(inventory, blockItemStack)) {
                            setRunning(false);
                        }
                    }
                }
            }
        }
    }

    public boolean torchPositionsActive() {
        if(torchPositions.length <= 0 || torchPositions[0] == null || torchPositions[1] == null || torchPositions[2] == null || getNearbyInventory() == null) {
            return false;
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

    public boolean insertItemIfPossible(Inventory inventory, ItemStack stack) {
        for (int i = 0; i < inventory.getInvSize(); i++) {
            if (inventory.getInvStack(i).isEmpty()) {
                inventory.setInvStack(i, stack);
                return true;
            }else if (ItemStack.areEqual(inventory.getInvStack(i), stack)) {
                if(inventory.getInvStack(i).getAmount() < 64) {
                    inventory.getInvStack(i).addAmount(1);
                    stack.addAmount(inventory.getInvStack(i).getAmount());
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

    //TODO CHANGE TORCHES TO CUSTOM TORCH
    public void runTorchLocationChecker() {
        BlockPos adjacentTorchPos = null, lengthTorchPos = null, widthTorchPos = null;
        Direction adjacentTorchDirection = null;
        Direction horizontalTorchDirection = null;
        int distanceZ = 0, distanceX = 0;

        for (Direction direction : Direction.values()) {
            BlockPos directionPos = new BlockPos(pos).offset(direction);
            if(world.getBlockState(directionPos).getBlock() == Blocks.TORCH) {
                adjacentTorchPos = directionPos;
                adjacentTorchDirection = direction;

                if(adjacentTorchDirection == Direction.NORTH || adjacentTorchDirection == Direction.SOUTH) {
                    horizontalTorchDirection = Direction.EAST;
                }else if(adjacentTorchDirection == Direction.EAST || adjacentTorchDirection == Direction.WEST) {
                    horizontalTorchDirection = Direction.NORTH;
                }

                for (int z = 0; z < maxSize; z++) {
                    BlockPos zPos = new BlockPos(adjacentTorchPos).offset(direction, z);
                    if(world.getBlockState(zPos).getBlock() == Blocks.TORCH && !areEqual(zPos, adjacentTorchPos)) {
                        lengthTorchPos = zPos;
                        distanceZ = (int) adjacentTorchPos.distanceTo(lengthTorchPos);

                        for (int x = 0; x < maxSize; x++) {
                            BlockPos xPos = new BlockPos(adjacentTorchPos).offset(horizontalTorchDirection, x);
                            if(world.getBlockState(xPos).getBlock() == Blocks.TORCH && !areEqual(xPos, adjacentTorchPos)) {
                                widthTorchPos = xPos;
                                distanceX = (int) adjacentTorchPos.distanceTo(widthTorchPos);
                                break;
                            }

                            BlockPos xPosOpposite = new BlockPos(adjacentTorchPos).offset(horizontalTorchDirection.getOpposite(), x);

                            if(world.getBlockState(xPosOpposite).getBlock() == Blocks.TORCH && !areEqual(xPosOpposite, adjacentTorchPos)) {
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
        this.running = running;
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
