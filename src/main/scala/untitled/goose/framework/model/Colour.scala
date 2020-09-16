package untitled.goose.framework.model

// TODO scaladoc
trait Colour {
  def red: Double

  def green: Double

  def blue: Double

  def opacity: Double

  override def toString: String = "Colour(R:" + red + ", G:" + green + ", B:" + blue + ", Alpha:" + opacity + ")"
}

object Colour {

  private case class ColourImpl(red: Double, green: Double, blue: Double, opacity: Double) extends Colour

  // TODO scaladoc
  def apply(red: Double, green: Double, blue: Double, opacity: Double): Colour = ColourImpl(red, green, blue, opacity)

  // TODO scaladoc
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

    // TODO scaladoc
    def colours: Seq[Colour] = super.values.toSeq.map(valueToColourVal)
  }

}
