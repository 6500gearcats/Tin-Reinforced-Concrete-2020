package frc.team6500.trc.util;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Joystick;

/**
 *	Create an abstract analog Human Interface that bridges different analog devices. 
 *	Currently only supports XboxController and generic Joysticks.
 */
public class TRCAnalogHI
{
	private XboxController.Axis xbx;
	private Joystick.AxisType joy;

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
	 *	@param joystickAxis the {@link Joystick.AxisType} to reference
	 */
	public TRCAnalogHI(Joystick.AxisType joystickAxis)
	{
		this.joy = joystickAxis;
	}

	/**
	 *	Create a reference to both an Xbox axis and Joystick axis
	 *	@param xboxAxis the {@link XboxController.Axis} to reference
	 *	@param joystickAxis the {@link Joystick.AxisType} to reference
	 */
	public TRCAnalogHI(XboxController.Axis xboxAxis, Joystick.AxisType joystickAxis)
	{
		this.xbx = xboxAxis;
		this.joy = joystickAxis;
	}

	/**
	 *	@return the {@link XboxController.Axis} reference
	 */
	public XboxController.Axis getXboxAxis() { return this.xbx; }

	/**
	 *	@return the {@link Joystick.AxisType} reference
	 */
	public Joystick.AxisType getJoystickAxis() { return this.joy; }

	/**
	 *	@return if the analog interface contains a reference to a {@link XboxController.Axis}
	 */
	public boolean hasXboxAxis() { return this.xbx != null; }

	/**
	 *	@return if the analog interface contains a reference to a {@link Joystick.AxisType}
	 */
	public boolean hasJoystickAxis() { return this.joy != null; }
}
