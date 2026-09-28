package frc.robot.subsystems.wrist;

public abstract class WristIO {
    protected double targetPosition = 0.0;
    protected double position = 0.0;
    protected double supplyCurrent = 0.0;
    protected double statorCurrent = 0.0;
    protected double voltage = 0.0;
    protected double temperature = 0.0;
    
    public void updateInputs() {}
    public void stop() {}
    public void setVoltage(double voltage) {}
    public void setPosition() {}
}
