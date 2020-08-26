package dsl.tile.properties

import scalafx.scene.paint.Color

sealed trait TileProperty

object TileProperty {

  case class ColorProperty(color: Color) extends TileProperty

  case class GroupProperty(group: String) extends TileProperty

  case class NameProperty(name: String) extends TileProperty

  case class BackgroundProperty(path: String) extends TileProperty

}


