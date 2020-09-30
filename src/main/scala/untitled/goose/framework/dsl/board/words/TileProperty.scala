package untitled.goose.framework.dsl.board.words

import untitled.goose.framework.model.Colour

sealed trait TileProperty

object TileProperty {

  case class ColourProperty(value: Colour) extends TileProperty

  case class GroupProperty(value: String) extends TileProperty

  case class NameProperty(name: String) extends TileProperty

  case class BackgroundProperty(path: String) extends TileProperty

}


