package frc.team6500.trc.wrappers.sensors;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class TRCTalonEncoder
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

    public double getRate()
    {
        return this.talon.getSelectedSensorVelocity();
    }

    public void reset()
    {
        this.talon.setSelectedSensorPosition(0);
    }
}