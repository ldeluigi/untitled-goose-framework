package untitled.goose.framework.dsl.board.words

import scalafx.scene.paint.Color
import untitled.goose.framework.dsl.board.words.properties.TileProperty.{BackgroundProperty, ColorProperty, GroupProperty, NameProperty}

trait TilePropertyWords {

  def name(name: String): NameProperty = NameProperty(name)

  def group(group: String): GroupProperty = GroupProperty(group)

  def color(color: Color): ColorProperty = ColorProperty(color)

  def background(path: String): BackgroundProperty = BackgroundProperty(path)

}
