package untitled.goose.framework.dsl.board.words

import untitled.goose.framework.dsl.board.words.TileProperty.{BackgroundProperty, ColourProperty, GroupProperty, NameProperty}
import untitled.goose.framework.model.Colour

/** Used for tile properties. */
trait TilePropertyWords {

  /** Assigns given name to the tile. */
  def name(name: String): NameProperty = NameProperty(name)

  /** Assigns given group to the tile. */
  def group(group: String): GroupProperty = GroupProperty(group)

  /** Assigns given colour to the tile. */
  def colour(colour: Colour): ColourProperty = ColourProperty(colour)

  /** Assigns given background image, taken from resources, to the tile. */
  def background(path: String): BackgroundProperty = BackgroundProperty(path)

}
