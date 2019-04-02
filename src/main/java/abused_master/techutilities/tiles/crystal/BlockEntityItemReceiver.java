package abused_master.techutilities.tiles.crystal;

import abused_master.abusedlib.tiles.BlockEntityBase;
import abused_master.abusedlib.utils.InventoryHelper;
import abused_master.techutilities.registry.ModBlockEntities;
import abused_master.techutilities.utils.linker.ILinkerHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.StringTextComponent;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.TagHelper;

public class BlockEntityItemReceiver extends BlockEntityBase implements Inventory, ILinkerHandler {

    public DefaultedList<ItemStack> inventory = DefaultedList.create(9, ItemStack.EMPTY);

    public BlockEntityItemReceiver() {
        super(ModBlockEntities.ITEM_TRANSFER);
    }

    @Override
    public void fromTag(CompoundTag nbt) {
        super.fromTag(nbt);
        inventory = DefaultedList.create(9, ItemStack.EMPTY);
        Inventories.fromTag(nbt, this.inventory);
    }

    @Override
    public CompoundTag toTag(CompoundTag nbt) {
        super.toTag(nbt);
        Inventories.toTag(nbt, this.inventory);
        return nbt;
    }

    @Override
    public void tick() {
        if(!isInventoryEmpty()) {
            sendItems();
        }
    }

    public void sendItems() {
        Inventory nearbyInventory = InventoryHelper.getNearbyInventory(world, pos);
        if(nearbyInventory != null) {
            for (int i = 0; i < inventory.size() - 1; i++) {
                if (!inventory.get(i).isEmpty()) {
                    ItemStack copyStack = inventory.get(i);
                    copyStack.setAmount(1);
                    if (InventoryHelper.insertItemIfPossible(nearbyInventory, copyStack, false)) {
                        takeInvStack(i, 1);
                    }
                }
            }
        }
    }

    public boolean isInventoryEmpty() {
        for (int i = 0; i < inventory.size() - 1; i++) {
            if(!inventory.get(i).isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public boolean canReceive(ItemStack stack) {
        for (int i = 0; i < inventory.size() - 1; i++) {
            if(inventory.get(i).isEmpty()) {
                return true;
            }else if(!inventory.get(i).isEmpty() && inventory.get(i).getAmount() < 64 && inventory.get(i).getItem() == stack.getItem()) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int getInvSize() {
        return inventory.size();
    }

    @Override
    public boolean isInvEmpty() {
        return inventory.isEmpty();
    }

    @Override
    public ItemStack getInvStack(int i) {
        return inventory.get(i);
    }

    @Override
    public ItemStack takeInvStack(int i, int i1) {
        return inventory.set(i, new ItemStack(inventory.get(i).getItem(), inventory.get(i).getAmount() - 1));
    }

    @Override
    public ItemStack removeInvStack(int i) {
        return inventory.set(i, ItemStack.EMPTY);
    }

    @Override
    public void setInvStack(int i, ItemStack itemStack) {
        inventory.set(i, itemStack);
    }

    @Override
    public boolean canPlayerUseInv(PlayerEntity playerEntity) {
        return true;
    }

    @Override
    public void clear() {
        inventory.clear();
    }

    @Override
    public void link(PlayerEntity player, CompoundTag tag) {
        if(!world.isClient) {
            if (tag.containsKey("itemPos")) {
                BlockEntityItemTransfer itemTransfer = (BlockEntityItemTransfer) world.getBlockEntity(TagHelper.deserializeBlockPos(tag.getCompound("itemPos")));
                if (itemTransfer != null) {
                    itemTransfer.setReceiverPosition(pos);
                    this.markDirty();
                    world.updateListeners(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
                    player.addChatMessage(new StringTextComponent("Linked Item Transferer position!"), true);
                } else {
                    player.addChatMessage(new StringTextComponent("Invalid crystal position!"), true);
                }
            }
        }
    }
}
