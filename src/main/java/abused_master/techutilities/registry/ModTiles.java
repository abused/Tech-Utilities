package abused_master.techutilities.registry;

import abused_master.techutilities.TechUtilities;
import abused_master.techutilities.tiles.TileEntityEnergyCrystal;
import abused_master.techutilities.tiles.TileEntityEnergyFurnace;
import abused_master.techutilities.tiles.TileEntityQuarry;
import abused_master.techutilities.tiles.TileEntitySolarPanel;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModTiles {

    public static BlockEntityType<TileEntityEnergyFurnace> ENERGY_FURNACE;
    public static BlockEntityType<TileEntityQuarry> QUARRY;
    public static BlockEntityType<TileEntitySolarPanel> SOLAR_PANEL;
    public static BlockEntityType<TileEntityEnergyCrystal> ENERGY_CRYSTAL;

    public static void registerTile(Registry<BlockEntityType<?>> registry) {
        ENERGY_FURNACE = Registry.register(registry, new Identifier(TechUtilities.MODID, "energy_furnace"), BlockEntityType.Builder.create(TileEntityEnergyFurnace::new).method_11034(null));
        QUARRY = Registry.register(registry, new Identifier(TechUtilities.MODID, "quarry"), BlockEntityType.Builder.create(TileEntityQuarry::new).method_11034(null));
        SOLAR_PANEL = Registry.register(registry, new Identifier(TechUtilities.MODID, "solar_panel"), BlockEntityType.Builder.create(TileEntitySolarPanel::new).method_11034(null));
        ENERGY_CRYSTAL = Registry.register(registry, new Identifier(TechUtilities.MODID, "energy_crystal"), BlockEntityType.Builder.create(TileEntityEnergyCrystal::new).method_11034(null));
    }
}
