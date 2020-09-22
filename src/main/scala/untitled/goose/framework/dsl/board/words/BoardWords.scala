package untitled.goose.framework.dsl.board.words

trait BoardWords extends BoardPropertyWords with TilePropertyWords {

  /** Used as prefix, for example "game board ...". */
  def game: GameWord = new GameWord

  /** Used as subject, for example "tile [identifier] [verb] ..." */
  val tile: TileWord = new TileWord

  /** Used as subject, for example "tiles [identifiers] [verb] ..." */
  val tiles: TilesWord = new TilesWord

  /** Used as prefix, for example "All tiles [verb] ..." */
  def All: AllWord = AllWord()

}
