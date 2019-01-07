package abused_master.techutilities.blocks.generators;

import abused_master.techutilities.TechUtilities;
import net.minecraft.util.Identifier;

public enum EnumSolarPanelTypes {
    SOLAR_PANEL_MK1(16, 10000),
    SOLAR_PANEL_MK2(256, 100000),
    SOLAR_PANEL_MK3(1024, 1000000);

    private Identifier identifier;
    private BlockSolarPanel solarPanel;
    private int generationPerTick, energyStorage;

    EnumSolarPanelTypes(int energyPerTick, int energyStorage) {
        this.identifier = new Identifier(TechUtilities.MODID, getName());
        this.solarPanel = new BlockSolarPanel(this);
        this.generationPerTick = energyPerTick;
        this.energyStorage = energyStorage;
    }

    public String getName() {
        return this.toString().toLowerCase();
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public BlockSolarPanel getBlockSolar() {
        return solarPanel;
    }

    public int getGenerationPerTick() {
        return generationPerTick;
    }

    public int getEnergyStorage() {
        return energyStorage;
    }
}
