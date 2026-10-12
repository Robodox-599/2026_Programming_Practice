package frc.robot.Subsystems.Rollers;

import static edu.wpi.first.units.Units.RotationsPerSecond;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;

import dev.doglog.DogLog;

import com.ctre.phoenix6.CANBus;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Temperature;
import edu.wpi.first.units.measure.Voltage;

public class RollersIOTalonFX extends RollersIO{
    public final TalonFX motor;
    public final TalonFXConfiguration config;
    public final CANBus canBus;
    public final VoltageOut voltage_out;
    public final VelocityVoltage velocity_voltage;



    public StatusSignal<Temperature> temperatureSignal;
    public StatusSignal<Voltage> voltageSignal;
    public StatusSignal<Current> supplyCurrentSignal;
    public StatusSignal<Current> statorCurrentSignal;
    public StatusSignal<AngularVelocity> velocitySignal;
    

    public RollersIOTalonFX() {

        canBus = new CANBus(RollersConstants.canBus);
        voltage_out = new VoltageOut(0.0);
        config = new TalonFXConfiguration();
        motor = new TalonFX(RollersConstants.ID, canBus);
        velocity_voltage = new VelocityVoltage(0.0);

        config.CurrentLimits.StatorCurrentLimit = RollersConstants.statorCurrentLimit;
        config.CurrentLimits.StatorCurrentLimitEnable = true;
        config.CurrentLimits.SupplyCurrentLimit = RollersConstants.supplyCurrentLimit;
        config.CurrentLimits.SupplyCurrentLimitEnable = true;

        config.Slot0.kP = RollersConstants.kP;
        config.Slot0.kI = RollersConstants.kI;
        config.Slot0.kD = RollersConstants.kD;

        motor.getConfigurator().apply(config);

        temperatureSignal = motor.getDeviceTemp();
        voltageSignal = motor.getMotorVoltage();
        supplyCurrentSignal = motor.getSupplyCurrent();
        statorCurrentSignal = motor.getStatorCurrent();
        velocitySignal = motor.getVelocity();

        BaseStatusSignal.setUpdateFrequencyForAll(50.0,
        temperatureSignal, voltageSignal, supplyCurrentSignal,
        statorCurrentSignal, velocitySignal);

        
    } 

    @Override
    public void setVoltage(double voltage) {
        motor.setControl(voltage_out.withOutput(voltage));
    }

    
    @Override
    public void setVelocity(double velocity) {
        motor.setControl(velocity_voltage.withVelocity(RotationsPerSecond.of(velocity)));
        
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
            velocitySignal
        );
        super.temperature = temperatureSignal.getValueAsDouble();
        super.voltage = voltageSignal.getValueAsDouble();
        super.supplyCurrent = supplyCurrentSignal.getValueAsDouble();
        super.statorCurrent = statorCurrentSignal.getValueAsDouble();
        super.velocity = velocitySignal.getValueAsDouble();


        DogLog.log("Rollers/Temperature", super.temperature);
        DogLog.log("Rollers/Voltage", super.voltage);
        DogLog.log("Rollers/SupplyCurrent", super.supplyCurrent);
        DogLog.log("Rollers/StatorCurrent", super.statorCurrent);
        DogLog.log("Rollers/Velocity", super.velocity);
    }
}
