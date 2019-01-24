package abused_master.techutilities.items;

import abused_master.techutilities.TechUtilities;
import abused_master.techutilities.utils.energy.IEnergyHandler;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.StringTextComponent;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

public class ItemTest extends ItemBase {

    public ItemTest() {
        super("test", new Settings().itemGroup(TechUtilities.modItemGroup).stackSize(1));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext usageContext) {
        World world = usageContext.getWorld();
        if(world.getBlockEntity(usageContext.getPos()) != null && world.getBlockEntity(usageContext.getPos()) instanceof IEnergyHandler) {
            if(!world.isClient) {
                usageContext.getPlayer().addChatMessage(new StringTextComponent("Power: " + ((IEnergyHandler) world.getBlockEntity(usageContext.getPos())).getEnergyStorage().getEnergyStored() + " / " + ((IEnergyHandler) world.getBlockEntity(usageContext.getPos())).getEnergyStorage().getEnergyCapacity()), false);
            }
        }
        return ActionResult.SUCCESS;
    }
}
