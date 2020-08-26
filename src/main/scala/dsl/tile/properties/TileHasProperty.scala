package dsl.tile.properties

import dsl.tile.properties.TileProperty._

trait TileHasProperty {
  def has(name: NameProperty): Unit = ???

  def has(group: GroupProperty): Unit = ???

  def has(color: ColorProperty): Unit = ???

  def has(background: BackgroundProperty): Unit = ???

  def has(properties: TileProperty*): Unit = ???
}
