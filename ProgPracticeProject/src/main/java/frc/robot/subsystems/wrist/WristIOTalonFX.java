package frc.robot.subsystems.wrist;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Temperature;
import edu.wpi.first.units.measure.Voltage;

public class WristIOTalonFX extends WristIO{
    private final TalonFX wristMotor;
    private final CANBus wristCANBus;
    private final TalonFXConfiguration wristConfig;
    private final MotionMagicVoltage m_request;

    public final StatusSignal<Voltage> wristVoltageSingal;
    public final StatusSignal<Temperature> wristTemperatureSignal;
    public final StatusSignal<Current> wristStatorCurrentSignal;
    public final StatusSignal<Current> wristSupplyCurrentSingal;
    public final StatusSignal<Angle> wristPositionSingal;

    public WristIOTalonFX() {
        wristMotor = new TalonFX(WristConstants.motorID);
        wristCANBus = new CANBus(WristConstants.CANBus);
        wristConfig = new TalonFXConfiguration()
        .withCurrentLimits(
            new CurrentLimitsConfigs()
            .withStatorCurrentLimit(WristConstants.statorCurrentLimit)
            .withSupplyCurrentLimit(WristConstants.supplyCurrentLimit))
        .withSlot0
            (new Slot0Configs()
            .withKP(WristConstants.kP)
            .withKI(WristConstants.kI)
            .withKD(WristConstants.kD))
        .withMotionMagic(new MotionMagicConfigs()
        .withMotionMagicCruiseVelocity(WristConstants.maxVelocity)
        .withMotionMagicAcceleration(WristConstants.maxAcceleraion));

        m_request = new MotionMagicVoltage(0);

        wristVoltageSingal = wristMotor.getSupplyVoltage();
        wristTemperatureSignal = wristMotor.getDeviceTemp();
        wristStatorCurrentSignal = wristMotor.getStatorCurrent();
        wristSupplyCurrentSingal = wristMotor.getSupplyCurrent();
        wristPositionSingal = wristMotor.getPosition();

        wristMotor.optimizeBusUtilization();

        BaseStatusSignal.setUpdateFrequencyForAll(
            50, 
            wristVoltageSingal,
            wristTemperatureSignal,
            wristStatorCurrentSignal,
            wristSupplyCurrentSingal,
            wristPositionSingal);

    }

    @Override
    public void updateInputs() {

    }

    @Override
    public void stop() {
        wristMotor.setVoltage(0);
    }

    @Override
    public void setPosition() {

    }

    @Override
    public void setVoltage(double voltage) {
        wristMotor.setVoltage(voltage);
    }
}
