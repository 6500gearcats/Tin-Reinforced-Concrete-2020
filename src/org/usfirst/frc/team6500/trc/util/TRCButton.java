package org.usfirst.frc.team6500.trc.util;


import org.usfirst.frc.team6500.trc.util.TRCTypes.ControllerButtonType;
import org.usfirst.frc.team6500.trc.util.TRCTypes.XboxButtonType;


public class TRCButton
{
    private ControllerButtonType type;
    private XboxButtonType xboxButton;
    private int rawButton;

    public TRCButton(XboxButtonType xbutton)
    {
        this.type = ControllerButtonType.Xbox;
        this.xboxButton = xbutton;
    }

    public TRCButton(int rbutton)
    {
        this.type = ControllerButtonType.Generic;
        this.rawButton = rbutton;
    }


    private Object returnButton(ControllerButtonType buttonType)
    {
        if (this.type == ControllerButtonType.Generic && buttonType == ControllerButtonType.Generic) { return this.rawButton; }
        else if (this.type == ControllerButtonType.Xbox && buttonType == ControllerButtonType.Xbox) { return this.xboxButton; }
        else // If this.type != buttonType
        {
            if (buttonType == ControllerButtonType.Generic)
            {
                return 1;
            }
            else if (buttonType == ControllerButtonType.Xbox)
            {
                return XboxButtonType.A;
            }
            else
            {
                return 1;
            }
        }
    }

    public ControllerButtonType getType() { return this.type; }
    public int getRawButton() { return ((int) returnButton(ControllerButtonType.Generic)); }
    public XboxButtonType getXboxButton() { return ((XboxButtonType) returnButton(ControllerButtonType.Xbox)); }
}