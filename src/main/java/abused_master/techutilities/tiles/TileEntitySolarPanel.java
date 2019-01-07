package abused_master.techutilities.tiles;

import abused_master.techutilities.api.phase.EnergyStorage;
import abused_master.techutilities.blocks.generators.BlockSolarPanel;
import abused_master.techutilities.blocks.generators.EnumSolarPanelTypes;
import abused_master.techutilities.registry.ModTiles;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class TileEntitySolarPanel extends TileEntityEnergyBase {

    public EnergyStorage storage;
    public EnumSolarPanelTypes type;
    public int generationPerTick;

    public TileEntitySolarPanel() {
        super(ModTiles.SOLAR_PANEL);
    }

    public TileEntitySolarPanel(EnumSolarPanelTypes type) {
        super(ModTiles.SOLAR_PANEL);
        this.type = type;
        this.storage = new EnergyStorage(type.getEnergyStorage());
        this.generationPerTick = type.getGenerationPerTick();
    }

    @Override
    public void fromTag(CompoundTag nbt) {
        super.fromTag(nbt);
        this.type = EnumSolarPanelTypes.values()[nbt.getInt("type")];
        this.generationPerTick = nbt.getInt("generationPerTick");
        this.storage.readFromNBT(nbt);
    }

    @Override
    public CompoundTag toTag(CompoundTag nbt) {
        super.toTag(nbt);
        nbt.putInt("type", this.type.ordinal());
        nbt.putInt("generationPerTick", this.generationPerTick);
        this.storage.writeEnergyToNBT(nbt);
        return nbt;
    }

    @Override
    public EnergyStorage getEnergyStorage() {
        return storage;
    }

    @Override
    public boolean isEnergyReceiver() {
        return false;
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

    public void sendEnergy() {
        for (Direction direction : Direction.values()) {
            BlockPos offsetPos = pos.offset(direction);
            BlockEntity entity = world.getBlockEntity(offsetPos);

            if(entity != null && entity instanceof TileEntityEnergyBase) {
                TileEntityEnergyBase energyEntity = (TileEntityEnergyBase) entity;
                EnergyStorage energyStorage = energyEntity.getEnergyStorage();
                if((energyStorage.getEnergyStored() + generationPerTick) < energyStorage.getEnergyCapacity() && energyEntity.isEnergyReceiver()) {
                    energyStorage.recieveEnergy(storage.getEnergyStored() >= generationPerTick ? generationPerTick : storage.getEnergyStored());
                    storage.extractEnergy(storage.getEnergyStored() >= generationPerTick ? generationPerTick : storage.getEnergyStored());
                }
            }
        }
    }
}
