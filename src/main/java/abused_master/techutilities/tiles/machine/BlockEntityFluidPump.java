package abused_master.techutilities.tiles.machine;

import abused_master.techutilities.TechUtilities;
import abused_master.techutilities.registry.ModBlockEntities;
import abused_master.techutilities.tiles.BlockEntityEnergy;
import abused_master.techutilities.energy.EnergyStorage;
import abused_master.techutilities.energy.IEnergyReceiver;
import abused_master.techutilities.utils.fluid.FluidStack;
import abused_master.techutilities.utils.fluid.FluidTank;
import abused_master.techutilities.utils.fluid.IFluidHandler;
import abused_master.techutilities.utils.linker.ILinkerHandler;
import abused_master.techutilities.utils.render.hud.IHudSupport;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.StringTextComponent;
import net.minecraft.util.TagHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.ArrayList;
import java.util.List;

public class BlockEntityFluidPump extends BlockEntityEnergy implements IEnergyReceiver, IFluidHandler, IHudSupport, ILinkerHandler {

    public EnergyStorage storage = new EnergyStorage(50000);
    public FluidTank tank = new FluidTank(32000);
    public int pumpRage = TechUtilities.config.getInt("pumpRange");
    public BlockPos drainingPos = null;
    public int drainPerBlock = 250;
    public int drainingSpeed = 0;

    public BlockEntityFluidPump() {
        super(ModBlockEntities.FLUID_PUMP);
    }

    @Override
    public void fromTag(CompoundTag nbt) {
        super.fromTag(nbt);
        storage.readFromNBT(nbt);

        if(this.tank != null) {
            this.tank.setBlockEntity(this);
            if(this.tank.getFluidStack() != null) {
                this.tank.readFromNBT(nbt);
            }

            if (nbt.containsKey("FluidData")) {
                this.tank.setFluidStack(FluidStack.fluidFromTag(nbt.getCompound("FluidData")));
            }
        }

        if(nbt.containsKey("drainingPos")) {
            this.drainingPos = TagHelper.deserializeBlockPos(nbt.getCompound("drainingPos"));
        }

        this.drainingSpeed = nbt.getInt("drainingSpeed");
    }

    @Override
    public CompoundTag toTag(CompoundTag nbt) {
        super.toTag(nbt);
        storage.writeEnergyToNBT(nbt);

        if (this.tank != null && this.tank.getFluidStack() != null) {
            CompoundTag tankTag = new CompoundTag();
            this.tank.getFluidStack().toTag(tankTag);
            nbt.put("FluidData", tankTag);
            this.tank.writeToNBT(nbt);
        }

        if(drainingPos != null) {
            nbt.put("drainingPos", TagHelper.serializeBlockPos(drainingPos));
        }

        nbt.putInt("drainingSpeed", this.drainingSpeed);

        return nbt;
    }

    @Override
    public void tick() {
        if(!world.isReceivingRedstonePower(pos)) {
            if (canRun()) {
                drainingSpeed++;
                if (drainingSpeed >= 20) {
                    run();
                }
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
        Iterable<BlockPos> drainArea = BlockPos.iterateBoxPositions(new BlockPos(pos.getX() - pumpRage, pos.getY() - pumpRage, pos.getZ() - pumpRage), new BlockPos(pos.getX() + pumpRage, pos.getY() + pumpRage, pos.getZ() + pumpRage));
        for (BlockPos pos : drainArea) {
            if(world.isAir(pos) || world.getFluidState(pos) == null || !(world.getBlockState(pos).getBlock() instanceof FluidBlock)) {
                continue;
            }

            if(drainingSpeed > 20) {
                if (tank.getFluidStack() == null) {
                    tank.setFluidStack(new FluidStack(world.getFluidState(pos).getFluid(), 1000));
                    world.setBlockState(pos, Blocks.STONE.getDefaultState());
                    drainingSpeed = 0;
                    drainingPos = pos;
                    storage.extractEnergy(drainPerBlock);
                } else if (tank.getFluidStack().getFluid().getDefaultState().getBlockState() == world.getFluidState(pos).getBlockState()) {
                    tank.fillFluid(new FluidStack(world.getFluidState(pos).getFluid(), 1000));
                    world.setBlockState(pos, Blocks.STONE.getDefaultState());
                    drainingSpeed = 0;
                    drainingPos = pos;
                    storage.extractEnergy(drainPerBlock);
                } else {
                    continue;
                }
            }
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

                        this.markDirty();
                    }
                }
            }
        }
    }

    @Override
    public EnergyStorage getEnergyStorage() {
        return storage;
    }

    @Override
    public boolean receiveEnergy(int amount) {
        return handleEnergyReceive(storage, amount);
    }

    @Override
    public FluidTank getFluidTank() {
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
        toDisplay.add("Energy: " + storage.getEnergyStored() + " / " + storage.getEnergyCapacity() + " PE");
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
