package dsl.words

import dsl.properties.tile.TileProperty._
import scalafx.scene.paint.Color

trait TilePropertyWords {

  def name(name: String): NameProperty = NameProperty(name)

  def group(group: String): GroupProperty = GroupProperty(group)

  def color(color: Color): ColorProperty = ColorProperty(color)

  def background(path: String): BackgroundProperty = BackgroundProperty(path)

}
