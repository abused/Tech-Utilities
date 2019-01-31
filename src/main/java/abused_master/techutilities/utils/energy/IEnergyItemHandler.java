package abused_master.techutilities.utils.energy;

public interface IEnergyItemHandler extends IEnergyHandler {

    boolean receiveEnergy(int amount);
    boolean extractEnergy(int amount);
}
