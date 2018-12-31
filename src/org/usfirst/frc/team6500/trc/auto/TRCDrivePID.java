package org.usfirst.frc.team6500.trc.auto;

import org.usfirst.frc.team6500.trc.wrappers.sensors.TRCEncoderSet;
import org.usfirst.frc.team6500.trc.wrappers.sensors.TRCGyroBase;
import org.usfirst.frc.team6500.trc.wrappers.systems.drives.TRCDifferentialDrive;
import org.usfirst.frc.team6500.trc.wrappers.systems.drives.TRCMecanumDrive;
import org.usfirst.frc.team6500.trc.util.TRCNetworkData;
import org.usfirst.frc.team6500.trc.util.TRCSpeed;
import org.usfirst.frc.team6500.trc.util.TRCTypes.DriveActionType;
import org.usfirst.frc.team6500.trc.util.TRCTypes.DriveType;
import org.usfirst.frc.team6500.trc.util.TRCTypes.VerbosityType;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.drive.RobotDriveBase;

public class TRCDrivePID extends Thread
{
    private static final double deadband = 2.0;
    private static final int verificationMin = 100;

    private static TRCEncoderSet encoders;
    private static TRCGyroBase gyro;
    private static Object drive;

    private static boolean driving;
    private static DriveType driveType;

    private MiniPID MPID;
    private TRCSpeed autoSpeed;

    private DriveActionType actionType;
    private double measurement;


    /**
     * Set up the necessary elements to be able to drive the robot in autonomous with a PID control loop for accurate distances and degrees
     * 
     * @param encoderset The robot's drivetrain encoders
     * @param gyroBase The robot's primary gyro
     * @param driveBase The robot's drivetrain
     * @param driveBaseType The type of the robot's drivetrain {@link DriveType}
     */
    public static void initializeTRCDrivePID(TRCEncoderSet encoderset, TRCGyroBase gyroBase, Object driveBase, DriveType driveBaseType)
    {
        encoders = encoderset;
        gyro = gyroBase;
        drive = driveBase;
        driveType = driveBaseType;

        TRCNetworkData.logString(VerbosityType.Log_Info, "DrivePID is online.");
        TRCNetworkData.createDataPoint("PIDSetpoint");
        TRCNetworkData.createDataPoint("PIDOutput");
        TRCNetworkData.createDataPoint("PIDOutputSmoothed");
    }

    /**
     * Prepare a thread to be used that will do driveAction for unit inches/degrees
     * 
     * @param driveAction The type of action the robot should take (one of {@link DriveActionType})
     * @param unit The inches/degrees of the action
     */
    public TRCDrivePID(DriveActionType driveAction, double unit)
    {
        driving = false;
        this.actionType = driveAction;
        this.measurement = unit;

        this.autoSpeed = new TRCSpeed();
        this.MPID = new MiniPID(1.0, 0.0, 0.0);
    }

    /**
     * Call .start(), not this, b/c Thread stuffs
     */
    @Override
    public void run()
    {
        switch(this.actionType)
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
        this.autoSpeed.reset();
        this.MPID.reset();
        this.MPID.setSetpoint(this.measurement);
        TRCNetworkData.updateDataPoint("PIDSetpoint", this.measurement);
        this.MPID.setOutputLimits(-1.0, 1.0);

        if (!driving)
        {
            driving = true;
            int deadbandcounter = 0;

            while (deadbandcounter < verificationMin && RobotState.isAutonomous())
            {
                double newSpeed = this.MPID.getOutput(encoders.getAverageDistanceTraveled());
                TRCNetworkData.updateDataPoint("PIDOutput", newSpeed);
                double smoothedSpeed = this.autoSpeed.calculateSpeed(newSpeed, 1.0);
                TRCNetworkData.updateDataPoint("PIDOutputSmoothed", smoothedSpeed);

                if (driveType == DriveType.Mecanum)
                {
                    ((TRCMecanumDrive) drive).driveCartesian(0.0, smoothedSpeed, 0.0);
                }
                else if (driveType == DriveType.Differential)
                {
                    ((TRCDifferentialDrive) drive).arcadeDrive(smoothedSpeed, 0.0, false);
                }

                if (Math.abs(encoders.getAverageDistanceTraveled() - this.measurement) < deadband)
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
        if (!driving)
        {
            this.autoSpeed.reset();
            this.MPID.reset();
            this.MPID.setSetpoint(this.measurement);
            TRCNetworkData.updateDataPoint("PIDSetpoint", this.measurement);
            this.MPID.setOutputLimits(-1.0, 1.0);

            driving = true;
            int deadbandcounter = 0;

            while (deadbandcounter < verificationMin && RobotState.isAutonomous())
            {
                double newSpeed = this.MPID.getOutput(encoders.getAverageDistanceTraveled());
                TRCNetworkData.updateDataPoint("PIDOutput", newSpeed);
                double smoothedSpeed = this.autoSpeed.calculateSpeed(newSpeed, 1.0);
                TRCNetworkData.updateDataPoint("PIDOutputSmoothed", smoothedSpeed);

                if (driveType == DriveType.Mecanum)
                {
                    ((TRCMecanumDrive) drive).driveCartesian(smoothedSpeed, 0.0, 0.0);
                }

                if (Math.abs(encoders.getAverageDistanceTraveled() - this.measurement) < deadband)
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
        if (!driving)
        {
            this.autoSpeed.reset();
            this.MPID.reset();
            this.MPID.setSetpoint(this.measurement);
            TRCNetworkData.updateDataPoint("PIDSetpoint", this.measurement);
            this.MPID.setOutputLimits(-0.5, 0.5);

            driving = true;
            int deadbandcounter = 0;

            while (deadbandcounter < verificationMin && RobotState.isAutonomous())
            {
                double newSpeed = this.MPID.getOutput(gyro.getAngle());
                TRCNetworkData.updateDataPoint("PIDOutput", newSpeed);
                double smoothedSpeed = this.autoSpeed.calculateSpeed(newSpeed, 1.0);
                TRCNetworkData.updateDataPoint("PIDOutputSmoothed", smoothedSpeed);

                if (driveType == DriveType.Mecanum)
                {
                    ((TRCMecanumDrive) drive).driveCartesian(0.0, 0.0, smoothedSpeed);
                }
                else if (driveType == DriveType.Differential)
                {
                    ((TRCDifferentialDrive) drive).arcadeDrive(0.0, smoothedSpeed, false);
                }

                if (Math.abs(gyro.getAngle() - this.measurement) < deadband)
                {
                    deadbandcounter++;
                }
            }

            driving = false;
        }
    }
}