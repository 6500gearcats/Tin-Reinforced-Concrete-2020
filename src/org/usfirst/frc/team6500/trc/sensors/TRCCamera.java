package org.usfirst.frc.team6500.trc.sensors;

import edu.wpi.cscore.CameraServerJNI;

import org.usfirst.frc.team6500.trc.util.TRCNetworkData;
import org.usfirst.frc.team6500.trc.util.TRCTypes.VerbosityType;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.UsbCameraInfo;
import edu.wpi.first.wpilibj.CameraServer;

/**
 * Class to setup and modify the camera.  The parameters at the top of the file (fps, width, height)
 * must not cause streaming to exceed the 7 Mb/s limit (meaning 480x640 @ 20 fps is the probably the
 * best quality we can get away with).
 */
public class TRCCamera
{
    public static final int fps = 20;
    public static final int width = 640;
    public static final int height = 480;

    public static UsbCamera camera;

    /**
     * Initialize the camera so it can be viewed from 
     */
    public static void initializeCamera()
    {
        //UsbCameraInfo cameraInfo = CameraServerJNI.enumerateUsbCameras()[0];
        //camera = new UsbCamera(cameraInfo.name, cameraInfo.path);
        camera = CameraServer.getInstance().startAutomaticCapture();
		camera.setFPS(fps);
        camera.setResolution(width, height);
        camera.setExposureAuto();
        camera.setWhiteBalanceAuto();
        TRCNetworkData.logString(VerbosityType.Log_Info, "Camera Sensor is online.");
    }

    /**
     * If the camera was calibrated in an unusual visual scenario, use this to fix visibility isuues
     */
    public static void recalibrateCamera()
    {
        camera.setExposureAuto();
        camera.setWhiteBalanceAuto();
        TRCNetworkData.logString(VerbosityType.Log_Debug, "Camera recalibrated.");
    }
}