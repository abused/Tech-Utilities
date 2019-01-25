package abused_master.techutilities.tiles.crystal;

import abused_master.techutilities.registry.ModBlockEntities;
import abused_master.techutilities.tiles.BlockEntityBase;
import abused_master.techutilities.utils.energy.EnergyStorage;
import abused_master.techutilities.utils.energy.IEnergyProvider;
import abused_master.techutilities.utils.energy.IEnergyReceiver;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class BlockEntityAutoCrystal extends BlockEntityBase implements IEnergyProvider, IEnergyReceiver {

    public EnergyStorage storage = new EnergyStorage(50000);
    public List<BlockPos> tilePositions = new ArrayList<>();
    public int areaLookupTimer = 0;
    public int sendPerTick = 250;
    public boolean loopingThroughTiles = false;

    public BlockEntityAutoCrystal() {
        super(ModBlockEntities.AUTO_ENERGY_CRYSTAL);
    }

    @Override
    public void fromTag(CompoundTag nbt) {
        super.fromTag(nbt);
        storage.readFromNBT(nbt);

        if(nbt.containsKey("tilePositions")) {
            CompoundTag positionsTag = nbt.getCompound("tilePositions");
            for (int i = 0; i < positionsTag.getSize(); i++) {
                BlockPos pos1 = BlockPos.fromLong(positionsTag.getLong("be" + i));
                if(pos1 != null && !tilePositions.contains(pos1)) {
                    tilePositions.add(pos1);
                }
            }
        }

        this.areaLookupTimer = nbt.getInt("areaLookupTimer");
    }

    @Override
    public CompoundTag toTag(CompoundTag nbt) {
        super.toTag(nbt);
        storage.writeEnergyToNBT(nbt);

        if(tilePositions.size() > 0) {
            CompoundTag positionsTag = new CompoundTag();
            for (BlockPos pos1 : tilePositions) {
                positionsTag.putLong("be" + tilePositions.indexOf(pos1), pos1.asLong());
            }

            nbt.put("tilePositions", positionsTag);
        }

        nbt.putInt("areaLookupTimer", this.areaLookupTimer);
        return nbt;
    }


    @Override
    public void tick() {
        areaLookupTimer++;
        if(areaLookupTimer >= 500) {
            loopingThroughTiles = true;
            lookForBlockEntities();
            areaLookupTimer = 0;
            loopingThroughTiles = false;
        }

        if(storage.getEnergyStored() >= sendPerTick && !loopingThroughTiles && tilePositions.size() > 0) {
            sendEnergy();
        }
    }

    public void sendEnergy() {
        for (BlockPos pos1 : tilePositions) {
            if (world.getBlockEntity(pos1) == null) {
                tilePositions.remove(pos1);
                continue;
            }

            sendEnergy(world, pos1, sendPerTick);
        }
    }

    public void lookForBlockEntities() {
        Iterable<BlockPos> posIterable = BlockPos.iterateBoxPositions(new BlockPos(pos.getX() - 12, pos.getY() - 12, pos.getZ() - 12), new BlockPos(pos.getX() + 12, pos.getY() + 12, pos.getZ() + 12));
        for (BlockPos pos1 : posIterable) {
            BlockEntity blockEntity = world.getBlockEntity(pos1);
            if(world.isAir(pos1) || blockEntity == null || blockEntity instanceof BlockEntityEnergyCollector || blockEntity instanceof BlockEntityEnergyCrystal) {
                continue;
            }

            if(blockEntity instanceof IEnergyReceiver) {
                if(!tilePositions.contains(pos1)) {
                    tilePositions.add(pos1);
                }
            }
        }
    }

    @Override
    public boolean sendEnergy(World world, BlockPos pos, int amount) {
        return storage.sendEnergy(world, pos, amount);
    }

    @Override
    public boolean receiveEnergy(int amount) {
        if(canReceive(amount)) {
            storage.recieveEnergy(amount);
            return true;
        }

        return false;
    }

    public boolean canReceive(int amount) {
        return (storage.getEnergyCapacity() - storage.getEnergyStored()) >= amount;
    }

    @Override
    public EnergyStorage getEnergyStorage() {
        return storage;
    }
}
