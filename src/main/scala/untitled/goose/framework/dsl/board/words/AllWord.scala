package untitled.goose.framework.dsl.board.words

/** Enables "All ..." */
case class AllWord() {

  /** Enables "All tiles have..." */
  val tiles: GroupedTilesWord = new GroupedTilesWord

}
