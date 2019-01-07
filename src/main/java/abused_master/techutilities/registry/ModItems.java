package abused_master.techutilities.registry;

import abused_master.techutilities.TechUtilities;
import abused_master.techutilities.blocks.BlockResources;
import abused_master.techutilities.blocks.generators.EnumSolarPanelTypes;
import abused_master.techutilities.items.EnumResourceItems;
import abused_master.techutilities.items.ItemQuarryRecorder;
import net.minecraft.item.Item;
import net.minecraft.item.block.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {

    public static ItemQuarryRecorder recorder = new ItemQuarryRecorder();

    public static void registerItems(Registry<Item> registry) {
        Registry.register(registry, new Identifier(TechUtilities.MODID, "energy_furnace"), new BlockItem(ModBlocks.RF_FURNACE, new Item.Settings().itemGroup(TechUtilities.modItemGroup)));
        Registry.register(registry, new Identifier(TechUtilities.MODID, "quarry"), new BlockItem(ModBlocks.QUARRY, new Item.Settings().itemGroup(TechUtilities.modItemGroup)));
        Registry.register(registry, new Identifier(TechUtilities.MODID, "glass_block"), new BlockItem(ModBlocks.GLASS_BLOCK, new Item.Settings().itemGroup(TechUtilities.modItemGroup)));
        Registry.register(registry, new Identifier(TechUtilities.MODID, "black_glass_block"), new BlockItem(ModBlocks.BLACK_GLASS_BLOCK, new Item.Settings().itemGroup(TechUtilities.modItemGroup)));
        Registry.register(registry, new Identifier(TechUtilities.MODID, "energy_crystal"), new BlockItem(ModBlocks.ENERGY_CRYSTAL, new Item.Settings().itemGroup(TechUtilities.modItemGroup)));

        Registry.register(registry, new Identifier(TechUtilities.MODID, "quarry_recorder"), recorder);

        for (EnumSolarPanelTypes panel : EnumSolarPanelTypes.values()) {
            Registry.register(registry, panel.getIdentifier(), new BlockItem(panel.getBlockSolar(), new Item.Settings().itemGroup(TechUtilities.modItemGroup)));
        }

        for (BlockResources.EnumResourceOres ore : BlockResources.EnumResourceOres.values()) {
            Registry.register(registry, ore.getOresIdentifier(), new BlockItem(ore.getBlockOres(), new Item.Settings().itemGroup(TechUtilities.modItemGroup)));
        }

        for (BlockResources.EnumResourceBlocks ore : BlockResources.EnumResourceBlocks.values()) {
            Registry.register(registry, ore.getOresIdentifier(), new BlockItem(ore.getBlockOres(), new Item.Settings().itemGroup(TechUtilities.modItemGroup)));
        }

        for (EnumResourceItems item : EnumResourceItems.values()) {
            Registry.register(registry, item.getIngotIdentifier(), item.getItemIngot());
        }
    }
}
