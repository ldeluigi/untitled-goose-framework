package view.board

import scalafx.scene.paint.Color

trait GraphicDescriptor {

  def color: Option[Color]

  def path: Option[String]

  def strokeColor: Option[Color]

  def tileName: Option[String]

}

object GraphicDescriptor {

  private class GraphicDescriptorImpl(val color: Option[Color], val path: Option[String], val strokeColor: Option[Color], val tileName: Option[String]) extends GraphicDescriptor {
  }

  def apply(specifiedColor: Color): GraphicDescriptor = new GraphicDescriptorImpl(Some(specifiedColor), None, None, None)

  def apply(path: String): GraphicDescriptor = new GraphicDescriptorImpl(None, Some(path), None, None)

  def apply(specifiedColor: Color, path: String, strokeColor: Color, tileName: String): GraphicDescriptor = new GraphicDescriptorImpl(Some(specifiedColor), Some(path), Some(strokeColor), Some(tileName))

}
