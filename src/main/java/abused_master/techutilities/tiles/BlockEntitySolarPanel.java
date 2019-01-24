package abused_master.techutilities.tiles;

import abused_master.techutilities.blocks.generators.EnumSolarPanelTypes;
import abused_master.techutilities.registry.ModBlockEntities;
import abused_master.techutilities.utils.energy.EnergyStorage;
import abused_master.techutilities.utils.energy.IEnergyHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class BlockEntitySolarPanel extends BlockEntityBase implements IEnergyHandler {

    public EnergyStorage storage;
    public EnumSolarPanelTypes type;
    public int generationPerTick;

    public BlockEntitySolarPanel() {
        super(ModBlockEntities.SOLAR_PANEL);
    }

    public BlockEntitySolarPanel(EnumSolarPanelTypes type) {
        super(ModBlockEntities.SOLAR_PANEL);
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
            storage.sendEnergy(world, offsetPos, generationPerTick);
        }
    }

    @Override
    public EnergyStorage getEnergyStorage() {
        return storage;
    }
}
