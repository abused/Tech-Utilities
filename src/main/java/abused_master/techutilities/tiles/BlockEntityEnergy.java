package abused_master.techutilities.tiles;

import abused_master.techutilities.energy.EnergyStorage;
import abused_master.techutilities.energy.IEnergyHandler;
import net.minecraft.block.entity.BlockEntityType;

public abstract class BlockEntityEnergy extends BlockEntityBase implements IEnergyHandler {

    public BlockEntityEnergy(BlockEntityType<?> blockEntityType_1) {
        super(blockEntityType_1);
    }

    public boolean handleEnergyReceive(EnergyStorage storage, int amount) {
        if(canReceive(storage, amount)) {
            storage.recieveEnergy(amount);
            this.markDirty();
            world.updateListeners(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
            return true;
        }

        return false;
    }

    public boolean canReceive(EnergyStorage storage, int amount) {
        return (storage.getEnergyCapacity() - storage.getEnergyStored()) >= amount;
    }
}
