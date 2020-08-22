package view.board

import scalafx.scene.paint.Color

/**
 * An object which describes the graphical properties that the user wants the game to gave.
 */
trait GraphicDescriptor {

  def color: Option[Color]

  def path: Option[String]

  def strokeColor: Option[Color]

  def tileName: Option[String]

}

object GraphicDescriptor {

  private class GraphicDescriptorImpl(val color: Option[Color], val path: Option[String], val strokeColor: Option[Color], val tileName: Option[String]) extends GraphicDescriptor {
  }

  /** A factory which creates a new GraphicDescriptor if only a custom color is specified. */
  def apply(specifiedColor: Color): GraphicDescriptor = new GraphicDescriptorImpl(Some(specifiedColor), None, None, None)

  /** A factory which creates a new GraphicDescriptor if only a custom resource path is specified. */
  def apply(path: String): GraphicDescriptor = new GraphicDescriptorImpl(None, Some(path), None, None)

  /** A factory which creates a new GraphicDescriptor if a custom color, path, stroke color and tile's name are specified. */
  def apply(specifiedColor: Color, path: String, strokeColor: Color, tileName: String): GraphicDescriptor = new GraphicDescriptorImpl(Some(specifiedColor), Some(path), Some(strokeColor), Some(tileName))

}
