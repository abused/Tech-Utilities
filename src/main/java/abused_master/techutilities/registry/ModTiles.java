package abused_master.techutilities.registry;

import abused_master.abusedlib.registry.RegistryHelper;
import abused_master.techutilities.TechUtilities;
import abused_master.techutilities.tiles.TileEntityEnergyCrystal;
import abused_master.techutilities.tiles.TileEntityEnergyFurnace;
import abused_master.techutilities.tiles.TileEntityQuarry;
import abused_master.techutilities.tiles.TileEntitySolarPanel;
import net.minecraft.block.entity.BlockEntityType;

public class ModTiles {

    public static BlockEntityType<TileEntityEnergyFurnace> ENERGY_FURNACE;
    public static BlockEntityType<TileEntityQuarry> QUARRY;
    public static BlockEntityType<TileEntitySolarPanel> SOLAR_PANEL;
    public static BlockEntityType<TileEntityEnergyCrystal> ENERGY_CRYSTAL;

    public static void registerTile() {
        ENERGY_FURNACE = RegistryHelper.registerTile(TechUtilities.MODID, "energy_furnace", TileEntityEnergyFurnace.class);
        QUARRY = RegistryHelper.registerTile(TechUtilities.MODID, "quarry", TileEntityQuarry.class);
        SOLAR_PANEL = RegistryHelper.registerTile(TechUtilities.MODID, "solar_panel", TileEntitySolarPanel.class);
        ENERGY_CRYSTAL = RegistryHelper.registerTile(TechUtilities.MODID, "energy_crystal", TileEntityEnergyCrystal.class);
    }
}
