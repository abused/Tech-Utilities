package abused_master.techutilities.items;

import abused_master.abusedlib.items.ItemBase;
import abused_master.techutilities.TechUtilities;
import abused_master.techutilities.registry.ModItems;
import nerdhub.cardinalenergy.api.IEnergyItemHandler;
import nerdhub.cardinalenergy.impl.ItemEnergyStorage;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.StringTextComponent;
import net.minecraft.text.TextComponent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemSteelIngot extends ItemBase implements IEnergyItemHandler {

    public ItemEnergyStorage storage = new ItemEnergyStorage(500000);

    public ItemSteelIngot(String name) {
        super(name, TechUtilities.modItemGroup);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        ItemStack stack = playerEntity.getMainHandStack();
        if(playerEntity.isSneaking() && stack.getItem() == ModItems.STEEL_INGOT && storage.getEnergyStored(stack) == storage.getEnergyCapacity(stack)) {
            playerEntity.setStackInHand(hand, new ItemStack(ModItems.ENERGIZED_STEEL_INGOT));
        }

        return new TypedActionResult<>(ActionResult.SUCCESS, playerEntity.getMainHandStack());
    }

    @Override
    public boolean hasEnchantmentGlint(ItemStack stack) {
        return stack.getItem() == ModItems.ENERGIZED_STEEL_INGOT;
    }

    @Override
    public void buildTooltip(ItemStack stack, @Nullable World world, List<TextComponent> list, TooltipContext context) {
        if(stack.hasTag() && stack.getTag().containsKey(ItemEnergyStorage.ENERGY_TAG)) {
            list.add(new StringTextComponent("Energy Stored: " + storage.getEnergyStored(stack) + " / " + storage.getEnergyCapacity(stack) + " CE"));
        }
    }

    @Override
    public ItemEnergyStorage getEnergyStorage() {
        return this.storage;
    }
}
