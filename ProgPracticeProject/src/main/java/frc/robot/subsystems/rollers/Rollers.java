package frc.robot.subsystems.rollers;

public class Rollers {

    private final RollersIO io;

    public Rollers(RollersIO io) {
        this.io = io;
    }

    public void updateInputs() {
        io.updateInputs();
    }

    public void stop() {
        io.stop();
    }
    public void setVoltage(double voltage) {
        io.setVoltage(voltage);
    }
}
