package frc.team6500.trc.wrappers.sensors;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.SpeedController;

import com.revrobotics.CANEncoder;

public class TRCSparkMaxEncoder
{
    private CANSparkMax sparkMax;
    private CANEncoder smEncoder;
    private double multiplier;
    private boolean inverse;


    public TRCSparkMaxEncoder(SpeedController motor, double multiplier, boolean reverse)
    {
        this.sparkMax = (CANSparkMax) motor;
        this.smEncoder = new CANEncoder(this.sparkMax);
        this.smEncoder.setPositionConversionFactor(multiplier);
        this.multiplier = multiplier;
        this.inverse = reverse;
    }

    public double getDistance()
    {
        if (this.inverse)
        {
            return -this.smEncoder.getPosition();
        }
        else
        {
            return this.smEncoder.getPosition();
        }
    }

    public double getRate()
    {
        return this.smEncoder.getVelocity();
    }

    public void setInverted(boolean invert)
    {
        this.smEncoder.setInverted(invert);
    }

    public boolean getInverted()
    {
        return this.smEncoder.getInverted();
    }

    public void reset()
    {
        this.smEncoder.setPosition(0);
    }
}