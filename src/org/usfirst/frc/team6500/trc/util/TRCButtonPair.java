package org.usfirst.frc.team6500.trc.util;


import org.usfirst.frc.team6500.trc.util.TRCTypes.ControllerButtonType;
import org.usfirst.frc.team6500.trc.util.TRCTypes.XboxButtonType;


public class TRCButtonPair
{
    private TRCButton xboxButton;
    private TRCButton rawButton;

    public TRCButtonPair(int rButton, XboxButtonType xButton)
    {
        this.xboxButton = new TRCButton(xButton);
        this.rawButton = new TRCButton(rButton);
    }


    public int getButton(ControllerButtonType buttonType)
    {
        if (buttonType == ControllerButtonType.Generic)
        {
            return this.rawButton.getRawButton();
        }
        else if (buttonType == ControllerButtonType.Xbox)
        {
            return this.xboxButton.getXboxButton().ordinal();
        }
        else
        {
            return 1;
        }
    }
}