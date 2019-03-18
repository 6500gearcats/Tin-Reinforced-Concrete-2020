package org.usfirst.frc.team6500.trc.util;


import org.usfirst.frc.team6500.trc.util.TRCTypes.ControllerType;
import org.usfirst.frc.team6500.trc.util.TRCTypes.XboxAxisType;
import org.usfirst.frc.team6500.trc.util.TRCTypes.ControllerButtonType;
import org.usfirst.frc.team6500.trc.util.TRCTypes.XboxButtonType;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;


public class TRCController
{
    private Joystick stickController;
    private XboxController xboxController;
    private ControllerType controllerType;
    private ControllerButtonType controllerButtonType;
    private int port;

    public TRCController(int port, ControllerType type)
    {
        this.controllerType = type;
        this.port = port;

        switch (controllerType)
        {
            case Xbox360:
                this.xboxController = new XboxController(port);
                this.controllerButtonType = ControllerButtonType.Xbox;
                break;
            case Extreme3D: //  | These will fall through to the default
            case Generic:   //  |
            default:        // \|/
                this.stickController = new Joystick(port);
                this.controllerButtonType = ControllerButtonType.Generic;
                break;
        }
    }


    private boolean getButtonIndividual(TRCButton button)
    {
        if (button.getType() == ControllerButtonType.Xbox && this.controllerType == ControllerType.Xbox360)
        {
            return this.xboxController.getRawButton(button.getXboxButton().ordinal());
        }
        else if (button.getType() == ControllerButtonType.Generic && (this.controllerType == ControllerType.Generic || this.controllerType == ControllerType.Extreme3D))
        {
            return this.stickController.getRawButton(button.getRawButton());
        }
        else
        {
            return false;
        }
    }

    private boolean getButtonPair(TRCButtonPair buttonPair)
    {
        switch (this.controllerButtonType)
        {
            case Generic:
                return this.stickController.getRawButton(buttonPair.getButton(this.controllerButtonType));
            case Xbox:
                return this.xboxController.getRawButton(buttonPair.getButton(this.controllerButtonType));
            default:
                return false;
        }
    }

    private boolean getButtonInt(int button)
    {
        switch (this.controllerButtonType)
        {
            case Generic:
                //System.out.println(Integer.toString(button) + Boolean.toString(this.stickController.getRawButton(button)));
                return this.stickController.getRawButton(button);
            case Xbox:
                return this.xboxController.getRawButton(button);
            default:
                return false;
        }
    }

    private boolean getButtonXbox(XboxButtonType button)
    {
        switch (this.controllerButtonType)
        {
            case Xbox:
                return this.xboxController.getRawButton(button.ordinal());
            default:
                return false;
        }
    }

    /**
     * Give this an int, XboxButtonType, TRCButton, or TRCButtonPair; nothing else will work
     * 
     */
    public boolean getButton(Object button)
    {
        if (button instanceof Integer)
        {
            return this.getButtonInt((Integer) button);
        }
        else if (button instanceof XboxButtonType)
        {
            return this.getButtonXbox((XboxButtonType) button);
        }
        else if (button instanceof TRCButtonPair)
        {
            return this.getButtonPair((TRCButtonPair) button);
        }
        else if (button instanceof TRCButton)
        {
            return this.getButtonIndividual((TRCButton) button);
        }
        else
        {
            return false;
        }
    }

    public double getAxis(XboxAxisType axis)
    {
        switch (this.controllerType)
        {
            case Xbox360:
                return this.xboxController.getRawAxis(axis.ordinal());
            default:
                return 0.0;
        }
    }

    public double getAxis(int axis)
    {
        switch (this.controllerType)
        {
            case Xbox360:
                return this.xboxController.getRawAxis(axis);
            case Extreme3D: // Falls through
            case Generic:
                return this.stickController.getRawAxis(axis);
            default:
                return 0.0;
        }
    }

    public int getPOV()
    {
        switch (this.controllerType)
        {
            case Xbox360:
                return this.xboxController.getPOV();
            case Extreme3D: // Falls through
            case Generic:
                return this.stickController.getPOV();
            default:
                return 0;
        }
    }

    public ControllerType getType()
    {
        return this.controllerType;
    }

    public double getX()
    {
        switch (this.controllerType)
        {
            case Xbox360:
                return this.xboxController.getX();
            case Extreme3D: // Falls through
            case Generic:
                return this.stickController.getX();
            default:
                return 0;
        }
    }

    public double getX(Hand hand)
    {
        switch (this.controllerType)
        {
            case Xbox360:
                return this.xboxController.getX(hand);
            case Extreme3D: // Falls through
            case Generic:
                return this.stickController.getX(hand);
            default:
                return 0;
        }
    }

    public double getY()
    {
        switch (this.controllerType)
        {
            case Xbox360:
                return this.xboxController.getY();
            case Extreme3D: // Falls through
            case Generic:
                return this.stickController.getY();
            default:
                return 0;
        }
    }

    public double getY(Hand hand)
    {
        switch (this.controllerType)
        {
            case Xbox360:
                return this.xboxController.getY(hand);
            case Extreme3D: // Falls through
            case Generic:
                return this.stickController.getY(hand);
            default:
                return 0;
        }
    }

    public double getZ()
    {
        switch (this.controllerType)
        {
            case Xbox360:
                return this.xboxController.getX(Hand.kLeft);
            case Extreme3D: // Falls through
            case Generic:
                return this.stickController.getZ();
            default:
                return 0;
        }
    }

    public double getZ(Hand hand)
    {
        switch (this.controllerType)
        {
            case Xbox360:
                return this.xboxController.getX(hand);
            case Extreme3D: // Falls through
            case Generic:
                return this.stickController.getZ();
            default:
                return 0;
        }
    }
    
    public double getThrottle()
    {
        switch (this.controllerType)
        {
            case Xbox360:
                return this.xboxController.getTriggerAxis(Hand.kRight);
            case Extreme3D: // Falls through
            case Generic:
                return this.stickController.getThrottle();
            default:
                return 0;
        }
    }

    public double getTrigger(Hand hand)
    {
        switch (this.controllerType)
        {
            case Xbox360:
                return this.xboxController.getTriggerAxis(hand);
            case Extreme3D: // Falls through
            case Generic:
                return this.stickController.getThrottle();
            default:
                return 0;
        }
    }

    public int getPort() { return this.port; }


    // Only use these while double checking the type of the controller before doing anything... otherwise null pointers!
    public Joystick getStick() { return this.stickController; }
    public XboxController getXbox() { return this.xboxController; }
}