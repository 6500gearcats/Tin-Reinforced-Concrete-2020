package frc.team6500.trc.wrappers.sensors;

import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C.Port;

public class TRCColorSensor extends ColorSensorV3
{
    public TRCColorSensor()
    {
        super(Port.kOnboard);
    }

    public TRCColorSensor(Port port)
    {
        super(port);
    }
}