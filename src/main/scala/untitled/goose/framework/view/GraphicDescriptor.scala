package untitled.goose.framework.view

import untitled.goose.framework.model.Colour

/**
 * An object which describes the graphical properties that the user wants the runtime to have.
 */
trait GraphicDescriptor {

  def color: Option[Colour]

  def path: Option[String]

  override def toString: String = "Color: " + this.color.toString + " Path: " + this.path.toString
}

object GraphicDescriptor {

  private class GraphicDescriptorImpl
  (val color: Option[Colour], val path: Option[String])
    extends GraphicDescriptor


  def merge(a: GraphicDescriptor, b: GraphicDescriptor): GraphicDescriptor = {
    GraphicDescriptor(
      if (a.color.isDefined) a.color else b.color,
      if (a.path.isDefined) a.path else b.path
    )
  }

  def apply(colorOption: Option[Colour], pathOption: Option[String]): GraphicDescriptor = new GraphicDescriptorImpl(colorOption, pathOption)

  /** A factory which creates a new GraphicDescriptor if only a custom color is specified. */
  def apply(specifiedColor: Colour): GraphicDescriptor = new GraphicDescriptorImpl(Some(specifiedColor), None)

  /** A factory which creates a new GraphicDescriptor if only a custom resource path is specified. */
  def apply(path: String): GraphicDescriptor = new GraphicDescriptorImpl(None, Some(path))

  /** A factory which creates a new GraphicDescriptor if a custom color, path, stroke color and tile's name are specified. */
  def apply(specifiedColor: Colour, path: String, strokeColor: Colour, tileName: String): GraphicDescriptor =
    new GraphicDescriptorImpl(Some(specifiedColor), Some(path))

}
