/*
 *  TRCDrivePID
 *      static class to control the Robot Drive
 */

package org.usfirst.frc.team6500.trc.auto;

import org.usfirst.frc.team6500.trc.wrappers.sensors.TRCEncoderSet;
import org.usfirst.frc.team6500.trc.wrappers.sensors.TRCGyroBase;
import org.usfirst.frc.team6500.trc.wrappers.systems.drives.TRCDifferentialDrive;
import org.usfirst.frc.team6500.trc.wrappers.systems.drives.TRCMecanumDrive;
import org.usfirst.frc.team6500.trc.util.TRCNetworkData;
import org.usfirst.frc.team6500.trc.util.TRCSpeed;
import org.usfirst.frc.team6500.trc.util.TRCTypes.DirectionType;
import org.usfirst.frc.team6500.trc.util.TRCTypes.DriveActionType;
import org.usfirst.frc.team6500.trc.util.TRCTypes.DriveType;
import org.usfirst.frc.team6500.trc.util.TRCTypes.VerbosityType;

import edu.wpi.first.wpilibj.RobotState;

public class TRCDrivePID
{
    private static final double deadband = 2.0;
    private static final int verificationMin = 100;

    private static TRCEncoderSet encoders;
    private static TRCGyroBase gyro;
    private static Object drive;

    private static boolean driving;
    private static double maxSpeed = 0.0;
    private static DriveType driveType;

    private static MiniPID MPID;
    private static TRCSpeed autoSpeed;

    private static DriveActionType actionType;
    private static double measurement;


    /**
     * Set up the necessary elements to be able to drive the robot in autonomous with a PID control loop for accurate distances and degrees
     * 
     * @param encoderset The robot's drivetrain encoders
     * @param gyroBase The robot's primary gyro
     * @param driveBase The robot's drivetrain
     * @param driveBaseType The type of the robot's drivetrain {@link DriveType}
     * @param topSpeed Fastest speed the robot should go in autonomous
     */
    public static void initializeTRCDrivePID(TRCEncoderSet encoderset, TRCGyroBase gyroBase, Object driveBase, DriveType driveBaseType, double topSpeed)
    {
        encoders = encoderset;
        gyro = gyroBase;
        drive = driveBase;
        driveType = driveBaseType;
        maxSpeed = topSpeed;

        TRCNetworkData.logString(VerbosityType.Log_Info, "DrivePID is online.");
        TRCNetworkData.createDataPoint("PIDSetpoint");
        TRCNetworkData.createDataPoint("PIDOutput");
        TRCNetworkData.createDataPoint("PIDOutputSmoothed");
    }

    /**
     * Executes the specific action
     * 
     * @param driveAction The type of action the robot should take (one of {@link DriveActionType})
     * @param unit The inches/degrees of the action
     */
    public static void run(DriveActionType driveAction, double unit)
    {
        driving = false;
        actionType = driveAction;
        measurement = unit;

        autoSpeed = new TRCSpeed();
        MPID = new MiniPID(1.0, 0.0, 0.0);

        switch(actionType)
        {
            case Forward:
                driveForwardBack();
                break;
            case Right:
                driveLeftRight();
                break;
            case Rotate:
                driveRotation();
                break;
            default:
                break;
        }
    }


    /**
     * Drive the robot forward (measurement) inches
     */
    public static void driveForwardBack()
    {
        autoSpeed.reset();
        MPID.reset();
        MPID.setSetpoint(measurement);
        TRCNetworkData.updateDataPoint("PIDSetpoint", measurement);
        MPID.setOutputLimits(-maxSpeed, maxSpeed);

        if (!driving)
        {
            driving = true;
            int deadbandcounter = 0;

            while (deadbandcounter < verificationMin && RobotState.isAutonomous())
            {
                double newSpeed = MPID.getOutput(encoders.getAverageDistanceTraveled(DirectionType.ForwardBackward));
                TRCNetworkData.updateDataPoint("PIDOutput", newSpeed);
                double smoothedSpeed = autoSpeed.calculateSpeed(newSpeed, 0.85);
                TRCNetworkData.updateDataPoint("PIDOutputSmoothed", smoothedSpeed);

                if (driveType == DriveType.Mecanum)
                {
                    ((TRCMecanumDrive) drive).driveCartesian(-smoothedSpeed, 0.0, 0.0);
                }
                else if (driveType == DriveType.Differential)
                {
                    ((TRCDifferentialDrive) drive).arcadeDrive(smoothedSpeed, 0.0, false);
                }

                if (Math.abs(encoders.getAverageDistanceTraveled(DirectionType.ForwardBackward) - measurement) < deadband)
                {
                    deadbandcounter++;
                }
            }

            driving = false;
        }
    }

    /**
     * Drive the robot right (measurement) inches (exclusive to mecanum)
     */
    public static void driveLeftRight()
    {
        if (!driving && driveType == DriveType.Mecanum) // Don't run this if not Mecanum!!
        {
            autoSpeed.reset();
            MPID.reset();
            MPID.setSetpoint(measurement);
            TRCNetworkData.updateDataPoint("PIDSetpoint", measurement);
            MPID.setOutputLimits(-maxSpeed, maxSpeed);

            driving = true;
            int deadbandcounter = 0;

            while (deadbandcounter < verificationMin && RobotState.isAutonomous())
            {
                double newSpeed = MPID.getOutput(encoders.getAverageDistanceTraveled(DirectionType.LeftRight));
                TRCNetworkData.updateDataPoint("PIDOutput", newSpeed);
                double smoothedSpeed = autoSpeed.calculateSpeed(newSpeed, 1.0);
                TRCNetworkData.updateDataPoint("PIDOutputSmoothed", smoothedSpeed);
                
                ((TRCMecanumDrive) drive).driveCartesian(0.0, smoothedSpeed, 0.0);

                if (Math.abs(encoders.getAverageDistanceTraveled(DirectionType.LeftRight) - measurement) < deadband)
                {
                    deadbandcounter++;
                }
            }
            
            driving = false;
        }
    }

    /**
     * Rotate the robot (measurement) degrees
     */
    public static void driveRotation()
    {
        if (!driving)
        {
            autoSpeed.reset();
            MPID.reset();
            MPID.setSetpoint(measurement);
            TRCNetworkData.updateDataPoint("PIDSetpoint", measurement);
            MPID.setOutputLimits(-maxSpeed, maxSpeed);

            driving = true;
            int deadbandcounter = 0;

            while (deadbandcounter < verificationMin && RobotState.isAutonomous())
            {
                double newSpeed = MPID.getOutput(gyro.getAngle());
                TRCNetworkData.updateDataPoint("PIDOutput", newSpeed);
                double smoothedSpeed = autoSpeed.calculateSpeed(newSpeed, 1.0);
                TRCNetworkData.updateDataPoint("PIDOutputSmoothed", smoothedSpeed);

                if (driveType == DriveType.Mecanum)
                {
                    ((TRCMecanumDrive) drive).driveCartesian(0.0, 0.0, smoothedSpeed);
                }
                else if (driveType == DriveType.Differential)
                {
                    ((TRCDifferentialDrive) drive).arcadeDrive(0.0, smoothedSpeed, false);
                }

                if (Math.abs(gyro.getAngle() - measurement) < deadband)
                {
                    deadbandcounter++;
                }
            }

            driving = false;
        }
    }
}