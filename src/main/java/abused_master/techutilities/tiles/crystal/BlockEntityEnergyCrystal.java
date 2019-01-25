package abused_master.techutilities.tiles.crystal;

import abused_master.techutilities.registry.ModBlockEntities;
import abused_master.techutilities.tiles.BlockEntityBase;
import abused_master.techutilities.utils.energy.EnergyStorage;
import abused_master.techutilities.utils.energy.IEnergyProvider;
import abused_master.techutilities.utils.energy.IEnergyReceiver;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class BlockEntityEnergyCrystal extends BlockEntityBase implements IEnergyReceiver, IEnergyProvider {

    public EnergyStorage storage = new EnergyStorage(100000);
    public final List<BlockPos> tilePositions = new ArrayList<>();
    public int sendPerTick = 250;

    public BlockEntityEnergyCrystal() {
        super(ModBlockEntities.ENERGY_CRYSTAL);
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

        return nbt;
    }

    @Override
    public void tick() {
        if(storage.getEnergyStored() >= sendPerTick && tilePositions.size() > 0) {
            sendEnergy();
        }
    }

    public void sendEnergy() {
        for (BlockPos blockPos : tilePositions) {
            if(blockPos == null || world.getBlockState(blockPos) == null || !(world.getBlockState(blockPos) instanceof IEnergyReceiver)) {
                tilePositions.remove(blockPos);
                return;
            }

            sendEnergy(world, blockPos, sendPerTick);
        }
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

    @Override
    public boolean sendEnergy(World world, BlockPos pos, int amount) {
        return storage.sendEnergy(world, pos, amount);
    }
}
