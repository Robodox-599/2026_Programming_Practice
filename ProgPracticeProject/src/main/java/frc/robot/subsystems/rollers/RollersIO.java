package frc.robot.subsystems.rollers;

public abstract class RollersIO {
    
    protected double rollersVelocityRadPerSec = 0.0;
    protected double rollersVoltage = 0.0;
    protected double rollersStatorCurrent = 0.0;
    protected double rollersSupplyCurrent = 0.0;
    protected double rollersTemperature = 0.0;

    public void updateInputs() {}
    public void setVoltage(double voltage) {}
    public void stop() {}
}

