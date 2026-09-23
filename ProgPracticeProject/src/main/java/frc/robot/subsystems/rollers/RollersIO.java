package frc.robot.subsystems.rollers;

abstract public class RollersIO {
    protected final double radPerSecVelocity = 0.0;
    protected final double temperature = 0.0;
    protected final double supplyCurrent = 0.0;
    protected final double voltage = 0.0;
    protected final double statorCurrent = 0.0;

    public void stop() {}
    public void updatedInputs() {}
    public void setVoltage(double voltage) {}  


}
