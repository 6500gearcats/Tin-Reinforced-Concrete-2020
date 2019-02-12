package org.usfirst.frc.team6500.trc.util;


import org.usfirst.frc.team6500.trc.util.TRCTypes.ControllerType;
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

    public double getXboxAxis()
    {

    }
}