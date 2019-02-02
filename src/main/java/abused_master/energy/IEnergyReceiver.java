package abused_master.energy;

public interface IEnergyReceiver extends IEnergyHandler {

    boolean receiveEnergy(int amount);
}
