package abused_master.techutilities.tiles.generator;

import abused_master.techutilities.blocks.generators.EnumSolarPanelTypes;
import abused_master.techutilities.registry.ModBlockEntities;
import abused_master.techutilities.tiles.BlockEntityEnergy;
import abused_master.techutilities.energy.EnergyStorage;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class BlockEntitySolarPanel extends BlockEntityEnergy {

    public EnergyStorage storage;
    public EnumSolarPanelTypes type;
    public int generationPerTick;

    public BlockEntitySolarPanel() {
        super(ModBlockEntities.SOLAR_PANEL);
    }

    @Override
    public void fromTag(CompoundTag nbt) {
        super.fromTag(nbt);
        this.setType(EnumSolarPanelTypes.values()[nbt.getInt("type")]);
        this.storage.readFromNBT(nbt);
    }

    @Override
    public CompoundTag toTag(CompoundTag nbt) {
        super.toTag(nbt);
        nbt.putInt("type", this.type.ordinal());
        this.storage.writeEnergyToNBT(nbt);
        return nbt;
    }

    @Override
    public void tick() {
        if(world.isDaylight()) {
            if((storage.getEnergyStored() + generationPerTick) < storage.getEnergyCapacity()) {
                storage.recieveEnergy(generationPerTick);
            }
        }

        sendEnergy();
    }

    public void setType(EnumSolarPanelTypes type) {
        this.type = type;
        if(storage != null && storage.getEnergyStored() > 0) {
            this.storage = new EnergyStorage(type.getEnergyStorage(), storage.getEnergyStored());
        }else {
            this.storage = new EnergyStorage(type.getEnergyStorage());
        }
        this.generationPerTick = type.getGenerationPerTick();
        markDirty();
    }

    public void sendEnergy() {
        for (Direction direction : Direction.values()) {
            BlockPos offsetPos = pos.offset(direction);
            storage.sendEnergy(world, offsetPos, generationPerTick);
            this.markDirty();
        }
    }

    @Override
    public EnergyStorage getEnergyStorage() {
        return storage;
    }
}
