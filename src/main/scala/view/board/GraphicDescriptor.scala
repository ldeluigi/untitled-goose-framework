package view.board

import scalafx.scene.paint.Color

/**
 * An object which describes the graphical properties that the user wants the game to gave.
 */
trait GraphicDescriptor {

  def color: Option[Color]

  def path: Option[String]

}

object GraphicDescriptor {

  private class GraphicDescriptorImpl
  (val color: Option[Color], val path: Option[String])
    extends GraphicDescriptor


  def merge(a: GraphicDescriptor, b: GraphicDescriptor): GraphicDescriptor = {
    GraphicDescriptor(
      if (a.color.isDefined) a.color else b.color,
      if (a.path.isDefined) a.path else b.path
    )
  }

  def apply(colorOption: Option[Color], pathOption: Option[String]): GraphicDescriptor = new GraphicDescriptorImpl(colorOption, pathOption)

  /** A factory which creates a new GraphicDescriptor if only a custom color is specified. */
  def apply(specifiedColor: Color): GraphicDescriptor = new GraphicDescriptorImpl(Some(specifiedColor), None)

  /** A factory which creates a new GraphicDescriptor if only a custom resource path is specified. */
  def apply(path: String): GraphicDescriptor = new GraphicDescriptorImpl(None, Some(path))

  /** A factory which creates a new GraphicDescriptor if a custom color, path, stroke color and tile's name are specified. */
  def apply(specifiedColor: Color, path: String, strokeColor: Color, tileName: String): GraphicDescriptor =
    new GraphicDescriptorImpl(Some(specifiedColor), Some(path))

}
