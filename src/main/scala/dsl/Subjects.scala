package dsl

import dsl.tile.properties.TileHasProperty
import dsl.words.{GameWord, TileWord, TilesWord}

trait Subjects {

  implicit def nameToTile(name: String): TileHasProperty = ???

  def game: GameWord = ???

  val tile: TileWord = ???

  val tiles: TilesWord = ???

}
