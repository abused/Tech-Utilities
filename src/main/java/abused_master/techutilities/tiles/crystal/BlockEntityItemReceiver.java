package abused_master.techutilities.tiles.crystal;

import abused_master.techutilities.registry.ModBlockEntities;
import abused_master.techutilities.tiles.BlockEntityBase;
import abused_master.techutilities.utils.InventoryHelper;
import abused_master.techutilities.utils.linker.ILinkerHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.StringTextComponent;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.InventoryUtil;
import net.minecraft.util.TagHelper;
import net.minecraft.util.math.BlockPos;

public class BlockEntityItemReceiver extends BlockEntityBase implements Inventory, ILinkerHandler {

    public DefaultedList<ItemStack> inventory = DefaultedList.create(9, ItemStack.EMPTY);

    public BlockEntityItemReceiver() {
        super(ModBlockEntities.ITEM_TRANSFER);
    }

    @Override
    public void fromTag(CompoundTag nbt) {
        super.fromTag(nbt);
        inventory = DefaultedList.create(9, ItemStack.EMPTY);
        InventoryUtil.deserialize(nbt, this.inventory);
    }

    @Override
    public CompoundTag toTag(CompoundTag nbt) {
        super.toTag(nbt);
        InventoryUtil.serialize(nbt, this.inventory);
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
        for (int i = 0; i < inventory.size() - 1; i++) {
            if(!inventory.get(i).isEmpty()) {
                if(InventoryHelper.insertItemIfPossible(nearbyInventory, new ItemStack(inventory.get(i).getItem(), 1), false)) {
                    inventory.get(i).subtractAmount(1);
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
        inventory.get(i).subtractAmount(i1);
        return inventory.get(i);
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
    public void clearInv() {
        inventory.clear();
    }

    @Override
    public void link(PlayerEntity player, CompoundTag tag) {
        if (tag.containsKey("itemPos")) {
            BlockPos transferPos = TagHelper.deserializeBlockPos(tag.getCompound("itemPos"));
            BlockEntityItemTransfer itemTransfer = (BlockEntityItemTransfer) world.getBlockEntity(transferPos);
            if(itemTransfer != null) {
                itemTransfer.setPos(pos);
                this.markDirty();
                world.updateListeners(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
                player.addChatMessage(new StringTextComponent("Linked Item Transferer position!"), true);
            }else {
                player.addChatMessage(new StringTextComponent("Invalid crystal position!"), true);
            }
        }
    }
}
