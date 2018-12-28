package abused_master.techutilities.registry;

import abused_master.techutilities.TechUtilities;
import abused_master.techutilities.blocks.BlockResources;
import abused_master.techutilities.items.EnumResourceItems;
import net.minecraft.item.Item;
import net.minecraft.item.block.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {

    public static void registerItems(Registry<Item> registry) {
        Registry.register(registry, new Identifier(TechUtilities.MODID, "energy_furnace"), new BlockItem(ModBlocks.RF_FURNACE, new Item.Settings().itemGroup(TechUtilities.modItemGroup)));
        Registry.register(registry, new Identifier(TechUtilities.MODID, "quarry"), new BlockItem(ModBlocks.QUARRY, new Item.Settings().itemGroup(TechUtilities.modItemGroup)));
        Registry.register(registry, new Identifier(TechUtilities.MODID, "quarry_marker"), new BlockItem(ModBlocks.QUARRY_MARKER, new Item.Settings().itemGroup(TechUtilities.modItemGroup)));

        for (BlockResources.EnumResourceOres ore : BlockResources.EnumResourceOres.values()) {
            Registry.register(registry, ore.getOresIdentifier(), new BlockItem(ore.getBlockOres(), new Item.Settings().itemGroup(TechUtilities.modItemGroup)));
        }

        for (BlockResources.EnumResoueceBlocks ore : BlockResources.EnumResoueceBlocks.values()) {
            Registry.register(registry, ore.getOresIdentifier(), new BlockItem(ore.getBlockOres(), new Item.Settings().itemGroup(TechUtilities.modItemGroup)));
        }

        for (EnumResourceItems item : EnumResourceItems.values()) {
            Registry.register(registry, item.getIngotIdentifier(), item.getItemIngot());
        }
    }
}
