package abused_master.techutilities;

import abused_master.abusedlib.utils.Config;
import abused_master.techutilities.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;

public class TechUtilities implements ModInitializer {

    public static final String RESOURCES_TAG = "fabric";
    public static String MODID = "techutilities";
    public static ItemGroup modItemGroup = FabricItemGroupBuilder.build(new Identifier(MODID, "techutilities"), () -> new ItemStack(ModBlocks.ENERGY_FURNACE));

    public static Config config = new Config(MODID, TechUtilities.class);

    @Override
    public void onInitialize() {
        ModBlocks.registerBlocks();
        ModItems.registerItems();
        WorldGenRegistry.generateOres();
        ModBlockEntities.registerBlockEntities();
        ModBlockEntities.registerServerGUIs();
        ModRecipes.registerRecipes();

        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if(player.getMainHandStack().getItem() == ModItems.WRENCH) {
                player.getMainHandStack().getItem().useOnBlock(new ItemUsageContext(player, hand, hitResult));
                return ActionResult.SUCCESS;
            }

            return ActionResult.PASS;
        });
    }
}
