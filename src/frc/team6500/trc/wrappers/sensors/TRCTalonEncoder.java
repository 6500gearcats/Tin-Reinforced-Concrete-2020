package frc.team6500.trc.wrappers.sensors;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.PIDSource;

public class TRCTalonEncoder extends PIDSource
{
    private WPI_TalonSRX talon;
    private double distancePerPulse;
    private boolean inverse;


    public TRCTalonEncoder(int talonId, double dpp, boolean reverse)
    {
        this.talon = new WPI_TalonSRX(talonId);
        this.distancePerPulse = dpp;
        this.inverse = reverse;
    }

    public double getDistance()
    {
        if (this.inverse)
        {
            return -this.talon.getSelectedSensorPosition() * this.distancePerPulse;
        }
        else
        {
            return this.talon.getSelectedSensorPosition() * this.distancePerPulse;
        }
    }

    public void reset()
    {
        this.talon.setSelectedSensorPosition(0);
    }

    public double pidGet()
    {
    	return this.getDistance();
    }
}