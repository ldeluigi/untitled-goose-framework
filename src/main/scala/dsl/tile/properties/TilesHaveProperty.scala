package dsl.tile.properties

import dsl.tile.properties.TileProperty.{BackgroundProperty, ColorProperty, GroupProperty}

trait TilesHaveProperty {

  def have(group: GroupProperty): Unit = ???

  def have(color: ColorProperty): Unit = ???

  def have(background: BackgroundProperty): Unit = ???

}
