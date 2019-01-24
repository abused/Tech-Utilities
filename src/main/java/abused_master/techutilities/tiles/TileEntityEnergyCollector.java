package abused_master.techutilities.tiles;

import abused_master.techutilities.registry.ModTiles;
import abused_master.techutilities.utils.energy.EnergyStorage;
import abused_master.techutilities.utils.energy.IEnergyReceiver;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;

public class TileEntityEnergyCollector extends TileEntityBase implements IEnergyReceiver {

    public EnergyStorage storage = new EnergyStorage(10000);
    private BlockPos crystalPos;
    private int sendPerTick = 500;

    public TileEntityEnergyCollector() {
        super(ModTiles.ENERGY_CRYSTAL_COLLECTOR);
    }

    @Override
    public void fromTag(CompoundTag nbt) {
        super.fromTag(nbt);
        this.storage.readFromNBT(nbt);
        if(nbt.containsKey("crystalPos")) {
            this.crystalPos = BlockPos.fromLong(nbt.getLong("crystalPos"));
        }
    }

    @Override
    public CompoundTag toTag(CompoundTag nbt) {
        super.toTag(nbt);
        storage.writeEnergyToNBT(nbt);
        if(crystalPos != null) {
            nbt.putLong("crystalPos", crystalPos.asLong());
        }
        return nbt;
    }

    @Override
    public void tick() {
        if(crystalPos != null && world.getBlockEntity(crystalPos) != null && world.getBlockEntity(crystalPos) instanceof TileEntityEnergyCrystal && storage.getEnergyStored() >= sendPerTick) {
            TileEntityEnergyCrystal energyCrystal = (TileEntityEnergyCrystal) world.getBlockEntity(crystalPos);
            if(energyCrystal.receiveEnergy(sendPerTick)) {
                storage.extractEnergy(sendPerTick);
            }
        }
    }

    public void setCrystalPos(BlockPos crystalPos) {
        this.crystalPos = crystalPos;
    }

    public BlockPos getCrystalPos() {
        return crystalPos;
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
