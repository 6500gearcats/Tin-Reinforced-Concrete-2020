package frc.team6500.trc.util;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.DMC60;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.SD540;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.VictorSP;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import frc.team6500.trc.wrappers.sensors.TRCEncoder;
import frc.team6500.trc.wrappers.sensors.TRCSparkMaxEncoder;
import frc.team6500.trc.wrappers.sensors.TRCTalonEncoder;


/**
 * Enumerations used to specify which type of something should be used in the creation or execution of something which has several
 *     possible values
 */
public class TRCTypes
{
	// Types of Gyros
	public enum GyroType
	{
		ADXRS450,
		NavX,
	}
	
	// Types of Drivetrains
	public enum DriveType
	{
		Differential,
		Mecanum,
		Custom,
	}
	
	// Modes of joystick operation for differential drivetrain robots using arcade controls
	public enum DifferentialArcadeMode
	{
		XRotation,
		ZRotation,
	}
	
	// Types of Speed Controllers
	public enum SpeedControllerType
	{
		DMC60,
		Jaguar,
		PWMTalonSRX,
		PWMVictorSPX,
		SD540,
		Spark,
		Talon,
		CANVictorSPX,
		VictorSP,
		CANTalonSRX,
		CANSparkMax,
	}

	// Types of Encoders
	public enum EncoderType
	{
		Digital,
		Talon,
		SparkMax,
	}
	
	// Types of actions a TRCDirectionalSystem can execute
	public enum DirectionalSystemActionType
	{
		Stop,
		Forward,
		Reverse,
		FullForward,
		FullReverse,
	}

	// Types of actions a TRCPneumaticSystem can execute
	public enum PneumaticSystemActionType
	{
		Open,
		Close,
	}

	// Types of actions a drivetrain can execute in autonomous for pid
	public enum DriveActionType
	{
		Forward,
		Right,
		Rotate,
	}

	// Types of actions a drivetrain can execute in autonomous for continuous
	public enum DriveContinuousActionType
	{
		Stop,
		ForwardRight,
		Right,
		ReverseRight,
		Reverse,
		ReverseLeft,
		Left,
		ForwardLeft,
		RotateRight,
		RotateLeft,
		CurveRight,
		CurveLeft,
		Forward,
	}

	public enum DriveSyncState
	{
		Teleop,
		DrivePID,
		DriveContinuous,
	}

	// Types of ways data can be sent to the driver station from the rio
	public enum DataInterfaceType
	{
		NetworkTables,  // Only NetworkTables
		Board,          // SmartDashboard or ShuffleBoard
	}

	// Types of verbosity for logging output
	public enum VerbosityType
	{
		Log_Debug,
		Log_Info,
		Log_Error,
	}

	// Types of controllers
	public enum ControllerType
	{
		Xbox360,    // Microsoft XBox 360 wired controller
		Extreme3D,  // Logitech Extreme 3D Joystick, included in KoP
		Generic,    // Any other basic HID compliant joystick
	}

	// Types of buttons on an Xbox controller (these are precisely accurate to the HID output from the controller, don't edit them)
	public enum XboxButtonType
	{
		A,
		B,
		X,
		Y,
		LeftBumper,
		RightBumper,
		Back,
		Start,
		LeftStick,
		RightStick,
	}

	// Types of axis of an Xbox controller (these are precisely accurate to the HID output from the controller, don't edit them)
	public enum XboxAxisType
	{
		LeftX,
		LeftY,
		LeftTrigger,
		RightTrigger,
		RightX,
		RightY,
	}

	// Types of buttons on a controller
	public enum ControllerButtonType
	{
		Xbox,
		Generic,
	}

	// Types of directions the robot can move, for use with the encoders
	public enum DirectionType
	{
		ForwardBackward,
		LeftRight,
	}
	

	// Positions a robot can start in at the beginning of a match
	public enum Position
	{
		Left,
		Middle,
		Right,;
		
		// Legacy method for use with methods that interpret position as a boolean "isLeft"
		public static boolean toBoolean(Position side)
		{
			// this formatting tho...
			     if (side == Position.Left)  { return true;  }
			else if (side == Position.Right) { return false; }
			else                             { return false; }
		}
	}


	public static Object encoderTypeToObject(int[] ports, double dpp, boolean lowres, boolean inverted, EncoderType type, SpeedController motor)
	{
		Object encoder = null;

		switch(type)
		{
			case Digital:
				encoder = new TRCEncoder(ports, dpp, lowres, inverted);
				break;
			case Talon:
				encoder = new TRCTalonEncoder(ports[0], dpp, inverted);
				break;
			case SparkMax:
				encoder = new TRCSparkMaxEncoder(motor, dpp, inverted);
				break;
			default:
				break;
		}

		return encoder;
	}

	public static SpeedController controllerTypeToObject(int port, SpeedControllerType type)
	{
		SpeedController motor = null;

		switch(type)
		{
			case DMC60:
				motor = new DMC60(port);
				break;
			case Jaguar:
				motor = new Jaguar(port);
				break;
			case PWMTalonSRX:
				motor = new PWMTalonSRX(port);
				break;
			case PWMVictorSPX:
				motor = new PWMVictorSPX(port);
				break;
			case SD540:
				motor = new SD540(port);
				break;
			case Spark:
				motor = new Spark(port);
				break;
			case Talon:
				motor = new PWMTalonSRX(port);
				break;
			case CANVictorSPX:
				motor = new WPI_VictorSPX(port);
				((WPI_VictorSPX) motor).configFactoryDefault();
				((WPI_VictorSPX) motor).set(ControlMode.PercentOutput, 0.0);
				((WPI_VictorSPX) motor).setInverted(false);
				((WPI_VictorSPX) motor).setSensorPhase(false);
				break;
			case VictorSP:
				motor = new VictorSP(port);
				break;
			case CANTalonSRX:
				motor = new WPI_TalonSRX(port);
				((WPI_TalonSRX) motor).configFactoryDefault();
				((WPI_TalonSRX) motor).set(ControlMode.PercentOutput, 0.0);
				((WPI_TalonSRX) motor).setInverted(false);
				((WPI_TalonSRX) motor).setSensorPhase(false);
				break;
			case CANSparkMax:
				motor = new CANSparkMax(port, CANSparkMaxLowLevel.MotorType.kBrushless);
				break;
			default:
				motor = new Spark(port);
				break;
		}

		return motor;
	}

	public static SpeedController[] speedControllerCreate(int[] motorPorts, SpeedControllerType[] motorTypes)
    {
		SpeedController newControllers[] = new SpeedController[motorPorts.length];

        for (int i = 0; i < motorPorts.length; i++)
		{
			newControllers[i] = controllerTypeToObject(motorPorts[i], motorTypes[i]);
		}

        return newControllers;
    }
}
