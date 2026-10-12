package frc.robot.Subsystems.Wrist;

public class WristIO {
    protected double temperature = 0.0;
    protected double voltage = 0.0;
    protected double supplyCurrent = 0.0;
    protected double statorCurrent = 0.0;
    protected double position = 0.0;
    protected double targetPosition = 0.0;

    public void setVoltage(double voltage) {}
    public void setPosition(double position) {}
    public void stop() {}
    public void updateInputs() {}
    
}
