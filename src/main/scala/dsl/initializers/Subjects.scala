package dsl.initializers

import dsl.words.board.GameWord
import dsl.words.tile.{TileWord, TilesWord}

trait Subjects {

  def game: GameWord = new GameWord

  val tile: TileWord = new TileWord

  val tiles: TilesWord = new TilesWord

}
