package frc.team6500.trc.wrappers.sensors;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class TRCTalonEncoder extends WPI_TalonSRX implements TRCEncoder
{
	public TRCTalonEncoder(int deviceNumber)
	{
		super(deviceNumber);
	}

	public double getDistance()
	{
		return getSelectedSensorPosition();
	}

	public void reset()
	{
		setSelectedSensorPosition(0);
	}
}