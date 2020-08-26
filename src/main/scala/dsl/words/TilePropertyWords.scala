package dsl.words

import dsl.tile.properties.NameProperty

trait TilePropertyWords {

  def name(name: String): NameProperty = NameProperty(name)

}
