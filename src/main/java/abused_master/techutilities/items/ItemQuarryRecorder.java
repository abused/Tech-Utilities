package abused_master.techutilities.items;

import abused_master.techutilities.TechUtilities;
import abused_master.techutilities.tiles.TileEntityQuarry;
import net.minecraft.client.item.TooltipOptions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.StringTextComponent;
import net.minecraft.text.TextComponent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class ItemQuarryRecorder extends Item {

    public ItemQuarryRecorder() {
        super(new Settings().itemGroup(TechUtilities.modItemGroup).stackSize(1));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext usageContext) {
        BlockPos pos = usageContext.getPos();
        World world = usageContext.getWorld();
        PlayerEntity player = usageContext.getPlayer();
        ItemStack stack = usageContext.getItemStack();

        CompoundTag tag = stack.getTag();
        if (!player.isSneaking()) {
            if (tag == null) {
                tag = new CompoundTag();
            }

            if (!tag.containsKey("coordinates1")) {
                tag.putIntArray("coordinates1", new int[]{pos.getX(), pos.getY(), pos.getZ()});
                if (!world.isClient) {
                    player.addChatMessage(new StringTextComponent("Set coordinates for the first corner"), true);
                }

            } else if (!tag.containsKey("coordinates2")) {
                tag.putIntArray("coordinates2", new int[]{pos.getX(), pos.getY(), pos.getZ()});
                if (!world.isClient) {
                    player.addChatMessage(new StringTextComponent("Set coordinates for the second corner"), true);
                }
            }

            stack.setTag(tag);
            return ActionResult.SUCCESS;
        }


        return ActionResult.SUCCESS;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if(player.isSneaking()) {
            clearTag(player.getStackInHand(hand));
            if(!world.isClient) {
                player.addChatMessage(new StringTextComponent("Cleared recorder settings"), true);
            }
        }

        return super.use(world, player, hand);
    }

    public void clearTag(ItemStack stack) {
        stack.setTag(null);
    }

    @Override
    public void buildTooltip(ItemStack itemStack, World world, List<TextComponent> list, TooltipOptions tooltipOptions) {
        CompoundTag tag = itemStack.getTag();
        if(tag != null) {
            if(tag.containsKey("coordinates1")) {
                int x = tag.getIntArray("coordinates1")[0], y = tag.getIntArray("coordinates1")[1], z = tag.getIntArray("coordinates1")[2];
                list.add(new StringTextComponent("First Corner, x: " + x + " y: " + y + " z: " + z));
            }

            if(tag.containsKey("coordinates2")) {
                int x1 = tag.getIntArray("coordinates2")[0], y1 = tag.getIntArray("coordinates2")[1], z1 = tag.getIntArray("coordinates2")[2];
                list.add(new StringTextComponent("Second Corner, x: " + x1 + " y: " + y1 + " z: " + z1));
            }
        }else {
            list.add(new StringTextComponent("Recorder not yet linked to any blocks."));
        }
    }
}
