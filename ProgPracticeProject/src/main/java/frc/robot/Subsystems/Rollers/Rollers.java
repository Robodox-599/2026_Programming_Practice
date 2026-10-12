package frc.robot.Subsystems.Rollers;

public class Rollers {
    public final RollersIO io;

    public Rollers(RollersIO io) {
        this.io = io;
    }

    public void setVelocity(double velocity) {
        io.setVelocity(velocity);
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
