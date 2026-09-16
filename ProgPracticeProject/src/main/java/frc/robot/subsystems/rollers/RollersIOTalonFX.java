package frc.robot.subsystems.rollers;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;

import dev.doglog.DogLog;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Temperature;
import edu.wpi.first.units.measure.Voltage;

public class RollersIOTalonFX extends RollersIO {
     private final TalonFX rollersMotor;
     private final CANBus rollersCANBus;
     private final TalonFXConfiguration rollersConfig;

     private final StatusSignal<AngularVelocity> rollersVelocitySignal;
     private final StatusSignal<Voltage> rollersVoltageSignal;
     private final StatusSignal<Current> rollersStatorCurrentSignal;
     private final StatusSignal<Current> rollersSupplyCurrentSignal;
     private final StatusSignal<Temperature> rollersTemperatureSignal;

     private VoltageOut m_request;

     public RollersIOTalonFX() {
        rollersCANBus = new CANBus(RollersConstants.rollerCANBus);
         rollersMotor = new TalonFX(RollersConstants.rollerMotorID, rollersCANBus);
         rollersConfig = new TalonFXConfiguration()
            .withCurrentLimits(new CurrentLimitsConfigs()
            .withSupplyCurrentLimit(RollersConstants.supplyCurrentLimit)
            .withSupplyCurrentLimitEnable(true)
            .withStatorCurrentLimit(RollersConstants.statorCurrentLimit)
            .withStatorCurrentLimitEnable(true)
            );

         rollersVelocitySignal = rollersMotor.getVelocity();
         rollersVoltageSignal = rollersMotor.getMotorVoltage();
         rollersStatorCurrentSignal = rollersMotor.getStatorCurrent();
         rollersSupplyCurrentSignal = rollersMotor.getSupplyCurrent();
         rollersTemperatureSignal = rollersMotor.getDeviceTemp();

         m_request = new VoltageOut(0);

         BaseStatusSignal.setUpdateFrequencyForAll(
            50, 
            rollersVelocitySignal, 
            rollersVoltageSignal, 
            rollersStatorCurrentSignal, 
            rollersSupplyCurrentSignal, 
            rollersTemperatureSignal);

            rollersMotor.optimizeBusUtilization();
     }

     @Override
     public void updateInputs() {
        BaseStatusSignal.refreshAll(rollersVelocitySignal, 
        rollersVoltageSignal,
        rollersStatorCurrentSignal,
        rollersSupplyCurrentSignal,
        rollersTemperatureSignal);

        super.rollersVelocityRadPerSec = rollersVelocitySignal.getValueAsDouble();
        super.rollersVoltage = rollersVoltageSignal.getValueAsDouble();
        super.rollersStatorCurrent = rollersStatorCurrentSignal.getValueAsDouble();
        super.rollersSupplyCurrent = rollersSupplyCurrentSignal.getValueAsDouble();
        super.rollersTemperature = rollersTemperatureSignal.getValueAsDouble();

        DogLog.log("Rollers/Velocity", super.rollersVelocityRadPerSec);
        DogLog.log("Rollers/Voltage", super.rollersVoltage);
        DogLog.log("Rollers/StatorCurrent", super.rollersStatorCurrent);
        DogLog.log("Rollers/SupplyCurrent", super.rollersSupplyCurrent);
        DogLog.log("Rollers/Temperature", super.rollersTemperature);
    }

    @Override
    public void setVoltage(double voltage) {
        rollersMotor.setControl(m_request.withOutput(voltage));
    }

    @Override
    public void stop() {
        rollersMotor.setVoltage(0);
    }

}
