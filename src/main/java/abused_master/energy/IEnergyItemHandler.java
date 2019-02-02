package abused_master.energy;

public interface IEnergyItemHandler extends IEnergyHandler {

    boolean receiveEnergy(int amount);
    boolean extractEnergy(int amount);
}
