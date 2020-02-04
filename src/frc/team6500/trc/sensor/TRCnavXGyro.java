package frc.team6500.trc.wrappers.sensors;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class TRCnavXGyro extends AHRS implements Gyro
{
	/**
	 * Constructs the AHRS class using SPI communication, overriding the 
	 * default update rate with a custom rate which may be from 4 to 200, 
	 * representing the number of updates per second sent by the sensor.  
	 *<p>
	 * This constructor should be used if communicating via SPI.
	 *<p>
	 * Note that increasing the update rate may increase the 
	 * CPU utilization.
	 *<p>
	 * @param spi_port_id SPI Port to use
	 * @param update_rate_hz Custom Update Rate (Hz)
	 */
	public TRCnavXGyro(SPI.Port spi_port_id, byte update_rate_hz)
	{
		super(spi_port_id, update_rate_hz);
	}

	/**
	 * The AHRS class provides an interface to AHRS capabilities
	 * of the KauaiLabs navX Robotics Navigation Sensor via SPI, I2C and
	 * Serial (TTL UART and USB) communications interfaces on the RoboRIO.
	 *
	 * The AHRS class enables access to basic connectivity and state information,
	 * as well as key 6-axis and 9-axis orientation information (yaw, pitch, roll,
	 * compass heading, fused (9-axis) heading and magnetic disturbance detection.
	 *
	 * Additionally, the ARHS class also provides access to extended information
	 * including linear acceleration, motion detection, rotation detection and sensor
	 * temperature.
	 *
	 * If used with the navX Aero, the AHRS class also provides access to
	 * altitude, barometric pressure and pressure sensor temperature data
	 *
	 * This constructor allows the specification of a custom SPI bitrate, in bits/second.
	 *
	 * @param spi_port_id SPI Port to use
	 * @param spi_bitrate SPI bitrate (Maximum:  2,000,000)
	 * @param update_rate_hz Custom Update Rate (Hz)
	 */

	public TRCnavXGyro(SPI.Port spi_port_id, int spi_bitrate, byte update_rate_hz)
	{
		super(spi_port_id, spi_bitrate, update_rate_hz);
	}

	/**
	 * Constructs the AHRS class using I2C communication, overriding the 
	 * default update rate with a custom rate which may be from 4 to 200, 
	 * representing the number of updates per second sent by the sensor.  
	 *<p>
	 * This constructor should be used if communicating via I2C.
	 *<p>
	 * Note that increasing the update rate may increase the 
	 * CPU utilization.
	 *<p>
	 * @param i2c_port_id I2C Port to use
	 * @param update_rate_hz Custom Update Rate (Hz)
	 */
	public TRCnavXGyro(I2C.Port i2c_port_id, byte update_rate_hz)
	{
		super(i2c_port_id, update_rate_hz);
	}

	/**
	 * Constructs the AHRS class using serial communication, overriding the 
	 * default update rate with a custom rate which may be from 4 to 200, 
	 * representing the number of updates per second sent by the sensor.  
	 *<p>
	 * This constructor should be used if communicating via either 
	 * TTL UART or USB Serial interface.
	 *<p>
	 * Note that the serial interfaces can communicate either 
	 * processed data, or raw data, but not both simultaneously.
	 * If simultaneous processed and raw data are needed, use
	 * one of the register-based interfaces (SPI or I2C).
	 *<p>
	 * Note that increasing the update rate may increase the 
	 * CPU utilization.
	 *<p>
	 * @param serial_port_id SerialPort to use
	 * @param data_type either kProcessedData or kRawData
	 * @param update_rate_hz Custom Update Rate (Hz)
	 */
	public TRCnavXGyro(SerialPort.Port serial_port_id, SerialDataType data_type, byte update_rate_hz)
	{
		super(serial_port_id, data_type, update_rate_hz);
	}

	/**
	 * Constructs the AHRS class using SPI communication and the default update rate.  
	 *<p>
	 * This constructor should be used if communicating via SPI.
	 */
	public TRCnavXGyro()
	{
		super();
	}

	/**
	 * Constructs the AHRS class using SPI communication and the default update rate.  
	 *<p>
	 * This constructor should be used if communicating via SPI.
	 *<p>
	 * @param spi_port_id SPI port to use.
	 */
	public TRCnavXGyro(SPI.Port spi_port_id)
	{
		super(spi_port_id);
	}

	/**
	 * Constructs the AHRS class using I2C communication and the default update rate.  
	 *<p>
	 * This constructor should be used if communicating via I2C.
	 *<p>
	 * @param i2c_port_id I2C port to use
	 */
	public TRCnavXGyro(I2C.Port i2c_port_id)
	{
		super(i2c_port_id);
	}
	

	/**
	 * Constructs the AHRS class using serial communication and the default update rate, 
	 * and returning processed (rather than raw) data.  
	 *<p>
	 * This constructor should be used if communicating via either 
	 * TTL UART or USB Serial interface.
	 *<p>
	 * @param serial_port_id SerialPort to use
	 */
	public TRCnavXGyro(SerialPort.Port serial_port_id)
	{
		super(serial_port_id);
	}

	/**
	 *	On navX, the gyro must be calibrated manually. This mearly exists to satisfy Gyro, but will only print
	 *	that automated calibration is not supported
	 */
	public void calibrate() 
	{
		System.out.println("Automated calibration not supported on navX.");
	}
}
