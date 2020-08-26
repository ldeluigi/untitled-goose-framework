package dsl.properties.tile

import dsl.properties.tile.TileProperty.{BackgroundProperty, ColorProperty, GroupProperty}

trait TilesHaveProperty {

  def have(group: GroupProperty): Unit = ???

  def have(color: ColorProperty): Unit = ???

  def have(background: BackgroundProperty): Unit = ???

}
