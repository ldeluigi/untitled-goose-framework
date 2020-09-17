package untitled.goose.framework.dsl.board.words

import untitled.goose.framework.dsl.board.words.properties.TileProperty.{BackgroundProperty, ColorProperty, GroupProperty, NameProperty}
import untitled.goose.framework.model.Colour

trait TilePropertyWords {

  def name(name: String): NameProperty = NameProperty(name)

  def group(group: String): GroupProperty = GroupProperty(group)

  def color(color: Colour): ColorProperty = ColorProperty(color)

  def background(path: String): BackgroundProperty = BackgroundProperty(path)

}
