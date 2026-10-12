package frc.robot.Subsystems.Rollers;

public abstract class RollersIO {
    protected double temperature = 0.0;
    protected double voltage = 0.0;
    protected double supplyCurrent = 0.0;
    protected double statorCurrent = 0.0;
    protected double velocity = 0.0;
    public void setVoltage(double voltage) {}
    public void setVelocity(double velocity) {}
    public void stop() {}
    public void updateInputs() {}
}
