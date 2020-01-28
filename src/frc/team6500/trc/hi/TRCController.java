package frc.team6500.trc.hi;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID;

/**
 *	A bridge between a Joystick and Xbox controller
 *	See {@link GenericHID} for more methods
 */
public class TRCController extends GenericHID
{
	public GenericHID.HIDType type;
	/**
	 *	Create a new TRCController of a specified type
	 *	@param port the port number on which the controller is plugged in
	 *	@param type the type of human interface device connected
	 */
	public TRCController(int port, HIDType type)
	{
		super(port);
		this.type = type;
	}

	/**
	 *	Get a {@link TRCDigitalHI}s state on the physical device
	 *	@param dhi {@link TRCDigitalHI} reference to a physical button
	 *	@return the state of the specified button
	 */
	public boolean getButton(TRCDigitalHI dhi)
	{
		return this.getRawButton(dhi.getButton(this.type));
	}

	/**
	 *	Get a {@link TRCAnalogHI}s value on the physical device
	 *	@param ahi {@link TRCAnalogHI}s reference to a physical axis
	 *	@return the value of the specified axis
	 */
	public double getAxis(TRCAnalogHI ahi)
	{
		return this.getRawAxis(ahi.getAxis(this.type));
	}

	public double getX(GenericHID.Hand hand)
	{
		if (this.type == GenericHID.HIDType.kHIDGamepad) // is xbox
		{
			switch (hand)
			{
				case kLeft:	return getRawAxis(XboxController.Axis.kRightX.value);
				case kRight: return getRawAxis(XboxController.Axis.kRightX.value);
			}
		}
		return this.getX();
	}

	public double getY(GenericHID.Hand hand)
	{
		if (this.type == GenericHID.HIDType.kHIDGamepad) // is xbox
		{
			switch (hand)
			{
				case kLeft:	return getRawAxis(XboxController.Axis.kRightY.value);
				case kRight: return getRawAxis(XboxController.Axis.kRightY.value);
			}
		}
		return this.getY();
	}
}
