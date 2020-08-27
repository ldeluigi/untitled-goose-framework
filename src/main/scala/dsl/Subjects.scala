package dsl

import dsl.words.{GameWord, TileWord, TilesWord}

trait Subjects {

  def game: GameWord = new GameWord

  val tile: TileWord = ???

  val tiles: TilesWord = ???

}
