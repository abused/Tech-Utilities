package abused_master.techutilities.tiles.transport;

import abused_master.abusedlib.tiles.BlockEntityBase;
import abused_master.techutilities.registry.ModBlockEntities;
import nerdhub.cardinalenergy.api.IEnergyHandler;
import nerdhub.cardinalenergy.impl.EnergyStorage;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class BlockEntityEnergyCable extends BlockEntityBase implements IEnergyHandler {

    public EnergyStorage storage = new EnergyStorage(500);

    public BlockEntityEnergyCable() {
        super(ModBlockEntities.ENERGY_CABLE);
    }

    @Override
    public void fromTag(CompoundTag tag) {
        super.fromTag(tag);
        storage.readEnergyFromTag(tag);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        storage.writeEnergyToTag(tag);
        return tag;
    }

    @Override
    public void tick() {
        this.sendEnergy();
    }

    public void sendEnergy() {
        for (Direction direction : Direction.values()) {
            BlockPos offsetDir = pos.offset(direction);
            storage.sendEnergy(world, offsetDir, storage.getEnergyStored() / 6);
            this.updateEntity();
        }
    }

    @Override
    public EnergyStorage getEnergyStorage(Direction direction) {
        return storage;
    }

    @Override
    public boolean isEnergyProvider(Direction direction) {
        return true;
    }
}
