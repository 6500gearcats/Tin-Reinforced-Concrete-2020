package org.usfirst.frc.team6500.trc.sensors;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.cscore.UsbCamera;

public class TRCCamera
{
    public static final int fps = 20;
    public static final int width = 640;
    public static final int height = 480;

    public static UsbCamera camera;

    public static void initializeCamera()
    {
        camera = CameraServer.getInstance().startAutomaticCapture();
		camera.setFPS(fps);
        camera.setResolution(width, height);
        camera.setExposureAuto();
        camera.setWhiteBalanceAuto();
    }

    public static void recalibrateCamera()
    {
        camera.setExposureAuto();
        camera.setWhiteBalanceAuto();
    }
}