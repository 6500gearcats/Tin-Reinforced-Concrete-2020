package frc.team6500.trc.wrappers.sensors;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.SpeedController;

import com.revrobotics.CANEncoder;

public class TRCSparkMaxEncoder
{
    private CANSparkMax sparkMax;
    private CANEncoder smEncoder;
    private double distancePerPulse;
    private boolean inverse;


    public TRCSparkMaxEncoder(SpeedController motor, double dpp, boolean reverse)
    {
        this.sparkMax = (CANSparkMax) motor;
        this.smEncoder = new CANEncoder(this.sparkMax);
        this.distancePerPulse = dpp;
        this.inverse = reverse;
    }

    public double getDistance()
    {
        if (this.inverse)
        {
            return -this.smEncoder.getPosition() * this.distancePerPulse;
        }
        else
        {
            return this.smEncoder.getPosition() * this.distancePerPulse;
        }
    }

    public void reset()
    {
        this.smEncoder.setPosition(0);
    }
}