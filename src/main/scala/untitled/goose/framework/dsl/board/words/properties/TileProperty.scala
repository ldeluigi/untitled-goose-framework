package untitled.goose.framework.dsl.board.words.properties

import scalafx.scene.paint.Color

sealed trait TileProperty

private[dsl] object TileProperty {

  case class ColorProperty(value: Color) extends TileProperty

  case class GroupProperty(value: String) extends TileProperty

  case class NameProperty(name: String) extends TileProperty

  case class BackgroundProperty(path: String) extends TileProperty

}


