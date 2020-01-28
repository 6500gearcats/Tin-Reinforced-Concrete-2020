package frc.team6500.trc.hi;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID.HIDType;

/**
 *	Create an abstract digital Human Interface that bridges different digital devices. 
 *	Currently only supports XboxController and generic Joysticks.
 */
public class TRCDigitalHI
{
	private XboxController.Button xbx;
	private int raw;

	/**
	 *	Create a reference to a XboxButton but not a Joystick button
	 *	@param xboxButton the {@link XboxController.Button} to reference
	 */
	public TRCDigitalHI(XboxController.Button xboxButton)
	{
		this.xbx = xboxButton;
		this.raw = -1;
	}

	/**
	 *	Create a reference to a Joystick button but not a XboxButton
	 *	@param rawButton the button id to reference
	 */
	public TRCDigitalHI(int rawButton)
	{
		this.raw = rawButton;
	}

	/**
	 *	Create a reference to both an XboxButton and Joystick button
	 *	@param xboxButton the {@link XboxController.Button} to reference
	 *	@param rawButton the button id to reference
	 */
	public TRCDigitalHI(XboxController.Button xboxButton, int rawButton)
	{
		this.xbx = xboxButton;
		this.raw = rawButton;
	}

	/**
	 *	Get a raw button id for a specified interface type
	 *	@param type the type (Xbox or Joystick) of the button id to return
	 *	@return the button id of the specified type (Note: returns -1 for unrecognized types)
	 */
	// this method IS PROTECTED, so the only way to access it is through a TRCController!!!
	protected int getButton(HIDType type)
	{
		switch (type)
		{
			case kHIDGamepad: return this.xbx.value;
			case kHIDJoystick: return this.raw;
		}
		return -1;
	}

	/**
	 *	@return the {@link XboxController.Button} reference
	 */
	public XboxController.Button getXboxButton() { return this.xbx; }

	/**
	 *	@return the raw reference
	 */
	public int getRawButton() { return this.raw; }

	/**
	 *	@return if the digital interface contains a reference to a {@link XboxController.Button}
	 */
	public boolean hasXboxButton() { return this.xbx != null; }

	/**
	 *	@return if the digital interface contains a reference to a raw button
	 */
	public boolean hasRawButton() {  return this.raw > -1; }
}
