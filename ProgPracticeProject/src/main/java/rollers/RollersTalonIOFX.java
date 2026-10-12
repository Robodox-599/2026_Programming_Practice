package rollers;

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Temperature;
import edu.wpi.first.units.measure.Velocity;
import edu.wpi.first.units.measure.Voltage;

public class RollersTalonIOFX extends RollersConstants {
    TalonFX kP;
    TalonFX kI;
    TalonFX kD;
    CANbus CurrentLimits;

    public StatusSignal<Current> supplyCurrent;
    public StatusSignal<Current> statorCurrent;
    public StatusSignal<Temperature> temperature;
    public StatusSignal<Velocity> velocity;
    public StatusSignal<Voltage> voltage;

    @Override
    public static void stop() {
        
    }

    @Override
    public static void updatedInputs() {
        
    }

}