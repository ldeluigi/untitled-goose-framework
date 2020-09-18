package untitled.goose.framework.model

/**
 * Represent a colour in the RGBA format.
 */
trait Colour {

  /** The red component for the colour. */
  def red: Double

  /** The green component for the colour. */
  def green: Double

  /** The blue component for the colour. */
  def blue: Double

  /** The opacity assigned to the colour. */
  def opacity: Double

  override def toString: String = "Colour(R:" + red + ", G:" + green + ", B:" + blue + ", Alpha:" + opacity + ")"
}

object Colour {

  val maxValue = 255.0

  private case class ColourImpl(red: Double, green: Double, blue: Double, opacity: Double) extends Colour

  def limit(x: Double): Double = if (x < 0.0) 0.0 else if (x > 1.0) 1.0 else x

  /**
   * This factory creates a [[Colour]] object with the given values, clamped to the correct range if necessary.
   *
   * @param red     a double from 0.0 to 1.0
   * @param green   a double from 0.0 to 1.0
   * @param blue    a double from 0.0 to 1.0
   * @param opacity a double from 0.0 to 1.0
   * @return a [[Colour]] object
   */
  def apply(red: Double, green: Double, blue: Double, opacity: Double): Colour =
    ColourImpl(limit(red), limit(green), limit(blue), limit(opacity))

  implicit def hex2int(hex: String): Int = Integer.parseInt(hex, 16)

  /**
   * This factory creates a [[Colour]] object with the given values, clamped to the correct range if necessary.
   *
   * @param hexCode the hex code for this color using '#000000' syntax
   * @return a [[Colour]] object
   */
  def apply(hexCode: String): Colour = {
    val code = hexCode.replace("#", "")
    val red: Double = code.splitAt(2)._1 / maxValue
    val green: Double = code.splitAt(2)._2.splitAt(2)._1 / maxValue
    val blue: Double = code.splitAt(4)._2 / maxValue
    ColourImpl(red, green, blue, 1)
  }

  /**
   * This factory creates a [[Colour]] object with the given values, clamped to the correct range if necessary.
   *
   * @param red   an Int from 0 to 255
   * @param green an Int from 0 to 255
   * @param blue  an Int from 0 to 255
   * @return a [[Colour]] object
   */
  def apply(red: Int, green: Int, blue: Int): Colour = ColourImpl(red / maxValue, green / maxValue, blue / maxValue, 1)

  /**
   * This factory creates a [[Colour]] object with the given values, clamped to the correct range if necessary.
   *
   * @param red     an Int from 0 to 255
   * @param green   an Int from 0 to 255
   * @param blue    an Int from 0 to 255
   * @param opacity a double from 0.0 to 1.0
   * @return a [[Colour]] object
   */
  def apply(red: Int, green: Int, blue: Int, opacity: Double): Colour = ColourImpl(red / maxValue, green / maxValue, blue / maxValue, opacity)

  /**
   * An object containing some default [[Colour]] values to be used with a user friendly syntax using the colour name.
   */
  object Default extends Enumeration {

    protected case class Val(name: String, red: Double, green: Double, blue: Double, opacity: Double)
      extends super.Val with Colour {
      override def toString: String = name
    }

    import scala.language.implicitConversions

    implicit def valueToColourVal(x: Value): Val = x.asInstanceOf[Val]

    val Red: Colour = Val("Red", 1.0, 0.0, 0.0, 1.0)
    val Green: Colour = Val("Green", 0.0, 1.0, 0.0, 1.0)
    val Blue: Colour = Val("Blue", 0.0, 0.0, 1.0, 1.0)
    val White: Colour = Val("White", 1.0, 1.0, 1.0, 1.0)
    val Black: Colour = Val("Black", 0.0, 0.0, 0.0, 1.0)
    val Yellow: Colour = Val("Yellow", 1.0, 1.0, 0.0, 1.0)
    val Cyan: Colour = Val("Cyan", 0.0, 1.0, 1.0, 1.0)
    val Magenta: Colour = Val("Magenta", 1.0, 0.0, 1.0, 1.0)
    val Gray: Colour = Val("Gray", 0.5, 0.5, 0.5, 1.0)
    val Orange: Colour = Val("Orange", 1.0, 0.65, 0.0, 1.0)
    val Purple: Colour = Val("Purple", 0.5, 0.0, 0.5, 1.0)

    /**
     * A Seq containing all the default color values available.
     *
     * @return
     */
    def colours: Seq[Colour] = super.values.toSeq.map(valueToColourVal)
  }

}
