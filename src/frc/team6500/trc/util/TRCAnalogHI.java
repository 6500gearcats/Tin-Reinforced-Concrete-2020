package frc.team6500.trc.util;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID.HIDType;

/**
 *	Create an abstract analog Human Interface that bridges different analog devices. 
 *	Currently only supports XboxController and generic Joysticks.
 */
public class TRCAnalogHI
{
	private XboxController.Axis xbx;
	private Joystick.Axis joy;

	/**
	 *	Create a reference to a Xbox axis but not a Joystick axis
	 *	@param xboxAxis the {@link XboxController.Axis} to reference
	 */
	public TRCAnalogHI(XboxController.Axis xboxAxis)
	{
		this.xbx = xboxAxis;
	}

	/**
	 *	Create a reference to a Joystick axis but not a Xbox axis
	 *	@param joystickAxis the {@link Joystick.Axis} to reference
	 */
	public TRCAnalogHI(Joystick.Axis joystickAxis)
	{
		this.joy = joystickAxis;
	}

	/**
	 *	Create a reference to both an Xbox axis and Joystick axis
	 *	@param xboxAxis the {@link XboxController.Axis} to reference
	 *	@param joystickAxis the {@link Joystick.Axis} to reference
	 */
	public TRCAnalogHI(XboxController.Axis xboxAxis, Joystick.Axis joystickAxis)
	{
		this.xbx = xboxAxis;
		this.joy = joystickAxis;
	}

	/**
	 *	Get a raw axis id for a specified interface type
	 *	@param type the type (Xbox or Joystick) of the axis id to return
	 *	@return the axis id of the specified type (Note: returns -1 for unrecognized types)
	 */
	// this method IS PROTECTED, so the only way to access it is through a TRCController!!!
	protected int getAxis(HIDType type)
	{
		switch (type)
		{
			case HIDType.kGamepad: return this.xbx.value;
			case HIDType.kJoystick: return this.joy.value;
		}
		return -1;
	}

	/**
	 *	@return the {@link XboxController.Axis} reference
	 */
	public XboxController.Axis getXboxAxis() { return this.xbx; }

	/**
	 *	@return the {@link Joystick.Axis} reference
	 */
	public Joystick.AxisType getJoystickAxis() { return this.joy; }

	/**
	 *	@return if the analog interface contains a reference to a {@link XboxController.Axis}
	 */
	public boolean hasXboxAxis() { return this.xbx != null; }

	/**
	 *	@return if the analog interface contains a reference to a {@link Joystick.Axis}
	 */
	public boolean hasJoystickAxis() { return this.joy != null; }
}
