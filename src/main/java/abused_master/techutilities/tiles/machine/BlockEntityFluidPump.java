package abused_master.techutilities.tiles.machine;

import abused_master.abusedlib.client.render.hud.IHudSupport;
import abused_master.abusedlib.fluid.FluidStack;
import abused_master.abusedlib.fluid.FluidContainer;
import abused_master.abusedlib.fluid.IFluidHandler;
import abused_master.abusedlib.tiles.BlockEntityBase;
import abused_master.techutilities.TechUtilities;
import abused_master.techutilities.registry.ModBlockEntities;
import abused_master.techutilities.utils.linker.ILinkerHandler;
import nerdhub.cardinalenergy.api.IEnergyHandler;
import nerdhub.cardinalenergy.impl.EnergyStorage;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.text.StringTextComponent;
import net.minecraft.util.TagHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BlockEntityFluidPump extends BlockEntityBase implements IEnergyHandler, IFluidHandler, IHudSupport, ILinkerHandler {

    public EnergyStorage storage = new EnergyStorage(50000);
    public FluidContainer tank = new FluidContainer(32000);
    public List<BlockPos> cachedDrainingPos = new ArrayList<>();
    public int pumpRage = TechUtilities.config.getInt("pumpRange");
    public BlockPos drainingPos = null;
    public int drainPerBlock = 250;
    public int drainingSpeed = 0;

    public BlockEntityFluidPump() {
        super(ModBlockEntities.FLUID_PUMP);
    }

    @Override
    public void fromTag(CompoundTag tag) {
        super.fromTag(tag);
        storage.readEnergyFromTag(tag);

        if(this.tank != null) {
            this.tank.setBlockEntity(this);
            if(this.tank.getFluidStack() != null) {
                this.tank.readFromNBT(tag);
            }

            if (tag.containsKey("FluidData")) {
                this.tank.setFluidStack(FluidStack.fluidFromTag(tag.getCompound("FluidData")));
            }
        }

        if(tag.containsKey("cachedDrainingPos")) {
            this.cachedDrainingPos.clear();
            ListTag listTag = tag.getList("cachedDrainingPos", NbtType.COMPOUND);

            for (Iterator<Tag> it = listTag.iterator(); it.hasNext(); ) {
                CompoundTag compoundTag = (CompoundTag) it.next();
                cachedDrainingPos.add(TagHelper.deserializeBlockPos(compoundTag));
            }
        }

        if(tag.containsKey("drainingPos")) {
            this.drainingPos = TagHelper.deserializeBlockPos(tag.getCompound("drainingPos"));
        }

        this.drainingSpeed = tag.getInt("drainingSpeed");
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        storage.writeEnergyToTag(tag);

        if (this.tank != null && this.tank.getFluidStack() != null) {
            CompoundTag tankTag = new CompoundTag();
            this.tank.getFluidStack().toTag(tankTag);
            tag.put("FluidData", tankTag);
            this.tank.writeToNBT(tag);
        }

        if(!this.cachedDrainingPos.isEmpty()) {
            ListTag listTag = new ListTag();

            for (BlockPos areaPos : cachedDrainingPos) {
                listTag.add(TagHelper.serializeBlockPos(areaPos));
            }

            tag.put("cachedDrainingPos", listTag);
        }


        if(drainingPos != null) {
            tag.put("drainingPos", TagHelper.serializeBlockPos(drainingPos));
        }

        tag.putInt("drainingSpeed", this.drainingSpeed);

        return tag;
    }

    @Override
    public void tick() {
        if (!world.isReceivingRedstonePower(pos) && canRun()) {
            drainingSpeed++;
            if (drainingSpeed >= 20) {
                run();
                this.updateEntity();
            }
        }

        sendFluid();
    }

    public boolean canRun() {
        if(storage.getEnergyStored() < drainPerBlock || (tank.getFluidCapacity() - tank.getFluidAmount()) < 1000) {
            return false;
        }

        return true;
    }

    public void run() {
        if(!cachedDrainingPos.isEmpty()) {
            BlockPos pos = null;

            for (Iterator<BlockPos> it = cachedDrainingPos.iterator(); it.hasNext(); ) {
                BlockPos pos2 = it.next();
                if (world.isAir(pos2) || world.getFluidState(pos2) == null || !(world.getBlockState(pos2).getBlock() instanceof FluidBlock) || world.isHeightInvalid(pos)) {
                    it.remove();
                    continue;
                }

                pos = pos2;
                drainingPos = pos2;
                break;
            }

            if (tank.getFluidStack() == null) {
                tank.setFluidStack(new FluidStack(world.getFluidState(pos).getFluid(), 1000));
                world.setBlockState(pos, Blocks.STONE.getDefaultState());
                drainingSpeed = 0;
                storage.extractEnergy(drainPerBlock);
            } else if (tank.getFluidStack().getFluid().getDefaultState().getBlockState() == world.getFluidState(pos).getBlockState()) {
                tank.fillFluid(new FluidStack(world.getFluidState(pos).getFluid(), 1000));
                world.setBlockState(pos, Blocks.STONE.getDefaultState());
                drainingSpeed = 0;
                storage.extractEnergy(drainPerBlock);
            }

            cachedDrainingPos.remove(pos);
        }
    }

    public void sendFluid() {
        if(tank.getFluidAmount() >= 250) {
            for (Direction direction : Direction.values()) {
                BlockPos offsetPos = pos.offset(direction);
                if (world.getBlockEntity(offsetPos) instanceof IFluidHandler) {
                    IFluidHandler fluidHandler = (IFluidHandler) world.getBlockEntity(offsetPos);
                    if((fluidHandler.getFluidTank().getFluidAmount() + 250) <= fluidHandler.getFluidTank().getFluidCapacity()) {
                        if(fluidHandler.getFluidTank().getFluidStack() != null && fluidHandler.getFluidTank().getFluidStack().getFluid() == tank.getFluidStack().getFluid()) {
                            fluidHandler.getFluidTank().fillFluid(new FluidStack(tank.getFluidStack().getFluid(), 250));
                            tank.extractFluid(250);
                        }else if(fluidHandler.getFluidTank().getFluidStack() == null) {
                            fluidHandler.getFluidTank().fillFluid(new FluidStack(tank.getFluidStack().getFluid(), 250));
                            tank.extractFluid(250);
                        }

                        this.updateEntity();
                    }
                }
            }
        }
    }

    public void cacheDrainingArea() {
        Iterable<BlockPos> drainArea = BlockPos.iterateBoxPositions(new BlockPos(pos.getX() - pumpRage, pos.getY() - pumpRage, pos.getZ() - pumpRage), new BlockPos(pos.getX() + pumpRage, pos.getY() + pumpRage, pos.getZ() + pumpRage));
        this.cachedDrainingPos = BlockEntityQuarry.listBlocksInIterable(drainArea);
        this.updateEntity();
    }

    @Override
    public EnergyStorage getEnergyStorage(Direction direction) {
        return storage;
    }

    @Override
    public FluidContainer getFluidTank() {
        return tank;
    }

    @Override
    public Direction getBlockOrientation() {
        return null;
    }

    @Override
    public boolean isBlockAboveAir() {
        return getWorld().isAir(pos.up());
    }

    @Override
    public BlockPos getBlockPos() {
        return getPos();
    }

    @Override
    public List<String> getClientLog() {
        List<String> toDisplay = new ArrayList<>();
        if(tank.getFluidStack() != null) {
            toDisplay.add(I18n.translate(tank.getFluidStack().getFluid().getDefaultState().getBlockState().getBlock().getTranslationKey()) + ": " + tank.getFluidAmount() + " / " + tank.getFluidCapacity());
        }else {
            toDisplay.add("Internal Tank Empty");
        }
        toDisplay.add("Energy: " + storage.getEnergyStored() + " / " + storage.getEnergyCapacity() + " CE");
        if(drainingPos != null) {
            toDisplay.add("Draining: X: " + drainingPos.getX() + " Y: " + drainingPos.getY() + " Z: " + drainingPos.getZ());
        }else {
            toDisplay.add("Not currently working");
        }
        return toDisplay;
    }

    @Override
    public void link(PlayerEntity player, CompoundTag tag) {
        if(!world.isClient) {
            if (tag.containsKey("collectorPos")) {
                tag.remove("collectorPos");
            }

            tag.put("blockPos", TagHelper.serializeBlockPos(pos));
            player.addChatMessage(new StringTextComponent("Saved block position!"), true);
        }
    }
}
