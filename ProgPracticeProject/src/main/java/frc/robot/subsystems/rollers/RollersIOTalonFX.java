package frc.robot.subsystems.rollers;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;

public class RollersIOTalonFX extends RollersIO {
    private final CANBus CANbus;
    private final TalonFX motor;
    private final TalonFXConfiguration con;    
    private final VoltageOut volt;

    temperature<StatusSignal temperature>;
    radPerSecVelocity <StatusSignal>;
    supplyCurrent <StatusSignal>;
    statorCurrent <StatusSignal>;
    voltage <StatusSignal>;

    public RollersIOTalonFX() {
        motor = new TalonFX(RollersConstants.ID);
        CANbus = new CANBus(RollersConstants.CANbus);
        con = new TalonFXConfiguration().withCurrentLimits(new CurrentLimitsConfigs()
        .withStatorCurrentLimit(RollersConstants.statorCurrent)
        .withSupplyCurrentLimit(RollersConstants.supplyCurrent));
        volt = new VoltageOut(0);

    }

    @Override
    public void stop() {
        setVoltage(0);
    }

    @Override
    public void updatedInputs() {

    }

    @Override
    public void setVoltage(double voltage) {
        setVoltage(voltage);
    }
}

