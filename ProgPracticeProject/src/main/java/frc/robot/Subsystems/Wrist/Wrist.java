package frc.robot.Subsystems.Wrist;

public class Wrist {
    
    public final WristIO io;

    public Wrist(WristIO io) {
        this.io = io;
    }

    public void setPosition(double position) {
        io.setPosition(position);
    }

    public void setVoltage(double voltage) {
        io.setVoltage(voltage);
    }

    public void stop() {
        io.stop();
    }

    public void periodic() {
        io.updateInputs();
    }
}
