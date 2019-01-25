package abused_master.techutilities.items;

import abused_master.techutilities.TechUtilities;
import abused_master.techutilities.tiles.crystal.BlockEntityAutoCrystal;
import abused_master.techutilities.tiles.crystal.BlockEntityEnergyCollector;
import abused_master.techutilities.tiles.crystal.BlockEntityEnergyCrystal;
import abused_master.techutilities.utils.energy.IEnergyReceiver;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipOptions;
import net.minecraft.entity.player.PlayerEntity;
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

public class ItemLinker extends ItemBase {

    public ItemLinker() {
        super("linker", new Settings().itemGroup(TechUtilities.modItemGroup).stackSize(1));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext usageContext) {
        BlockPos pos = usageContext.getPos();
        World world = usageContext.getWorld();
        PlayerEntity player = usageContext.getPlayer();
        ItemStack stack = usageContext.getItemStack();

        CompoundTag tag = stack.getTag();
        if (player.isSneaking()) {
            if (tag == null) {
                tag = new CompoundTag();
            }

            BlockEntity blockEntity = world.getBlockEntity(pos);
            if(blockEntity != null) {
                if (blockEntity instanceof IEnergyReceiver) {
                    if(blockEntity instanceof BlockEntityEnergyCrystal) {
                        //BlockEntityEnergyCrystal crystalBlockEntity = (BlockEntityEnergyCrystal) world.getBlockEntity(pos);
                        if(tag.containsKey("collectorPos")) {
                            BlockPos collectorPos = BlockPos.fromLong(tag.getLong("collectorPos"));
                            BlockEntityEnergyCollector energyCollector = (BlockEntityEnergyCollector) world.getBlockEntity(collectorPos);
                            if(energyCollector != null) {
                                energyCollector.setCrystalPos(pos);
                                if(!world.isClient) {
                                    player.addChatMessage(new StringTextComponent("Linked collector position!"), true);
                                }
                            }else {
                                if(!world.isClient) {
                                    player.addChatMessage(new StringTextComponent("Invalid collector position!"), true);
                                }
                            }
                        }else if(tag.containsKey("blockPos")) {
                            BlockPos blockPos = BlockPos.fromLong(tag.getLong("blockPos"));
                            if(world.getBlockEntity(blockPos) != null && world.getBlockEntity(blockPos) instanceof IEnergyReceiver && !((BlockEntityEnergyCrystal) world.getBlockEntity(pos)).tilePositions.contains(blockPos)) {
                                ((BlockEntityEnergyCrystal) world.getBlockEntity(pos)).tilePositions.add(blockPos);
                                if(!world.isClient) {
                                    System.out.println(((BlockEntityEnergyCrystal) world.getBlockEntity(pos)).tilePositions.size());
                                    player.addChatMessage(new StringTextComponent("Linked BlockEntity position!"), true);
                                }
                            }
                        }else {
                            if(!world.isClient) {
                                player.addChatMessage(new StringTextComponent("No block position has been linked!"), true);
                            }
                        }
                    }else {
                        if(blockEntity instanceof BlockEntityEnergyCollector) {
                            if(tag.containsKey("blockPos")) {
                                tag.remove("blockPos");
                            }
                            tag.putLong("collectorPos", pos.asLong());
                            if(!world.isClient) {
                                player.addChatMessage(new StringTextComponent("Saved collector position!"), true);
                            }
                        }else {
                            if(tag.containsKey("collectorPos")) {
                                tag.remove("collectorPos");
                            }
                            tag.putLong("blockPos", pos.asLong());
                            if(!world.isClient) {
                                player.addChatMessage(new StringTextComponent("Saved block position!"), true);
                            }
                        }
                    }
                }else {
                    if(!world.isClient) {
                        player.addChatMessage(new StringTextComponent("The selected entity cannot be linked!"), true);
                    }
                }
            }else {
                if(!world.isClient) {
                    player.addChatMessage(new StringTextComponent("The selected block is not a BlockEntity!"), true);
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
                player.addChatMessage(new StringTextComponent("Cleared linker settings"), true);
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
            if(tag.containsKey("collectorPos")) {
                BlockPos pos = BlockPos.fromLong(tag.getLong("collectorPos"));
                list.add(new StringTextComponent("Collector Pos, x: " + pos.getX() + " y: " + pos.getY() + " z: " + pos.getZ()));
            }else if(tag.containsKey("blockPos")) {
                BlockPos pos = BlockPos.fromLong(tag.getLong("blockPos"));
                list.add(new StringTextComponent("BlockEntity Pos, x: " + pos.getX() + " y: " + pos.getY() + " z: " + pos.getZ()));
            }
        }else {
            list.add(new StringTextComponent("No block positions have been saved."));
        }
    }
}
