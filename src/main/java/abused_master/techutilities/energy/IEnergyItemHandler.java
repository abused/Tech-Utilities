package abused_master.techutilities.energy;

public interface IEnergyItemHandler extends IEnergyHandler {

    boolean receiveEnergy(int amount);
    boolean extractEnergy(int amount);
}
