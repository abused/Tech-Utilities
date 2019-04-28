package abused_master.techutilities.items;

import abused_master.abusedlib.items.ItemBase;
import abused_master.techutilities.TechUtilities;
import abused_master.techutilities.utils.IWrenchable;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemWrench extends ItemBase {

    public ItemWrench() {
        super("wrench", new Settings().itemGroup(TechUtilities.modItemGroup).stackSize(1));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState block = world.getBlockState(pos);

        if(block.getBlock() instanceof IWrenchable) {
            return ((IWrenchable) block.getBlock()).onWrenched(world, pos, block, context.getPlayer()) ? ActionResult.SUCCESS : ActionResult.PASS;
        }

        return super.useOnBlock(context);
    }
}
