package frc.team6500.trc.sensor;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.DigitalSource;

public class TRCDigitalEncoder extends Encoder implements TRCEncoder 
{
	/**
	 * Encoder constructor. Construct a Encoder given a and b channels.
	 *
	 * <p>The encoder will start counting immediately.
	 *
	 * @param channelA         The a channel DIO channel. 0-9 are on-board, 10-25 are on the MXP port
	 * @param channelB         The b channel DIO channel. 0-9 are on-board, 10-25 are on the MXP port
	 * @param reverseDirection represents the orientation of the encoder and inverts the output values
	 *                         if necessary so forward represents positive values.
	 */
	public TRCDigitalEncoder(final int channelA, final int channelB, boolean reverseDirection) 
	{
		super(channelA, channelB, reverseDirection);
	}

	/**
	 * Encoder constructor. Construct a Encoder given a and b channels.
	 *
	 * <p>The encoder will start counting immediately.
	 *
	 * @param channelA The a channel digital input channel.
	 * @param channelB The b channel digital input channel.
	 */
	public TRCDigitalEncoder(final int channelA, final int channelB)
	{
		super(channelA, channelB);
	}

	/**
	 * Encoder constructor. Construct a Encoder given a and b channels.
	 *
	 * <p>The encoder will start counting immediately.
	 *
	 * @param channelA         The a channel digital input channel.
	 * @param channelB         The b channel digital input channel.
	 * @param reverseDirection represents the orientation of the encoder and inverts the output values
	 *                         if necessary so forward represents positive values.
	 * @param encodingType     either k1X, k2X, or k4X to indicate 1X, 2X or 4X decoding. If 4X is
	 *                         selected, then an encoder FPGA object is used and the returned counts
	 *                         will be 4x the encoder spec'd value since all rising and falling edges
	 *                         are counted. If 1X or 2X are selected then a m_counter object will be
	 *                         used and the returned value will either exactly match the spec'd count
	 *                         or be double (2x) the spec'd count.
	 */
	public TRCDigitalEncoder(final int channelA, final int channelB, boolean reverseDirection, final EncodingType encodingType)
	{
		super(channelA, channelB, reverseDirection, encodingType);
	}

	/**
	 * Encoder constructor. Construct a Encoder given a and b channels. Using an index pulse forces 4x
	 * encoding
	 *
	 * <p>The encoder will start counting immediately.
	 *
	 * @param channelA         The a channel digital input channel.
	 * @param channelB         The b channel digital input channel.
	 * @param indexChannel     The index channel digital input channel.
	 * @param reverseDirection represents the orientation of the encoder and inverts the output values
	 *                         if necessary so forward represents positive values.
	 */
	public TRCDigitalEncoder(final int channelA, final int channelB, final int indexChannel, boolean reverseDirection)
	{
		super(channelA, channelB, indexChannel, reverseDirection);
	}

	/**
	 * Encoder constructor. Construct a Encoder given a and b channels. Using an index pulse forces 4x
	 * encoding
	 *
	 * <p>The encoder will start counting immediately.
	 *
	 * @param channelA     The a channel digital input channel.
	 * @param channelB     The b channel digital input channel.
	 * @param indexChannel The index channel digital input channel.
	 */
	public TRCDigitalEncoder(final int channelA, final int channelB, final int indexChannel)
	{
		super(channelA, channelB, indexChannel);
	}

	/**
	* Encoder constructor. Construct a Encoder given a and b channels as digital inputs. This is used
	* in the case where the digital inputs are shared. The Encoder class will not allocate the
	* digital inputs and assume that they already are counted.
	*
	* <p>The encoder will start counting immediately.
	*
	* @param sourceA          The source that should be used for the a channel.
	* @param sourceB          the source that should be used for the b channel.
	* @param reverseDirection represents the orientation of the encoder and inverts the output values
	*                         if necessary so forward represents positive values.
	*/
	public TRCDigitalEncoder(DigitalSource sourceA, DigitalSource sourceB, boolean reverseDirection)
	{
		super(sourceA, sourceB, reverseDirection);
	}

	/**
	 * Encoder constructor. Construct a Encoder given a and b channels as digital inputs. This is used
	 * in the case where the digital inputs are shared. The Encoder class will not allocate the
	 * digital inputs and assume that they already are counted.
	 *
	 * <p>The encoder will start counting immediately.
	 *
	 * @param sourceA The source that should be used for the a channel.
	 * @param sourceB the source that should be used for the b channel.
	 */
	public TRCDigitalEncoder(DigitalSource sourceA, DigitalSource sourceB)
	{
		super(sourceA, sourceB);
	}

	/**
	 * Encoder constructor. Construct a Encoder given a and b channels as digital inputs. This is used
	 * in the case where the digital inputs are shared. The Encoder class will not allocate the
	 * digital inputs and assume that they already are counted.
	 *
	 * <p>The encoder will start counting immediately.
	 *
	 * @param sourceA          The source that should be used for the a channel.
	 * @param sourceB          the source that should be used for the b channel.
	 * @param reverseDirection represents the orientation of the encoder and inverts the output values
	 *                         if necessary so forward represents positive values.
	 * @param encodingType     either k1X, k2X, or k4X to indicate 1X, 2X or 4X decoding. If 4X is
	 *                         selected, then an encoder FPGA object is used and the returned counts
	 *                         will be 4x the encoder spec'd value since all rising and falling edges
	 *                         are counted. If 1X or 2X are selected then a m_counter object will be
	 *                         used and the returned value will either exactly match the spec'd count
	 *                         or be double (2x) the spec'd count.
	 */
	public TRCDigitalEncoder(DigitalSource sourceA, DigitalSource sourceB, boolean reverseDirection, final EncodingType encodingType)
	{
		super(sourceA, sourceB, reverseDirection, encodingType);
	}

	/**
	 * Encoder constructor. Construct a Encoder given a, b and index channels as digital inputs. This
	 * is used in the case where the digital inputs are shared. The Encoder class will not allocate
	 * the digital inputs and assume that they already are counted.
	 *
	 * <p>The encoder will start counting immediately.
	 *
	 * @param sourceA          The source that should be used for the a channel.
	 * @param sourceB          the source that should be used for the b channel.
	 * @param indexSource      the source that should be used for the index channel.
	 * @param reverseDirection represents the orientation of the encoder and inverts the output values
	 *                         if necessary so forward represents positive values.
	 */
	public TRCDigitalEncoder(DigitalSource sourceA, DigitalSource sourceB, DigitalSource indexSource, boolean reverseDirection)
	{
		super(sourceA, sourceB, indexSource, reverseDirection);
	}

	/**
	 * Encoder constructor. Construct a Encoder given a, b and index channels as digital inputs. This
	 * is used in the case where the digital inputs are shared. The Encoder class will not allocate
	 * the digital inputs and assume that they already are counted.
	 *
	 * <p>The encoder will start counting immediately.
	 *
	 * @param sourceA     The source that should be used for the a channel.
	 * @param sourceB     the source that should be used for the b channel.
	 * @param indexSource the source that should be used for the index channel.
	 */
	public TRCDigitalEncoder(DigitalSource sourceA, DigitalSource sourceB, DigitalSource indexSource)
	{
		super(sourceA, sourceB, indexSource);
	}
}
