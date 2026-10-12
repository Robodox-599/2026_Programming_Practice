package frc.robot.Subsystems.Wrist;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;

import dev.doglog.DogLog;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Temperature;
import edu.wpi.first.units.measure.Voltage;

public class WristIOTalonFX extends WristIO {
    public final TalonFX motor;
    public final TalonFXConfiguration config;
    public final CANBus canBus;
    public final VoltageOut voltageOut;
    public final VelocityVoltage velocityVoltage;
    private final MotionMagicVoltage m_request = new MotionMagicVoltage(0.0)
    .withSlot(0).withEnableFOC(false);

    public StatusSignal<Temperature> temperatureSignal;
    public StatusSignal<Voltage> voltageSignal;
    public StatusSignal<Current> supplyCurrentSignal;
    public StatusSignal<Current> statorCurrentSignal;
    public StatusSignal<Angle> positionSignal;

    public WristIOTalonFX() {
        
        canBus = new CANBus(WristConstants.canBus);
        voltageOut = new VoltageOut(0.0);
        config = new TalonFXConfiguration();
        motor = new TalonFX(WristConstants.ID, canBus);
        velocityVoltage = new VelocityVoltage(0.0);

        config.CurrentLimits.SupplyCurrentLimit = WristConstants.supplyCurrentLimit;
        config.CurrentLimits.SupplyCurrentLimitEnable = true;
        config.CurrentLimits.StatorCurrentLimit = WristConstants.statorCurrentLimit;
        config.CurrentLimits.StatorCurrentLimitEnable = true;

        config.Slot0.kP = WristConstants.kP;
        config.Slot0.kI = WristConstants.kI;
        config.Slot0.kD = WristConstants.kD;

        motor.getConfigurator().apply(config);

        temperatureSignal = motor.getDeviceTemp();
        voltageSignal = motor.getMotorVoltage();
        supplyCurrentSignal = motor.getSupplyCurrent();
        statorCurrentSignal = motor.getStatorCurrent();
        positionSignal = motor.getPosition();

        BaseStatusSignal.setUpdateFrequencyForAll(50,
        temperatureSignal, voltageSignal, supplyCurrentSignal,
        statorCurrentSignal, positionSignal);
    }

    @Override
    public void setVoltage(double voltage) {
        motor.setControl(voltageOut.withOutput(voltage));
    }

    @Override
    public void setPosition(double position) {
        motor.setControl(m_request.withPosition(position));
    }

    @Override
    public void stop() {
        motor.setVoltage(0);
    }

    @Override
    public void updateInputs() {
        BaseStatusSignal.refreshAll(
            temperatureSignal,
            voltageSignal,
            supplyCurrentSignal,
            statorCurrentSignal,
            positionSignal
        );

        super.temperature = temperatureSignal.getValueAsDouble();
        super.voltage = voltageSignal.getValueAsDouble();
        super.supplyCurrent = supplyCurrentSignal.getValueAsDouble();
        super.statorCurrent = statorCurrentSignal.getValueAsDouble();
        super.position = positionSignal.getValueAsDouble();

        DogLog.log("Rollers/Temperature", super.temperature);
        DogLog.log("Rollers/Voltage", super.voltage);
        DogLog.log("Rollers/SupplyCurrent", super.supplyCurrent);
        DogLog.log("Rollers/StatorCurrent", super.statorCurrent);
        DogLog.log("Rollers/Position", super.position);
    }
}
