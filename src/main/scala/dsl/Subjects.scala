package dsl

import dsl.words.{GameWord, TileWord, TilesWord}

trait Subjects {

  def game: GameWord = ???

  val tile: TileWord = ???

  val tiles: TilesWord = ???

}
