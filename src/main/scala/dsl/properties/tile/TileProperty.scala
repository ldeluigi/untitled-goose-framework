package dsl.properties.tile

import scalafx.scene.paint.Color

sealed trait TileProperty

object TileProperty {

  case class ColorProperty(value: Color) extends TileProperty

  case class GroupProperty(value: String) extends TileProperty

  case class NameProperty(name: String) extends TileProperty

  case class BackgroundProperty(path: String) extends TileProperty

}


