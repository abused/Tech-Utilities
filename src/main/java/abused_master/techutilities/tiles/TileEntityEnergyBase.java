package abused_master.techutilities.tiles;

import abused_master.techutilities.api.phase.EnergyStorage;
import net.minecraft.block.entity.BlockEntityType;

public abstract class TileEntityEnergyBase extends TileEntityBase {

    public TileEntityEnergyBase(BlockEntityType<?> blockEntityType_1) {
        super(blockEntityType_1);
    }

    public abstract EnergyStorage getEnergyStorage();
    public abstract boolean isEnergyReceiver();
}
