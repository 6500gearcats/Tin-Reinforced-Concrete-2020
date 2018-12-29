package org.usfirst.frc.team6500.trc.auto;

import org.usfirst.frc.team6500.trc.wrappers.sensors.TRCEncoderSet;
import org.usfirst.frc.team6500.trc.wrappers.sensors.TRCGyroBase;
import org.usfirst.frc.team6500.trc.wrappers.systems.drives.TRCDifferentialDrive;
import org.usfirst.frc.team6500.trc.wrappers.systems.drives.TRCMecanumDrive;
import org.usfirst.frc.team6500.trc.util.TRCSpeed;
import org.usfirst.frc.team6500.trc.util.TRCTypes.DriveActionType;
import org.usfirst.frc.team6500.trc.util.TRCTypes.DriveType;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.drive.RobotDriveBase;

public class TRCDrivePID extends Thread
{
    private static final double deadband = 2.0;
    private static final int verificationMin = 100;

    private TRCEncoderSet encoders;
    private TRCGyroBase gyro;
    private RobotDriveBase drive;

    private MiniPID MPID;
    private TRCSpeed autoSpeed;

    private static boolean driving;
    private DriveType driveType;
    private DriveActionType actionType;
    private double measurement;


    /**
     * Set up the necessary elements to be able to drive the robot in autonomous with a PID control loop for accurate distances and degrees
     * 
     * @param encoderset The robot's encoders
     * @param gyroBase The robot's gyroscop
     * @param driveBase The robot's drivetrain
     * @param driveBaseType The type of the robot's drivetrain (one of {@link DriveType})
     * @param driveAction The type of action the robot should take
     * @param unit The inches/degrees of the action
     */
    public TRCDrivePID(TRCEncoderSet encoderset, TRCGyroBase gyroBase, RobotDriveBase driveBase, DriveType driveBaseType, DriveActionType driveAction, double unit)
    {
        encoders = encoderset;
        gyro = gyroBase;
        drive = driveBase;
        driveType = driveBaseType;
        
        driving = false;
        actionType = driveAction;
        measurement = unit;

        autoSpeed = new TRCSpeed();
        MPID = new MiniPID(1.0, 0.0, 0.0);
    }

    @Override
    public void run()
    {
        switch(actionType)
        {
            case Forward:
                this.driveForward();
                break;
            case Right:
                this.driveRight();
                break;
            case Rotate:
                this.driveRotation();
                break;
            default:
                break;
        }
    }


    /**
     * Drive the robot forward (measurement) inches
     */
    public void driveForward()
    {
        autoSpeed.reset();
        MPID.reset();
        MPID.setSetpoint(measurement);
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

                if (Math.abs(encoders.getAverageDistanceTraveled() - measurement) < deadband)
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
    public void driveRight()
    {
        autoSpeed.reset();
        MPID.reset();
        MPID.setSetpoint(measurement);
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
                    ((TRCMecanumDrive) drive).driveCartesian(smoothedSpeed, 0.0, 0.0);
                }

                if (Math.abs(encoders.getAverageDistanceTraveled() - measurement) < deadband)
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
    public void driveRotation()
    {
        autoSpeed.reset();
        MPID.reset();
        MPID.setSetpoint(measurement);
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

                if (Math.abs(gyro.getAngle() - measurement) < deadband)
                {
                    deadbandcounter++;
                }
            }

            driving = false;
        }
    }
}