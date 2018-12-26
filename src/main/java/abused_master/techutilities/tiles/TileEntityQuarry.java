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
    public int maxSize = 500;

    public TileEntityQuarry() {
        super(ModTiles.QUARRY);
    }

    @Override
    public void fromTag(CompoundTag nbt) {
        super.fromTag(nbt);
        this.running = nbt.getBoolean("running");
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
        if (running && canRun()) {
            Inventory inventory = getNearbyInventory();
            BlockPos currentMiningPos;
            int distanceForward = (int) torchPositions[0].distanceTo(torchPositions[1]);
            int distanceSide = (int) torchPositions[1].distanceTo(torchPositions[2]);

            for (int y = pos.getY(); y > 0; y--) {
                currentMiningPos = new BlockPos(pos);
                currentMiningPos.down(1);

                for (int x = 0; x < distanceSide - 1; x++) {
                    currentMiningPos.offset(horizontalDirection, 1);

                    for (int z = 0; z < distanceForward - 1; z++) {
                        currentMiningPos.offset(forwardDirection, 1);
                        BlockState state = world.getBlockState(currentMiningPos);

                        if (!state.isAir() && state.getBlock() != Blocks.BEDROCK && !(state.getBlock() instanceof FluidBlock)) {
                            this.miningPos = currentMiningPos;
                            ItemStack blockItemStack = new ItemStack(state.getBlock());
                            world.setBlockState(currentMiningPos, Blocks.AIR.getDefaultState());

                            //Stop quarry if it has nowhere to place blocks
                            //if(!this.insertItemIfPossible(inventory, blockItemStack)) {
                            //    this.setRunning(false);
                            //}
                        }
                    }

                }
            }
        }
    }

    public boolean canRun() {
        if(torchPositions.length <= 0 || torchPositions[0] == null || torchPositions[1] == null || torchPositions[2] == null || getNearbyInventory() == null) {
            return false;
        }

        return true;
    }

    public boolean insertItemIfPossible(Inventory inventory, ItemStack stack) {
        for (int i = 0; i < inventory.getInvSize() - 1; i++) {
            if (inventory.getInvStack(i).isEmpty()) {
                inventory.setInvStack(i, stack);
                return true;
            }else if (ItemStack.areEqual(inventory.getInvStack(i), stack)) {
                if(inventory.getInvStack(i).getAmount() < 64) {
                    inventory.getInvStack(i).addAmount(1);
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
        BlockPos firstTorch = null, secondTorch = null, thirdTorch = null;
        Direction torchDirection = null;

        //Loop for first and second torch
        for (Direction direction : Direction.values()) {
            BlockPos directionPos = new BlockPos(pos).offset(direction);
            BlockState block = world.getBlockState(directionPos);
            if (block.getBlock() == Blocks.TORCH) {
                torchDirection = direction;
                firstTorch = directionPos;

                for (int i = 0; i < maxSize; i++) {
                    directionPos.offset(direction, 1);
                    block = world.getBlockState(directionPos);

                    if (!block.isAir() && block.getBlock() == Blocks.TORCH) {
                        secondTorch = directionPos;
                        break;
                    }
                }

                break;
            }
        }

        Direction thirdTorchDirection = null;

        if (torchDirection == Direction.NORTH || torchDirection == Direction.SOUTH) {
            thirdTorchDirection = Direction.WEST;
        } else {
            thirdTorchDirection = Direction.NORTH;
        }

        //Loop for third torch
        for (int i = 0; i < (maxSize * 2); i++) {
            BlockPos leftRightLookup = null;
            if (i <= maxSize) {
                leftRightLookup = new BlockPos(firstTorch).offset(thirdTorchDirection, i);
                BlockState block = world.getBlockState(leftRightLookup);
                if (!block.isAir() && block.getBlock() == Blocks.TORCH) {
                    thirdTorch = leftRightLookup;
                    break;
                }
            } else {
                leftRightLookup = new BlockPos(firstTorch).offset(thirdTorchDirection, -i);
                BlockState block = world.getBlockState(leftRightLookup);
                if (!block.isAir() && block.getBlock() == Blocks.TORCH) {
                    thirdTorch = leftRightLookup;
                    break;
                }
            }
        }

        this.torchPositions[0] = firstTorch;
        this.torchPositions[1] = secondTorch;
        this.torchPositions[2] = thirdTorch;
        this.forwardDirection = torchDirection;
        this.horizontalDirection = thirdTorchDirection;
    }

    public void setRunning(boolean running) {
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
