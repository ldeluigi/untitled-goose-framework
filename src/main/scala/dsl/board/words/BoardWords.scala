package dsl.board.words

trait BoardWords extends BoardPropertyWords with TilePropertyWords {

  def game: GameWord = new GameWord

  val tile: TileWord = new TileWord

  val tiles: TilesWord = new TilesWord

  def All: AllWord = AllWord()

}
