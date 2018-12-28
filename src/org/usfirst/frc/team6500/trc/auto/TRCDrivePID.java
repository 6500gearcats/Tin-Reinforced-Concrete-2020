package org.usfirst.frc.team6500.trc.auto;

import org.usfirst.frc.team6500.trc.wrappers.sensors.TRCEncoderSet;
import org.usfirst.frc.team6500.trc.wrappers.sensors.TRCGyroBase;
import org.usfirst.frc.team6500.trc.wrappers.systems.drives.TRCDifferentialDrive;
import org.usfirst.frc.team6500.trc.wrappers.systems.drives.TRCMecanumDrive;
import org.usfirst.frc.team6500.trc.util.TRCSpeed;
import org.usfirst.frc.team6500.trc.util.TRCTypes.DriveType;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.drive.RobotDriveBase;

public class TRCDrivePID
{
    private static final double deadband = 2.0;
    private static final int verificationMin = 100;

    private static TRCEncoderSet encoders;
    private static TRCGyroBase gyro;
    private static RobotDriveBase drive;

    private static MiniPID MPID;
    private static TRCSpeed autoSpeed;

    private static boolean driving;
    private static DriveType driveType;
    

    public static void initializeTRCDrivePID(TRCEncoderSet encoderset, TRCGyroBase gyroBase, RobotDriveBase driveBase, DriveType driveBaseType)
    {
        encoders = encoderset;
        gyro = gyroBase;
        drive = driveBase;
        driveType = driveBaseType;
        
        driving = false;

        autoSpeed = new TRCSpeed();
        MPID = new MiniPID(1.0, 0.0, 0.0);
    }


    public static void driveForward(double inches)
    {
        autoSpeed.reset();
        MPID.reset();
        MPID.setSetpoint(inches);
        MPID.setOutputLimits(-1.0, 1.0);

        if (!driving)
        {
            driving = true;
            int deadbandcounter = 0;

            while (deadbandcounter < verificationMin && RobotState.isAutonomous())
            {
                double newSpeed = MPID.getOutput(encoders.getAverageDistanceTraveled());
                double smoothedSpeed = autoSpeed.calculateSpeed(newSpeed, 1.0);

                if (driveType == DriveType.Mecanum)
                {
                    ((TRCMecanumDrive) drive).driveCartesian(0.0, smoothedSpeed, 0.0);
                }
                else if (driveType == DriveType.Differential)
                {
                    ((TRCDifferentialDrive) drive).arcadeDrive(smoothedSpeed, 0.0, false);
                }

                if (Math.abs(encoders.getAverageDistanceTraveled() - inches) < deadband)
                {
                    deadbandcounter++;
                }
            }

            driving = false;
        }
    }

    public static void driveRotation(int degrees)
    {
        autoSpeed.reset();
        MPID.reset();
        MPID.setSetpoint(degrees);
        MPID.setOutputLimits(-0.5, 0.5);

        if (!driving)
        {
            driving = true;
            int deadbandcounter = 0;

            while (deadbandcounter < verificationMin && RobotState.isAutonomous())
            {
                double newSpeed = MPID.getOutput(gyro.getAngle());
                double smoothedSpeed = autoSpeed.calculateSpeed(newSpeed, 1.0);

                if (driveType == DriveType.Mecanum)
                {
                    ((TRCMecanumDrive) drive).driveCartesian(0.0, 0.0, smoothedSpeed);
                }
                else if (driveType == DriveType.Differential)
                {
                    ((TRCDifferentialDrive) drive).arcadeDrive(0.0, smoothedSpeed, false);
                }

                if (Math.abs(gyro.getAngle() - degrees) < deadband)
                {
                    deadbandcounter++;
                }
            }

            driving = false;
        }
    }
}